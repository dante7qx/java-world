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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

import com.alibaba.citrus.service.configuration.ProductionModeAware;
import com.epolleo.bp.task.bean.TaskDefBean;
import com.epolleo.bp.task.dao.TaskDefDao;
import com.epolleo.bp.util.SystemInfo;

/**
 * TaskSchedular
 * <p>
 * Date: 2012-11-09,11:17:17 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
public class TaskScheduler implements ApplicationContextAware, ProductionModeAware, ServletContextAware, Runnable {

    final Log logger = LogFactory.getLog(getClass());
    boolean productionMode;
    ApplicationContext applicationContext;
    ServletContext servletContext;
    Map<String, Runnable> jobs;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Thread thread;
    long minWait = 45 * 1000L;
    long maxWait = 5 * 60 * 1000L;
    @Resource
    TaskDefDao taskDefDao;

    public void setMinWait(Long ms) {
        if (ms != null && ms.longValue() > 0)
            minWait = ms;
    }

    public void setMaxWait(Long ms) {
        if (ms != null && ms.longValue() > 0)
            maxWait = ms;
    }

    public boolean isProductionMode() {
        return productionMode;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setServletContext(ServletContext ctx) {
        servletContext = ctx;
    }

    public void setProductionMode(boolean productionMode) {
        this.productionMode = productionMode;

        if (thread == null) {
            thread = new Thread(this);
            thread.setName("BP_JobSheduler-" + Integer.toHexString(hashCode()));
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.applicationContext = ac;
    }

    public void setJobs(Map<String, Runnable> jobs) {
        this.jobs = jobs;
    }

    public static String getBpId(ServletContext sc) {
        Object id = sc.getAttribute("bp.shedId");
        if (id == null || !(id instanceof String)) {
            id = "#";
        }
        String bpId = (String) id;
        if (bpId.length() < 1) {
            bpId = "#";
        }
        return bpId;
    }

    public void run() {
        if (minWait < 1000L) {
            minWait = 1000L;
        }
        if (maxWait <= minWait) {
            maxWait = minWait * 2;
        }

        try {
            SystemInfo.dumpSysInfo();
            SystemInfo.doDiagnostic();
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        while (jobs != null && jobs.size() > 0) {
            try {
                Thread.sleep(minWait);
            } catch (Throwable e) {
            }
            String bpId = getBpId(servletContext);
            try {
                Date date = new Date();
                String dstr = sdf.format(date);
                List<TaskDefBean> list = taskDefDao.findAllTask(date, bpId);
                Date ndate = new Date(date.getTime() + maxWait);
                for (final TaskDefBean td : list) {
                    try {
                        String jobId = td.getBeanId();
                        final Runnable r = jobs.get(jobId);
                        if (r == null) {
                            logger.warn("Job [" + jobId + "] not exits.");
                            // td.setEnable(false);
                            // td.setLastTime(date);
                            td.setLastInfo("Job [" + jobId + "] not exits at [" + bpId + "].");
                            taskDefDao.updateTaskLastInfo(td);
                            continue;
                        }
                        if (shouldFire(td, date)) {
                            // td.setLastTime(date);
                            td.setLastInfo("Job running at " + dstr);
                            taskDefDao.updateTaskLastInfo(td);
                            if (!taskDefDao.tryUpdate(td, date)) {
                                continue;
                            }
                            Thread t = new Thread("BP_JobSheduler#" + td.getBeanId()) {
                                public void run() {
                                    String err = null;
                                    try {
                                        if (productionMode) {
                                            logger.info("Start run job " + r);
                                            r.run();
                                            logger.info("End run job " + r);
                                        } else {
                                            logger.warn("Mock run job " + r);
                                            Thread.sleep(5000L);
                                        }
                                    } catch (Throwable e) {
                                        err = e.getMessage() + " of " + e.getClass().getName();
                                        logger.warn(e.getMessage(), e);
                                    }

                                    td.setLastInfo("Job end at " + sdf.format(new Date())
                                        + (err == null ? "" : "\n" + err));
                                    taskDefDao.updateTaskLastInfo(td);
                                }
                            };
                            t.setDaemon(true);
                            t.start();
                        }
                        if (td.getNextTime() != null && td.getNextTime().after(date)) {
                            if (ndate.after(td.getNextTime())) {
                                ndate = td.getNextTime();
                            }
                        }
                    } catch (Throwable t) {
                        logger.warn(t.getMessage(), t);
                    }
                }
                long t = ndate.getTime() - date.getTime() - minWait;
                if (t > 0) {
                    try {
                        Thread.sleep(t);
                    } catch (Throwable e) {
                    }
                }
            } catch (Throwable t) {
                try {
                    logger.warn(t.getMessage(), t);
                } catch (Throwable e) {
                }
            }
        }
    }

    boolean shouldFire(TaskDefBean td, Date date) {
        if (td.getShedType() == 2) {
            try {
                if (CronExpression.isValidExpression(td.getShedExpr())) {
                    CronExpression ce = new CronExpression(td.getShedExpr());
                    Date ld = td.getLastTime();
                    if (ld == null) {
                        ld = new Date(0);
                    }
                    Date d = ce.getNextValidTimeAfter(ld);
                    if (date.after(d)) {
                        td.setNextTime(ce.getNextValidTimeAfter(date));
                        return true;
                    }
                }
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
        // ## for other than cron
        return false;
    }

}
