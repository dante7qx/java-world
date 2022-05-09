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

package com.epolleo.bp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.epolleo.bp.entity.BPReportRecord;
import com.epolleo.bp.pub.HibernateDao;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.dao.SeqDao;

/**
 * ReportDao
 * <p>
 * Date: 2013-01-23,14:31:37 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
public class ReportDao extends HibernateDao<BPReportRecord, Integer> {

    @Resource
    SeqDao seqDao;

    public int newSeqId() {
        return (int) seqDao.getNewId(IdKind.ReportRecordId);
    }

    public BPReportRecord findReport(String rptId, String rptKey) {
        DetachedCriteria c = DetachedCriteria.forClass(getEntityClass());
        c.add(Restrictions.and(Restrictions.eq("reportId", rptId), Restrictions.eq("reportKey", rptKey)));

        @SuppressWarnings("unchecked")
        List<BPReportRecord> list = getHibernateTemplate().findByCriteria(c, 0, 1);
        if (list == null || list.size() < 1)
            return null;
        return list.get(0);
    }

}
