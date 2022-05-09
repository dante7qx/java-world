package com.epolleo.bp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * BPReportRecord
 * <p>
 * Date: 2013-01-23,13:04:22 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
@Entity
@Table(name = "bp_rpt_record")
public class ReportRecord {
    @Id
    @Column(name = "RPT_SEQ_ID", unique = true, nullable = false)
    int seqId;
    @Column(name = "RPT_ID")
    String reportId;
    @Column(name = "RPT_KEY")
    String reportKey;
    @Column(name = "RPT_TITLE")
    String reportTitle;
    @Column(name = "CREATE_ORG_ID")
    Integer orgId;
    @Column(name = "CREATE_USER")
    String userId;
    @Column(name = "CREATE_TIME")
    Date createTime;

    @Transient
    String orgName;

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportKey() {
        return reportKey;
    }

    public void setReportKey(String reportKey) {
        this.reportKey = reportKey;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String title) {
        this.reportTitle = title;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

}
