/**
 * All rights reserved.
 * ReportRecordService.java
 * Date: 2013-02-26
 */
package com.epolleo.bp.report.service;

import java.util.Map;

import javax.annotation.Resource;

import com.epolleo.bp.dao.ReportDao;
import com.epolleo.bp.dao.ReportRecordDao;
import com.epolleo.bp.entity.BPReportRecord;
import com.epolleo.bp.entity.ReportRecord;
import com.epolleo.bp.org.bean.OrgNodeBean;
import com.epolleo.bp.org.dao.ibatis.OrgDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

/**
 * <p>
 * 报表记录的业务类
 * </p>
 * 
 * @version 1.0
 */
public class ReportRecordService {

    @Resource
    ReportDao reportDao;
    @Resource
    ReportRecordDao reportRecordDao;
    @Resource
    OrgDao orgDao;

    public PagingResult<ReportRecord> findPaging(PagingForm pf) {
        PagingResult<ReportRecord> pr = reportRecordDao.findPage(pf);
        Map<Integer, OrgNodeBean> om = orgDao.getOrgNodeBeanMap();
        for (ReportRecord r : pr.getRows()) {
            if (r.getOrgId() != null) {
                OrgNodeBean ob = om.get(r.getOrgId());
                if (ob != null) {
                    r.setOrgName(ob.getOrgName());
                }
            }
        }
        return pr;
    }

    public PagingResult<ReportRecord> findUserReortHistory(PagingForm pf, int orgId) {
        PagingResult<ReportRecord> pr = reportRecordDao.findUserReortHistory(pf, orgId);
        Map<Integer, OrgNodeBean> om = orgDao.getOrgNodeBeanMap();
        for (ReportRecord r : pr.getRows()) {
            if (r.getOrgId() != null) {
                OrgNodeBean ob = om.get(r.getOrgId());
                if (ob != null) {
                    r.setOrgName(ob.getOrgName());
                }
            }
        }
        return pr;
    }

    public PagingResult<ReportRecord> findUserReortHistory(PagingForm pf, int orgId, String rptId) {
        PagingResult<ReportRecord> pr = reportRecordDao.findUserReortHistory(pf, orgId, rptId);
        Map<Integer, OrgNodeBean> om = orgDao.getOrgNodeBeanMap();
        for (ReportRecord r : pr.getRows()) {
            if (r.getOrgId() != null) {
                OrgNodeBean ob = om.get(r.getOrgId());
                if (ob != null) {
                    r.setOrgName(ob.getOrgName());
                }
            }
        }
        return pr;
    }

    public BPReportRecord getReportById(int id) {
        return reportDao.findById(id);
    }

    public int delete(int id) {
        BPReportRecord obj = reportDao.findById(id);
        if (obj != null) {
            reportDao.delete(obj);
            return 1;
        }
        return 0;
    }

}