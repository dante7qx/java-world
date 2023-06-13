package com.dante.jdk8;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;

public class Spieda {

	public static void main(String[] args) {
		String cookie = "4hP44ZykCTt5S=5HrCTAf9.y8zZv.igPO1uokanroIQJlbnzBeN5Q7lHhpQuoiJmf36SVuPvOs1kk95oHQFRtmm0t.nnWpeQ0VgLq; Hm_lvt_e4d5f2fca1c9cc73451f27d30520d193=1683799218; Hm_lpvt_e4d5f2fca1c9cc73451f27d30520d193=1683799218; yfx_c_g_u_id_10000005=_ck23051118002916389766265726841; yfx_f_l_v_t_10000005=f_t_1683799229634__r_t_1683799229634__v_t_1683799229634__r_c_0; 4hP44ZykCTt5T=6.k1j6CeZ42JeddwehhBPk5m5f_bGEvEt0IG2evw799fKLe2e6udviMpD9JcaAxSj.yU5pz6zO8HXfgBDyrCjZk2vkdVlditsP9MsarObuDf2oNOFvxc4VvJvglLTORo1MtKqFZp3V21GHrRDUQmtfrExEdrT_q0VEMk2DvkLI3bzXGpD37DyJ4EPGWm6YdHjsMGuUrrGlwXMwDjyBopvBCMJXfMvoanCvGvAM3DqKASGOZY.S9gwbp51sO8W2_o4Zy7I7DnX5FI_q05Hxsp7m_e5DK_iFO_JpjIRIjH0i63kl2w1yyn7uVhepJsD3Fxf9k0ceBEAhkULo9dzeNB.AA29AgHoJ3lEgfm3JrrL.0";
		String url = "http://kjt.gansu.gov.cn/common/search/8b0e378b0f424ed2b381455e389c24b3?_isAgg=true&_isJson=true&_pageSize=20&_template=index&_rangeTimeGte=&_channelName=&page=1";
		HttpRequest req = HttpUtil.createGet(url);
		Map<String, String> headers = new HashMap<>();
//		headers.put("If-None-Match", etag);
		headers.put("Accept", "*/*");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Cookie", cookie);
//		headers.put("If-None-Match", "W/\"1ad18-wHfWgR8aVwpgLwMmNlOY+AWDGRY\"");
		headers.put("Host", "kjt.gansu.gov.cn");
		headers.put("Proxy-Connection", "keep-alive");
		headers.put("Referer", "http://kjt.gansu.gov.cn/common/search/8b0e378b0f424ed2b381455e389c24b3?_isAgg=true&_isJson=true&_pageSize=20&_template=index&_rangeTimeGte=&_channelName=&page=1");
		headers.put("Upgrade-Insecure-Requests", "1");
		headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");

		req.addHeaders(headers);

		String body = req.execute().body();
		System.out.println(body);

	}

}
