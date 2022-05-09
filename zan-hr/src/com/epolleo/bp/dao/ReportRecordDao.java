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

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.epolleo.bp.entity.ReportRecord;
import com.epolleo.bp.pub.HibernateDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

/**
 * ReportDao
 * <p>
 * Date: 2013-01-23,14:31:37 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ReportRecordDao extends HibernateDao<ReportRecord, Integer> {

    public PagingResult<ReportRecord> findPage(PagingForm pf) {
        DetachedCriteria c = DetachedCriteria.forClass(getEntityClass());
        if (pf.getSort() != null) {
            if ("asc".equals(pf.getSort())) {
                c.addOrder(Order.asc(pf.getSort()));
            } else {
                c.addOrder(Order.desc(pf.getSort()));
            }
        } else {
            c.addOrder(Order.desc("createTime"));
        }
        c.setProjection(Projections.rowCount());
        Number total = (Number) getHibernateTemplate().findByCriteria(c).get(0);
        c.setProjection(null);
        List<ReportRecord> list = (List<ReportRecord>) getHibernateTemplate().findByCriteria(c, pf.getSkip(),
            pf.getPageSize());
        return new PagingResult<ReportRecord>(list, total.intValue());
    }

    public PagingResult<ReportRecord> findUserReortHistory(PagingForm pf, int orgId) {
        DetachedCriteria c = DetachedCriteria.forClass(getEntityClass());
        c.add(Restrictions.sqlRestriction("{alias}.CREATE_ORG_ID in (select ORG_ID from BP_ORGAN where FULL_ID like "
            + "'%:" + orgId + ":%')"));
        if (pf.getSort() != null) {
            if ("asc".equals(pf.getSort())) {
                c.addOrder(Order.asc(pf.getSort()));
            } else {
                c.addOrder(Order.desc(pf.getSort()));
            }
        } else {
            c.addOrder(Order.desc("createTime"));
        }
        c.setProjection(Projections.rowCount());
        Number total = (Number) getHibernateTemplate().findByCriteria(c).get(0);
        c.setProjection(null);
        List<ReportRecord> list = (List<ReportRecord>) getHibernateTemplate().findByCriteria(c, pf.getSkip(),
            pf.getPageSize());
        return new PagingResult<ReportRecord>(list, total.intValue());
    }

    public PagingResult<ReportRecord> findUserReortHistory(PagingForm pf, int orgId, String rptId) {
        DetachedCriteria c = DetachedCriteria.forClass(getEntityClass());
        c.add(Restrictions.and(
            Restrictions.eq("reportId", rptId),
            Restrictions.sqlRestriction("{alias}.CREATE_ORG_ID in (select ORG_ID from BP_ORGAN where FULL_ID like "
                + "'%:" + orgId + ":%')")));
        if (pf.getSort() != null) {
            if ("asc".equals(pf.getSort())) {
                c.addOrder(Order.asc(pf.getSort()));
            } else {
                c.addOrder(Order.desc(pf.getSort()));
            }
        } else {
            c.addOrder(Order.desc("createTime"));
        }
        c.setProjection(Projections.rowCount());
        Number total = (Number) getHibernateTemplate().findByCriteria(c).get(0);
        c.setProjection(null);
        List<ReportRecord> list = (List<ReportRecord>) getHibernateTemplate().findByCriteria(c, pf.getSkip(),
            pf.getPageSize());
        return new PagingResult<ReportRecord>(list, total.intValue());
    }
}
