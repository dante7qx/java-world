package com.epolleo.bp.entity;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.citrus.util.io.ByteArrayInputStream;
import com.alibaba.fastjson.annotation.JSONType;

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
@JSONType(ignores = { "reportData", "report" })
public class BPReportRecord {
    @Id
    @Column(name = "RPT_SEQ_ID", unique = true, nullable = false)
    int seqId;
    @Column(name = "RPT_ID")
    String reportId;
    @Column(name = "RPT_KEY")
    String reportKey;
    @Column(name = "RPT_TITLE")
    String reportTitle;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "RPT_OBJECT", columnDefinition = "BLOB", length = 16777215)
    byte[] reportData;

    @Column(name = "CREATE_ORG_ID")
    Integer orgId;
    @Column(name = "CREATE_USER")
    String userId;
    @Column(name = "CREATE_TIME")
    Date createTime;

    @Transient
    Object reportObject;
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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    public byte[] getReportData() {
        return reportData;
    }

    public void setReportData(byte[] reportData) {
        this.reportData = reportData;
        reportObject = null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getReport() throws Exception {
        if (getReportData() == null)
            return null;
        if (reportObject != null) {
            return (T) reportObject;
        }
        ByteArrayInputStream bs = new ByteArrayInputStream(getReportData());
        ObjectInputStream os = new ObjectInputStream(new GZIPInputStream(bs));
        return (T) (reportObject = os.readObject());

    }

    public void setReport(Serializable report) throws Exception {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(new GZIPOutputStream(bs));
        os.writeObject(report);
        os.flush();
        os.close();
        setReportData(bs.toByteArray());
        reportObject = report;
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
