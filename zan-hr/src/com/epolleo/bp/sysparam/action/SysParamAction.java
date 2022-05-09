package com.epolleo.bp.sysparam.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epolleo.bp.pub.AbstractAction;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.sysparam.bean.SysParamBean;
import com.epolleo.bp.sysparam.service.SysParamService;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.buffered.BufferedRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;

/**
 * 
 * SysParamAction Date:2012-07-13,17:02:22 +0800
 * 
 * @author nj.sun
 * @version 1.0
 */
@Function()
@FunctionDir(id="bp.sysparam", name="全局参数")
public class SysParamAction extends AbstractAction {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    SysParamService sysParamService;

    @Resource
    private BufferedRequestContext buffered;

    /**
     * update sysParam
     */
    @Function("bp.sysparam.update")
    @FunctionName("全局参数更新")
    public void doUpdate(HttpServletRequest req, Context context, @Params SysParamBean sysParamGen) {
        sysParamService.update(sysParamGen, LoginUser.getCurrentUser().getUserId());
        context.put("json", sysParamGen);
    }

    /**
     * query sysParam
     */
    @Function("bp.sysparam.query")
    @FunctionName("全局参数查询")
    public void doQuery(Context context, @Params PagingForm form, @Params SysParamBean sysParamGen) {
        HashMap<String, Object> filter = new HashMap<String, Object>();

        Object paramKey = sysParamGen.getParamKey();
        if (paramKey != null) {
            if (paramKey instanceof String) {
                filter.put("paramKey", "%" + paramKey + "%");
            } else {
                filter.put("paramKey", paramKey);
            }
        }
        Object paramName = sysParamGen.getParamName();
        if (paramName != null) {
            if (paramName instanceof String) {
                filter.put("paramName", "%" + paramName + "%");
            } else {
                filter.put("paramName", paramName);
            }
        }
        form.setFilterMap(filter);
        PagingResult<SysParamBean> result = sysParamService.findPaging(form);
        context.put("json", result);
    }

    /**
     * insert sysParam
     */
    @Function("bp.sysparam.insert")
    @FunctionName("全局参数保存")
    public void doSave(HttpServletRequest req, Context context, @Params SysParamBean sysParamGen) {

        sysParamService.save(sysParamGen, LoginUser.getCurrentUser().getUserId());
        context.put("json", sysParamGen);
    }

    /**
     * delete sysParam
     */
    @Function("bp.sysparam.delete")
    @FunctionName("全局参数删除")
    public void doDelete(Context context, @Param(name = "id") String id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        int delete = sysParamService.delete(id);
        result.put("success", delete == 1);
        context.put("json", result);
    }

    /**
     * 导出菜单SQL并提供下载
     */
    @Function("bp.sysparam.download")
    @FunctionName("全局参数下载")
    public void doDownload(HttpServletRequest req, HttpServletResponse resp) {
        // 附件下载/预览时关闭Buffering
        buffered.setBuffering(false);
        List<SysParamBean> list = sysParamService.getSysParamAll();

        StringBuffer buf = new StringBuffer(
            "insert into BP_SYS_PARAM(PARAM_KEY, PARAM_NAME, PARAM_VALUE, PARAM_TYPE, DEFAULT_VALUE, SYNC_FLAG) values");

        for (SysParamBean m : list) {
            buf.append("\r\n\t('").append(m.getParamKey()).append("', ");
            buf.append("'").append(m.getParamName()).append("', ");
            buf.append("'").append(m.getParamValue()).append("', ");
            buf.append(m.getParamType()).append(", ");
            if (m.getDefaultValue() == null) {
                buf.append("NULL, ");
            } else {
                buf.append("'").append(m.getDefaultValue()).append("', ");
            }
            buf.append(m.isSyncFlag()).append("),");
        }

        int idx = buf.lastIndexOf(",");
        if (idx > 0)
            buf.replace(idx, idx + 1, ";");

        try {
            String fileName = encodeFileName("bp_sysparam_insert.sql", req);// 附件描述默认为文件名（无后缀）
            resp.setContentType("text/plain;charset=utf-8");
            String contentDisposition = "attachment";// "inline"

            resp.setHeader("Content-disposition", contentDisposition + ";filename=" + fileName);
            resp.getOutputStream().write(buf.toString().getBytes("utf-8"));
            resp.getOutputStream().close();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    String encodeFileName(String fileName, HttpServletRequest req) {
        String userAgent = req.getHeader("user-agent");
        boolean isMSIE = userAgent.contains("MSIE");
        boolean isMSIE6 = userAgent.contains("MSIE 6.0");
        String returnFileName = "";
        try {
            if (isMSIE) {// IE
                returnFileName = URLEncoder.encode(fileName, "UTF-8");
                if (returnFileName.length() > 150 && isMSIE6) {// 处理IE6 无法下载问题 "KB816868"
                    returnFileName = returnFileName.substring(0, 149);// 截取前150位
                } else {
                    returnFileName = StringUtils.replace(returnFileName, "+", "%20");
                }
            } else {// FF
                returnFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
            }
        } catch (UnsupportedEncodingException e) {
            logger.warn(e.getMessage(), e);
        }
        return returnFileName;
    }
}
