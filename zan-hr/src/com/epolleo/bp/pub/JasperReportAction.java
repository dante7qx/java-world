/*-
 * Copyright 2012 Owl Group
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 */

package com.epolleo.bp.pub;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.citrus.service.configuration.ProductionModeAware;
import com.epolleo.bp.dao.ReportDao;
import com.epolleo.bp.entity.BPReportRecord;

/**
 * JasperReportAction
 * <p>
 * Date: 2013-01-23,16:07:15 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
public class JasperReportAction implements ProductionModeAware {
    final Log log = LogFactory.getLog(getClass());
    @Resource
    ReportDao reportDao;

    @Resource
    DataSource dataSource;

    boolean productionMode;

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    protected BPReportRecord getReport(String rptId, String rptKey) {
        if (productionMode) {
            return reportDao.findReport(rptId, rptKey);
        }
        // always no record
        return null;
    }

    /**
     * 填充报表实例，并加入/更新到报表历史纪录中.
     * 
     * @param title
     *            报表标题，通常为该报表的菜单名称
     * @param rptId
     *            报表模板的相对路径
     * @param reportKey
     *            报表业务参数所组成的key，比如："startDate=20121101, endDate=20130101, orgId=130"
     * @param jasperPath
     *            报表模板的绝对路径
     * @param parameters
     *            报表参数
     * @param conn
     *            连接或数据源对象，可以为null
     * @return 报表实例
     */
    protected BPReportRecord fillAndUpdateReport(String title, String rptId, String reportKey, String jasperPath,
        Map<String, Object> parameters, Object conn) {
        try {
            File f = new File(jasperPath);
            parameters.put("SUBREPORT_DIR", f.getParentFile().getAbsolutePath() + File.separator);
            JasperPrint jasperPrint = null;
            if (conn instanceof Connection) {
                jasperPrint = JasperFillManager.fillReport(jasperPath, parameters, (Connection) conn);
            } else if (conn instanceof JRDataSource) {
                jasperPrint = JasperFillManager.fillReport(jasperPath, parameters, (JRDataSource) conn);
            } else {
                jasperPrint = JasperFillManager.fillReport(jasperPath, parameters);
            }
            BPReportRecord rpt = reportDao.findReport(rptId, reportKey);
            boolean save = rpt == null;
            if (save) {
                rpt = new BPReportRecord();
                rpt.setSeqId(reportDao.newSeqId());
            }
            rpt.setUserId(LoginUser.getCurrentUser().getUserId());
            rpt.setOrgId(LoginUser.getCurrentUser().getOrganizationId());
            rpt.setCreateTime(new Date());
            rpt.setReportTitle(title);
            rpt.setReportId(rptId);
            rpt.setReportKey(reportKey);
            rpt.setReport(jasperPrint);
            if (save) {
                reportDao.save(rpt);
            } else {
                reportDao.update(rpt);
            }
            return rpt;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
    /**
     * 填充报表实例，若报表历史中已存在，则返回历史纪录.
     * 
     * @param title
     *            报表标题，通常为该报表的菜单名称
     * @param rptId
     *            报表模板的相对路径
     * @param reportKey
     *            报表业务参数所组成的key，比如："startDate=20121101, endDate=20130101, orgId=130"
     * @param jasperPath
     *            报表模板的绝对路径
     * @param parameters
     *            报表参数
     * @param conn
     *            连接或数据源对象，可以为null
     * @return 报表实例
     */
    protected BPReportRecord fillReport(String title, String rptId, String reportKey, String jasperPath,
        Map<String, Object> parameters, Object conn) {
        try {
            BPReportRecord rpt = reportDao.findReport(rptId, reportKey);
            if (rpt == null) {
                File f = new File(jasperPath);
                parameters.put("SUBREPORT_DIR", f.getParentFile().getAbsolutePath() + File.separator);
                JasperPrint jasperPrint = null;
                if (conn instanceof Connection) {
                    jasperPrint = JasperFillManager.fillReport(jasperPath, parameters, (Connection) conn);
                } else if (conn instanceof JRDataSource) {
                    jasperPrint = JasperFillManager.fillReport(jasperPath, parameters, (JRDataSource) conn);
                } else {
                    jasperPrint = JasperFillManager.fillReport(jasperPath, parameters);
                }
                rpt = new BPReportRecord();
                rpt.setSeqId(reportDao.newSeqId());
                rpt.setUserId(LoginUser.getCurrentUser().getUserId());
                rpt.setOrgId(LoginUser.getCurrentUser().getOrganizationId());
                rpt.setCreateTime(new Date());
                rpt.setReportTitle(title);
                rpt.setReportId(rptId);
                rpt.setReportKey(reportKey);
                rpt.setReport(jasperPrint);
                reportDao.save(rpt);
            }
            return rpt;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
    /**
     * 填充报表实例，不读写报表历史纪录.
     * 
     * @param title
     *            报表标题，通常为该报表的菜单名称
     * @param rptId
     *            报表模板的相对路径
     * @param reportKey
     *            报表业务参数所组成的key，比如："startDate=20121101, endDate=20130101, orgId=130"
     * @param jasperPath
     *            报表模板的绝对路径
     * @param parameters
     *            报表参数
     * @param conn
     *            连接或数据源对象，可以为null
     * @return 报表实例
     */
    protected BPReportRecord newReport(String title, String rptId, String reportKey, String jasperPath,
        Map<String, Object> parameters, Object conn) {
        try {
            File f = new File(jasperPath);
            parameters.put("SUBREPORT_DIR", f.getParentFile().getAbsolutePath() + File.separator);
            JasperPrint jasperPrint = null;
            if (conn instanceof Connection) {
                jasperPrint = JasperFillManager.fillReport(jasperPath, parameters, (Connection) conn);
            } else if (conn instanceof JRDataSource) {
                jasperPrint = JasperFillManager.fillReport(jasperPath, parameters, (JRDataSource) conn);
            } else {
                jasperPrint = JasperFillManager.fillReport(jasperPath, parameters);
            }
            BPReportRecord rpt = new BPReportRecord();
            rpt.setSeqId(reportDao.newSeqId());
            rpt.setCreateTime(new Date());
            rpt.setReportTitle(title);
            rpt.setReportId(rptId);
            rpt.setReportKey(reportKey);
            rpt.setReport(jasperPrint);
            return rpt;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void setProductionMode(boolean mode) {
        productionMode = mode;
    }
}
