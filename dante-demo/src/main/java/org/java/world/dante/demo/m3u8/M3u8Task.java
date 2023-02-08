package org.java.world.dante.demo.m3u8;

import java.io.IOException;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FFmpegLogCallback;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class M3u8Task implements Runnable {
	Logger m_log = LoggerFactory.getLogger(M3u8Task.class);

//	String m_rtspURL = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4";
	
	String m_rtspURL = "rtsp://admin:risun8768@192.168.1.249:554/Streaming/Channels/101";
	
	String m_rtspM3u8 = "/Users/dante/Documents/Technique/Vue/vue2/video/1.m3u8";
	
	@Override
	public void run() {
		try {			
			this.convertMediaToM3u8ByHttp(m_rtspURL, m_rtspM3u8, "1", 5);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			m_log.error("转换异常");
		}
	}
	
	public void convertMediaToM3u8ByHttp(String inputStream, String m3u8Url, String hls_time,int maxSize) throws IOException {
		// 开启ffmpeg日志级别；QUIET是屏蔽所有，可选INFO、DEBUG、ERROR等
        avutil.av_log_set_level(avutil.AV_LOG_ERROR);
        FFmpegLogCallback.set();
 
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
        grabber.setOption("rtsp_transport", "tcp");
        grabber.setImageWidth(960);
		grabber.setImageHeight(540);
        grabber.start();
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(m3u8Url, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());
 
        recorder.setFormat("hls");
        recorder.setOption("hls_time", hls_time);
        recorder.setOption("hls_list_size", maxSize-1+"");
        recorder.setOption("hls_flags", "delete_segments");
        recorder.setOption("hls_delete_threshold", "1");
        recorder.setOption("hls_segment_type", "mpegts");
 
        // http属性
        recorder.setOption("method", "POST");
 
        recorder.setFrameRate(25);
        recorder.setGopSize(2 * 25);
        recorder.setVideoQuality(1.0);
        recorder.setVideoBitrate(960 * 540);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        recorder.start();
 
        Frame frame;
        while ((frame = grabber.grabImage()) != null) {
            try {
                recorder.record(frame);
            } catch (FrameRecorder.Exception e) {
                e.printStackTrace();
            }
        }
        recorder.setTimestamp(grabber.getTimestamp());
        recorder.close();
        grabber.close();
    }
	
	public static void main(String[] args) {
		Thread worker1 = new Thread(new M3u8Task());
		worker1.start();
		try {
			Thread.sleep(1*60*60*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
