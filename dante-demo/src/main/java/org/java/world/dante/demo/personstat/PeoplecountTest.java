package org.java.world.dante.demo.personstat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.FileCopyUtils;

import com.alibaba.fastjson.JSONObject;
import com.ccb.publicservice.outbound.RestOutboundExecutor;
import com.ccb.publicservice.outbound.enumeration.SwitchEnum;
import com.ccb.publicservice.outbound.pojo.ResponseMessage;
import com.ccb.publicservice.outbound.pojo.TransKey;
import com.ccb.publicservice.outbound.pojo.WorkKeyNegoCpa;


public class PeoplecountTest {

    static String address = "https://service.ccb.com";
    static String appId = "2_app_20200918161954";
    static String appSecret = "46664b0d8d9e4e07a35bb533a7625581";
    static String bussinessId = "123456789";
    static String userId = "default";
    static String pbe = "c475j73b0298421h";
    static String keyFactor = "1234567891111111";

    static String transkey_url = "https://service.ccb.com/psp/getTransKey";
    static String pubkey = "3NVYvzUUyxBqTuMtDYSbWPyDKxhXhfSrRTOUvR+NqUhIFfRL0bEgJ4dOpaJK47T6gs9XMob4MKxY81FwRVlwVQ==";
    static String passwordType = "03";
    static String encAlg = "ps2";

    public static void main(String[] args) {
        try {
            Map<String, String> options = new HashMap<>();
            options.put("protocol", "https");

            TransKey transKey = new TransKey();
            transKey.setAppSecret(appSecret);
            transKey.setPasswordType(passwordType);
            transKey.setCipherKey(keyFactor);
            transKey.setEncAlg(encAlg);
            //获取协商秘钥
            WorkKeyNegoCpa WorkKeyNegoCpa = RestOutboundExecutor.getTransKey(transkey_url, userId, pbe, pubkey, transKey, options);
            System.out.println(WorkKeyNegoCpa.getCipherKey());
            //开始处理
//            Analysis(WorkKeyNegoCpa);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Analysis(WorkKeyNegoCpa WorkKeyNegoCpa) {

        Map<String, String> options = new HashMap<>();
        options.put("protocol", "https");


        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Content-Type", "application/json;charset=UTF-8");
        requestHeader.put("C-App-Id", appId);
        requestHeader.put("C-Tenancy-Id", "psp-001");
        requestHeader.put("C-Dynamic-Password", WorkKeyNegoCpa.getDynamicPassword());

        Map<String, String> secretParam = new HashMap<>();
        secretParam.put(SwitchEnum.ECRP.getCode(), encAlg);

        Map<String, String> requestParam = new HashMap<>();
        //图片
//        requestParam.put("fileType", "01");
        //视频
        requestParam.put("fileType", "02");

        Map<String, String> fileMap = new HashMap<>();
        //图片
//        fileMap.put("file", "D:\\test.png");
        //视频
        fileMap.put("file", "D:\\test.mp4");
        try {
            ResponseMessage rsp = RestOutboundExecutor.callService(
                    address,
                    "/product/fileupload",
                    appId,
                    appSecret,
                    bussinessId,
                    pbe,
                    keyFactor,
                    secretParam,
                    requestParam,
                    requestHeader,
                    fileMap,
                    options,
                    null);
            String body = rsp.getResponseBody();
            JSONObject json = JSONObject.parseObject(body);
            JSONObject jsonObject = (JSONObject)json.get("C-Response-Body");
            if("01".equals(requestParam.get("fileType"))){
                //图片
                System.out.println("统计人数为："+ jsonObject.get("peoplecount"));
                //保存处理后的图片
                FileCopyUtils.copy(jsonObject.getBytes("file"),new FileOutputStream(new File("D:\\test2.png")));
            }else if("02".equals(requestParam.get("fileType"))){
                //视频
                //保存处理后的视频
                FileCopyUtils.copy(jsonObject.getBytes("file"),new FileOutputStream(new File("D:\\test2.mp4")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
