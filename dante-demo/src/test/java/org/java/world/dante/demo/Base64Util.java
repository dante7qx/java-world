package org.java.world.dante.demo;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Encoder;

public class Base64Util {

    /**
     * 
     * @param str
     *            string to encode
     * @return the string after encoding
     */
    public static String encode(String str) {
        String code = new String(Base64.encodeBase64(encodeOrDecode(str)
            .getBytes()));
        return code;
    }

    /**
     * 
     * @param str
     *            string to decode
     * @return the string after decode
     */
    public static String decode(String str) {
        String code = new String(Base64.decodeBase64(str.getBytes()));
        return encodeOrDecode(code);
    }

    private static String encodeOrDecode(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

    public static String GetBase64Code(String source) {
        return GetBase64Code(source, "UTF-8");
    }

    public static String GetBase64Code(String source, String charsetName) {
        BASE64Encoder base64 = new BASE64Encoder();
        String ret = null;
        try {
            ret = base64.encode(source.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        ret = ret.replaceAll("\r\n", "");
        return ret;
    }

    
    public static void main(String[] args) {
        System.out.println(encode("mps.jdbc.url=e541917c3f2e0870ed0310c38d7eb973fd4f6eacc040b5fd0f9557378908100c3490baee086f7ad1901224deba510f8d384805f1e6d91f90j"));
        System.out.println(decode("e541917c3f2e0870ed0310c38d7eb973fd4f6eacc040b5fd0f9557378908100c3490baee086f7ad1901224deba510f8d384805f1e6d91f90j"));

    }
}
