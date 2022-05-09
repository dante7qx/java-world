package org.java.world.dante.demo.easycode;

import org.junit.Test;

import com.ccbft.easyCode.PHEasyCode;
import com.ccbft.easyCode.PHSelfCode;
import com.ccbft.easyCode.SELF_CODE_TYPE;

public class EasyCode {

	@Test
	public void codeVersion() {
		System.out.println(PHEasyCode.getVersion());
	}
	
	@Test
	public void generateCode() {
		PHEasyCode easyCode = PHEasyCode.getInstance(null);
		PHSelfCode selfCode = new PHSelfCode();
		selfCode.addCode(SELF_CODE_TYPE.BIRTHDAY, "19880721");
		System.out.println(easyCode.getCode(8, selfCode));
	}

}