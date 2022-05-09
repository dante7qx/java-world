package com.dante.hiapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class SendPostMessage {
	private HttpURLConnection conn = null;    
    
	/**
	 * POST请求服务端
	 * @param urlAddr
	 * @param val
	 * @return
	 * @throws Exception
	 */
    public String send(String urlAddr, String val) throws Exception {    

         StringBuffer params = new StringBuffer();    
         StringBuffer result = new StringBuffer();
         params.append(val);    

        try{    
			 URL url = new URL(urlAddr);    
			 conn = (HttpURLConnection)url.openConnection();    
//			 conn.setReadTimeout(0);
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
			   
             BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			 String line;
			while ((line = in.readLine()) != null) {
			    result.append(line);
			}
			
         }catch(Exception ex){    
             ex.printStackTrace();    
         }finally{    
             conn.disconnect();    
         }    
        return result.toString();    
    }
}
