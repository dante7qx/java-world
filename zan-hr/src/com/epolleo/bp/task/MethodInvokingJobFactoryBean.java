/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package com.epolleo.bp.task;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.support.ArgumentConvertingMethodInvoker;
import org.springframework.util.MethodInvoker;

public class MethodInvokingJobFactoryBean extends
    ArgumentConvertingMethodInvoker implements FactoryBean<Runnable>,
    InitializingBean {
    MethodInvokingJob job;

    public String toString() {
        return String.valueOf(getTargetObject());
    }

    public void afterPropertiesSet() throws ClassNotFoundException,
        NoSuchMethodException {
        prepare();
        job = new MethodInvokingJob(this);
    }

    public Runnable getObject() {
        return this.job;
    }

    public Class<? extends Runnable> getObjectType() {
        return MethodInvokingJob.class;
    }

    public boolean isSingleton() {
        return true;
    }

    /**
     * Quartz Job implementation that invokes a specified method. Automatically
     * applied by MethodInvokingJobDetailFactoryBean.
     */
    public static class MethodInvokingJob implements Runnable {

        private MethodInvoker methodInvoker;

        /**
         * Set the MethodInvoker to use.
         */
        MethodInvokingJob(MethodInvoker methodInvoker) {
            this.methodInvoker = methodInvoker;
        }

        /**
         * Invoke the method via the MethodInvoker.
         */
        public void run() {
            try {
                methodInvoker.invoke();
            } catch (InvocationTargetException ex) {
                throw new RuntimeException(ex.getTargetException());
            } catch (RuntimeException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        public String toString() {
            return methodInvoker.toString();
        }
    }
}
