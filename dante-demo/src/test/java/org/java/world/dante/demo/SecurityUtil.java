package org.java.world.dante.demo;

public class SecurityUtil {
	private static DESPlus desPlus = null;
	static {
		try {
			desPlus = new DESPlus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String encode(String strIn) throws Exception {
		return desPlus.encrypt(strIn);
	}

	public static String decode(String strIn) throws Exception {
		return desPlus.decrypt(strIn);
	}

	public static void main(String[] args) throws Exception {
//		System.out.println(encode("cem.jdbc.url|jdbc:jtds:sqlserver://10.71.88.214:1433/deerjet_cem"));
//		System.out.println(encode("crm.jdbc.url|jdbc:jtds:sqlserver://10.71.88.214:1433/deerjet_crm"));
//		System.out.println(encode("me.jdbc.url|jdbc:jtds:sqlserver://10.71.88.214:1433/deerjet_me"));
//		System.out.println(encode("ocs.jdbc.url|jdbc:jtds:sqlserver://10.71.88.214:1433/deerjet_ocs"));
//		System.out.println(encode("fi.jdbc.url|jdbc:jtds:sqlserver://10.71.88.214:1433/deerjet_fi"));
//		System.out.println(encode("eb.jdbc.url|jdbc:jtds:sqlserver://10.71.88.214:1433/deerjet_eb"));
//		System.out.println(encode("cs.jdbc.url|jdbc:jtds:sqlserver://10.71.88.214:1433/deerjet_cs"));
//		System.out.println(encode("dms.jdbc.url|jdbc:jtds:sqlserver://10.71.88.214:1433/deerjet_dms"));
//		System.out.println(encode("fltplan.jdbc.url|jdbc:jtds:sqlserver://10.71.88.214:1433/fltplan_deer"));
//		System.out.println(encode("spm.jdbc.url|jdbc:jtds:sqlserver://10.71.88.214:1433/deerjet_spm"));
//		System.out.println(encode("ic.jdbc.url|jdbc:jtds:sqlserver://10.71.88.214:1433/deerjet_ic"));
		System.out.println(encode("mps.jdbc.url|jdbc:jtds:sqlserver://10.73.99.190:1433/Msg"));
		System.out.println(decode("e541917c3f2e0870ed0310c38d7eb973fd4f6eacc040b5fd0f9557378908100c3490baee086f7ad1901224deba510f8d384805f1e6d91f90"));
		
	}
}