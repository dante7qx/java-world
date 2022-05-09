package com.dante.boss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FunctionService {
	private static Connection ocsConn = BossDbUtil.getOcsConn();
	private static Connection crmConn = BossDbUtil.getCrmConn();
	private static int index = 0;
	
	public List<FunctionVo> getFuncsByCode(String dbsource, String funcCode) throws Exception {
		index = 0;
		List<FunctionVo> funcs = new ArrayList<FunctionVo>();
		FunctionVo vo = null;
		if(FunctionExport.OCS.equals(dbsource)) {
			vo = this.getFuncByCode(ocsConn, funcCode);
			this.buildFunctionVo(ocsConn, vo, vo.getFuncCode(), vo.getLevel());
			BossDbUtil.closeOcsConn();
		} else if(FunctionExport.CRM.equals(dbsource)) {
			vo = this.getFuncByCode(crmConn, funcCode);
			this.buildFunctionVo(crmConn, vo, vo.getFuncCode(), vo.getLevel());
			BossDbUtil.closeCrmConn();
		}
		funcs.add(vo);
		return funcs;
	}
	
	public List<FunctionVo> getFuncsByParentCode(String dbsource, String parentCode) throws Exception {
		index = 0;
		List<FunctionVo> funcs = null;
		if(FunctionExport.OCS.equals(dbsource)) {
			funcs = this.getFuncByParentCode(ocsConn, parentCode);
			for (FunctionVo vo : funcs) {
				this.buildFunctionVo(ocsConn, vo, vo.getFuncCode(), vo.getLevel());
			}
			BossDbUtil.closeOcsConn();
		} else if(FunctionExport.CRM.equals(dbsource)) {
			funcs = this.getFuncByParentCode(crmConn, parentCode);
			for (FunctionVo vo : funcs) {
				this.buildFunctionVo(crmConn, vo, vo.getFuncCode(), vo.getLevel());
			}
			BossDbUtil.closeCrmConn();
		}
		return funcs;
	}
	
	private FunctionVo getFuncByCode(Connection conn, String funcCode) throws Exception {
		FunctionVo vo = null;
		String sql = "select FUNC_CODE, FUNC_NAME from BP_FUNCTION where FUNC_CODE=? order by FUNC_ORDER asc";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, funcCode);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
        	vo = new FunctionVo();
        	vo.setFuncCode(rs.getString(1));
        	vo.setFuncName(rs.getString(2));
        }
        rs.close();    
        pstmt.close();
		return vo;
	}
	
	private List<FunctionVo> getFuncByParentCode(Connection conn, String parentFuncCode) throws Exception {
		List<FunctionVo> funcs = new ArrayList<FunctionVo>();
		String sql = "select FUNC_CODE, FUNC_NAME from BP_FUNCTION where PARENT=? order by FUNC_ORDER asc";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, parentFuncCode);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
        	FunctionVo vo = new FunctionVo();
        	vo.setFuncCode(rs.getString(1));
        	vo.setFuncName(rs.getString(2));
        	funcs.add(vo);
        }
        rs.close();    
        pstmt.close();
		return funcs;
	}
	
	private void buildFunctionVo(Connection conn, FunctionVo pVo, String pCode, int plevel) throws Exception {
		pVo.setIndex(index);
		List<FunctionVo> childs = this.getFuncByParentCode(conn, pCode);
		if(childs != null && !childs.isEmpty()) {
			for (FunctionVo child : childs) {
				index++;
				child.setIndex(index);
				child.setLevel(plevel + 1);
				List<FunctionVo> subs = this.getFuncByParentCode(conn, child.getFuncCode());
				if(subs != null && !subs.isEmpty()) {
					pVo.getChilds().add(child);
					buildFunctionVo(conn, child, child.getFuncCode(), child.getLevel());
				} else {
					pVo.getChilds().add(child);
				}
			}
		}
	}
	
}
