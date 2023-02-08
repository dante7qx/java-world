package org.java.world.dante.demo.mp3;

import java.io.File;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

public class Mp3Test {

	public static void getMp3Duration(String filePath) {
		try {
			File mp3File = new File(filePath);
			MP3File f = (MP3File) AudioFileIO.read(mp3File);
			MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
			System.out.println("时长:" + Float.parseFloat(audioHeader.getTrackLength() + ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getMp3Duration("/Users/dante/Documents/Project/java-world/javaworld/dante-demo/src/main/java/org/java/world/dante/demo/mp3/tuns.mp3");
	}

}
