package org.java.world.dante.demo.icr;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ccbft.icr.IcrApplication;
import com.ccbft.icr.entity.ImageInfo;

public class ICRApi {

	private static final String PIC_DIR = "/Users/dante/Documents/Project/java-world/javaworld/dante-demo/pic/";
	
	/**
	 * 通用识别
	 */
	@Test
	public void testCommonIndentifyAPI() {
		List<ImageInfo> commonImageInfoList = new ArrayList<>();
		IcrApplication icrApplication = IcrApplication.getInstance();
		
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setFilePath(PIC_DIR.concat("common2.png"));
		commonImageInfoList.add(imageInfo);
		
		String commonData = icrApplication.commonIdentifyApi(commonImageInfoList, "{\"uid\":\"67562\"}", false);
		System.out.println(commonData);
	}
	
	/**
	 * 身份证识别
	 */
	@Test
	public void testIdCardIndentifyAPI() {
		List<ImageInfo> idcardImageInfoList = new ArrayList<>();
		IcrApplication icrApplication = IcrApplication.getInstance();
		
		ImageInfo idcardFrontImageInfo = new ImageInfo();
		idcardFrontImageInfo.setFilePath(PIC_DIR.concat("frontend.png"));
		idcardFrontImageInfo.setFlag("front");
		idcardImageInfoList.add(idcardFrontImageInfo);
		
		ImageInfo idcardBackImageInfo = new ImageInfo();
		idcardBackImageInfo.setFilePath(PIC_DIR.concat("backend.png"));
		idcardBackImageInfo.setFlag("back");
		idcardImageInfoList.add(idcardBackImageInfo);
		
		String idcardData = icrApplication.idCardIdentifyApi(idcardImageInfoList, null, false);
		System.out.println(idcardData);
	}
	
	/**
     * 银行卡识别
     */
    @Test
    public void testBankCardIndentifyAPI() {
        List<ImageInfo> bankcardImageInfoList = new ArrayList<>();
        IcrApplication icrApplication = IcrApplication.getInstance();

        ImageInfo bankcardImageInfo = new ImageInfo();
        bankcardImageInfo.setFilePath(PIC_DIR.concat("bank.jpg"));
        bankcardImageInfoList.add(bankcardImageInfo);

        // 返回Json字符串
        String bankcardData = icrApplication.bankCardIdentifyApi(bankcardImageInfoList, null, false);
        System.out.println(bankcardData);
    }	
  
  	/**
     * 驾驶证识别
     */
    @Test
    public void testDriverIndentifyAPI() {
        List<ImageInfo> driverImageInfoList = new ArrayList<>();
        IcrApplication icrApplication = IcrApplication.getInstance();

        ImageInfo driverImageInfo = new ImageInfo();
        driverImageInfo.setFilePath(PIC_DIR.concat("driver.png"));
        driverImageInfoList.add(driverImageInfo);

        // 返回Json字符串
        String driverImageData = icrApplication.driverIdentifyApi(driverImageInfoList, null, false);
        System.out.println(driverImageData);
    }
  
  	/**
     * 行驶证识别
     */
    @Test
    public void testVehicleIndentifyAPI() {
        List<ImageInfo> vehicleImageInfoList = new ArrayList<>();
        IcrApplication icrApplication = IcrApplication.getInstance();

        ImageInfo vehicleImageInfo = new ImageInfo();
        vehicleImageInfo.setFilePath(PIC_DIR.concat("vehicle.png"));
        vehicleImageInfoList.add(vehicleImageInfo);

        // 返回Json字符串
        String vehicleImageData = icrApplication.vehicleIdentifyApi(vehicleImageInfoList, null, false);
        System.out.println(vehicleImageData);
    }
  
  	/**
     * 营业执照识别
     */
    @Test
    public void testBizLicenseIndentifyAPI() {
        List<ImageInfo> bizLicenseImageInfoList = new ArrayList<>();
        IcrApplication icrApplication = IcrApplication.getInstance();

        ImageInfo bizLicenseImageInfo = new ImageInfo();
        bizLicenseImageInfo.setFilePath(PIC_DIR.concat("bizLic.png"));
        bizLicenseImageInfoList.add(bizLicenseImageInfo);

        // 返回Json字符串
        String bizLicenseImageData = icrApplication.bizLicenseIdentifyApi(bizLicenseImageInfoList, null, false);
        System.out.println(bizLicenseImageData);
    }
    
    /**
     * 房产证识别
     */
    @Test
    public void testOwnerCertIndentifyAPI() {
        List<ImageInfo> ownerCertImageInfoList = new ArrayList<>();
        IcrApplication icrApplication = IcrApplication.getInstance();

        ImageInfo ownerCertImageInfo = new ImageInfo();
        ownerCertImageInfo.setFilePath(PIC_DIR.concat("owener.png"));
        ownerCertImageInfoList.add(ownerCertImageInfo);

        // 返回Json字符串
        String ownerCertImageData = icrApplication.ownerCertIdentifyApi(ownerCertImageInfoList, null, false);
        System.out.println(ownerCertImageData);
    }
    
    /**
     * 光盘识别
     */
    @Test
    public void testEconomyIndentifyAPI() {
        List<ImageInfo> economyImageInfoList = new ArrayList<>();
        IcrApplication icrApplication = IcrApplication.getInstance();

        ImageInfo economyImageInfo = new ImageInfo();
        economyImageInfo.setFilePath(PIC_DIR.concat("guangpan.png"));
        economyImageInfoList.add(economyImageInfo);

        // 返回Json字符串
        String economyImageData = icrApplication.economyIdentifyApi(economyImageInfoList, null, false);
        System.out.println(economyImageData);
    }
	

}
