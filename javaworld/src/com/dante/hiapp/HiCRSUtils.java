package com.dante.hiapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import HNA.Security.Base64Utils;

public class HiCRSUtils {
	
	private final static String URL = "http://app.hk01.hnahotels.com:7054/CRSHiAppService.ashx"; // 服务端的URL地址
	private final static String CODE = "HiApp";
	private final static String PWD = "9:;<4ymz";
	private static String rasPrivatekey;
	private static String baseReqXML;
	
	static {
		try {
			rasPrivatekey = readFileByLines(HiCRSUtils.class.getResourceAsStream("private_hiapp.xml"));
			
			System.out.println(rasPrivatekey);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String readFileByLines(InputStream resourceAsStream) throws IOException {
		StringBuffer buffer = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));
		String line = in.readLine();
		while (line != null && line.trim().length() > 0) {
			buffer.append(line);
			line = in.readLine();
		}
		resourceAsStream.close();
		in.close();
		return buffer.toString();
    }

	/**
	 * POST请求服务端
	 * 
	 * @param urlAddr
	 * @param val
	 * @return
	 * @throws Exception
	 */
	private static String send(String urlAddr, String val) {

		StringBuffer params = new StringBuffer();
		StringBuffer result = new StringBuffer();
		params.append(val);
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlAddr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(params.length()));
			conn.setDoInput(true);
			conn.connect();

			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(params.toString());
			out.flush();
			out.close();

			int code = conn.getResponseCode();
			if (code != 200) {
				System.out.println("ERROR===" + code);
			} else {
				System.out.println("Success!");
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return result.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Base64Utils.encode("1234,qwer".getBytes("UTF-8")));
		System.out.println(Base64Utils.decode("<RSAKeyValue><Modulus>wUcWK/WKrHHKp5e32Q8DWavvkY4nf/4TtB5KyiPG73x2eu+/giNqcudbF/a9IVwVVYe5vjtBGelYrr6i5/GUJXGNXx8RNFLNRI4fuQ+Q9rFOmF8RPbUx9KdkOb/PFO86x2qFW5lvr5JDJ6d/9OIcPLmnt6I7+/OSEjzXnI/9198=</Modulus><Exponent>AQAB</Exponent><P>+uYk4PFQhl95Nfbl8Jn25qjdaFvqk9XzxTpfBXF9aT1WLHgaLJzWSfLNDroxVdcanBvdTCm17WGvcAOKIrpUfw==</P><Q>xTUKWgcQBZSHUkcI4wYWSrr8wFGpEgwGJsvciOsl1b3QIqPBEetVO1L/HO7SSMyR+2/yZk2ZcDb0eDuZx8dMoQ==</Q><DP>R8BFT6bzNlpbWZlwDKybNT+Zgdy3auvGuOKGhhH8oXoPCMyX/6YiiidLiSiqLzhCvd4iHUPYaTiEaW45tNDAlQ==</DP><DQ>OFhoRcLA1CdU5rA/HMZvdhipALYUTYC7/TfAIiq1m7VpE4ygLGOF7bZQB89Aq+YvS5z3sD05uvMotHWrAYazIQ==</DQ><InverseQ>crCPsPE4HxbU5vsiy7+erS7GI4kbXIO2plulBhyx0/Ef9ahMPVME51O9XHImxeiWbz+SgmS2DwG8XuEX/obbKw==</InverseQ><D>E7cJlvBT9eUhk9jRAw4Z/GKibEmA0/h50Ayq+lWN7E3Y1A9yjHbKW/AnhZlAj4+SCYhvtOzcZiU8S47clSY6Qy3NhP8SXT/SV7L15igZGehXbJGltuoa8Wt9Wfl3QAuHYgqmx1X+eaTNjzBhq3iDashf6le1yxj9OXjdmKjgs8E=</D></RSAKeyValue>\n" + 
				"MTIzNCxxd2Vy"));
	}
}
