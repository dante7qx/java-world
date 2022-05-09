package com.epolleo.util.common.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.epolleo.pub.att.bean.AttBean;
import com.epolleo.util.common.service.FileIOService;

public class FileIOAction {
	protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private FileIOService fileIOService;
    

    /**
     * @param req
     * @param resp
     * @param attId
     * @param type
     *            1.attachment;2.inline
     */
    public void doDownload(HttpServletRequest req, HttpServletResponse resp,
        @Param(name = "attId") Long attId,
        @Param(name = "isPrview") Boolean isPrview) {
        fileIOService.download(req, resp, attId, isPrview);
    }

    /**
     * @param req
     * @param resp
     * @param attBean
     */
    public void doDownloadAtts(HttpServletRequest req,
        HttpServletResponse resp, @Params AttBean attBean) {
        fileIOService.downloadAtts(req, resp, attBean);
    }
}