package com.epolleo.bp.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * CnToSpellHelper
 * <p>
 * Date: 2012-10-16,20:05:39 +0800
 * 
 * @version 1.0
 */
public class CnToSpellHelper {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String str = "中华人名共和国";
        System.out.print(getPinYinHeadChar(str));
    }

    // 将汉字转换为全拼
    public static String getPingYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches(
                    "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else
                    t4 += java.lang.Character.toString(t1[i]);
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }

        return t4;
    }

    // 返回中文的首字母
    public static String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert.toUpperCase();
    }

    // 将字符串转移为ASCII码
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

    private static String concatPinyinStringArray(String[] pinyinArray) {
        StringBuffer pinyinStrBuf = new StringBuffer();
        if ((null != pinyinArray) && (pinyinArray.length > 0)) {
            for (int i = 0; i < pinyinArray.length; i++) {
                pinyinStrBuf.append(pinyinArray[i]);
                pinyinStrBuf.append(",");
            }
        }
        String outputString = pinyinStrBuf.toString();
        return outputString;
    }

    // 获取多音字的拼音
    public static String getMoreSpell(String cnStr, boolean withTone) {
        char cnChar = cnStr.charAt(0);
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        if (withTone) {
            outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        } else {
            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        }

        String[] pinyinArray = null;
        try {
            pinyinArray = PinyinHelper.toHanyuPinyinStringArray(cnChar,
                outputFormat);
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        String spellStr = concatPinyinStringArray(pinyinArray);
        spellStr = spellStr.substring(0, spellStr.length() - 1);
        return spellStr;
    }
}
