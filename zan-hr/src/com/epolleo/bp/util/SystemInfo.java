/*-
 * Copyright (c) 2008-2009 Owl Group
 * All rights reserved. 
 * SystemInfo.java
 * Date: 2009-10-10
 */
package com.epolleo.bp.util;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.management.ManagementFactory;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SystemInfo
 * <p>
 * Date: 2009-10-10, 12:47:55 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
public class SystemInfo {

    protected static final Log logger = LogFactory.getLog(SystemInfo.class);
    static final String[] CORE_PROP_KEYS = { "java.version", "java.vendor",
        "java.home", "java.vm.specification.version", "java.vm.version",
        "java.vm.name", "java.class.version", "java.class.path",
        "java.library.path", "java.io.tmpdir", "java.compiler",
        "java.ext.dirs", "java.protocol.handler.pkgs", "sun.arch.data.model",
        "sun.cpu.endian", "sun.cpu.isalist", "os.name", "os.arch",
        "os.version", "file.encoding", "file.separator", "path.separator",
        "line.separator", "user.name", "user.language", "user.home",
        "user.dir", "catalina.home" };

    /**
     * Eval Entry.
     */
    public static void main(String[] args) {
        doDiagnostic();
        dumpSysInfo();
    }

    public static void doDiagnostic() {
        UncaughtExceptionHandler h = new UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                logger.fatal("UncaughtException occurred form: " + t);
                logger.fatal(e.getMessage(), e);
                dumpSysInfo();
                dumpThreads();
                if (e instanceof OutOfMemoryError) {
                    logger.fatal("Heap dump start...");
                    String pid = ManagementFactory.getRuntimeMXBean().getName();
                    pid = pid.substring(0, pid.indexOf("@"));
                    String fileName = dumpJvmHeap(pid);
                    if (fileName != null) {
                        logger.fatal("Heap dump Okey! file = " + fileName);
                    } else {
                        logger.fatal("Heap dump failed! unkown jvm type.");
                    }
                }
            }
        };
        Thread.setDefaultUncaughtExceptionHandler(h);

        Runtime.getRuntime().addShutdownHook(new Thread("ExitHook") {
            public void run() {
                try {
                    logger.info("Program halt.");
                    dumpThreads();
                    Thread t = new Thread("Halter") {
                        public void run() {
                            try {
                                Thread.sleep(15000);
                            } catch (Exception e) {
                            }
                            try {
                                Runtime.getRuntime().halt(1234);
                            } catch (Exception e) {
                                logger.warn(e.getMessage(), e);
                                Runtime.getRuntime().halt(23);
                            }
                        }
                    };
                    t.setDaemon(true);
                    t.start();
                } catch (Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        });
    }

    public static void dumpThreads() {
        logger.fatal("Thread dump start...");
        Map<Thread, StackTraceElement[]> all = Thread.getAllStackTraces();
        for (Thread t : all.keySet()) {
            logger.fatal(t);
            StackTraceElement[] stes = all.get(t);
            for (StackTraceElement ste : stes) {
                logger.fatal("\t" + ste);
            }
        }
        logger.fatal("Thread dump Okey!");
    }

    public static String dumpJvmHeap(String pid) {
        try {
            System.gc();
        } catch (Exception e) {
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.ibm.jvm.Dump");
        } catch (Exception e) {
            clazz = null;
        }
        if (clazz != null) {
            try {
                clazz.getMethod("HeapDump").invoke(null);
                return "IBM JVM HEAP DUMP FILE";
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
        try {
            clazz = Class.forName("sun.management.ManagementFactory");
        } catch (Exception e) {
            clazz = null;
        }
        if (clazz != null) {
            if (pid == null || pid.length() < 1) {
                pid = "unknown_java_proc";
            } else {
                pid = "java_proc_" + pid;
            }
            String path = System.getProperty("catalina.home",
                System.getProperty("user.dir"));
            File file = new File(path, pid + ".dump");
            try {
                Object dmxb = clazz.getMethod("getDiagnosticMXBean").invoke(
                    null);
                dmxb.getClass()
                    .getMethod("dumpHeap", String.class, boolean.class)
                    .invoke(dmxb, file.getAbsolutePath(), true);
            } catch (Exception e) {
                // since 1.7 convert to sun.management.ManagementFactoryHelper
                try {
                    clazz = Class
                        .forName("sun.management.ManagementFactoryHelper");
                } catch (ClassNotFoundException e1) {
                    logger.error(e1.getMessage(), e1);
                    return null;
                }
                if (clazz != null) {
                    try {
                        Object dmxb = clazz.getMethod("getDiagnosticMXBean")
                            .invoke(null);
                        dmxb.getClass()
                            .getMethod("dumpHeap", String.class, boolean.class)
                            .invoke(dmxb, file.getAbsolutePath(), true);
                    } catch (Exception e2) {
                        logger.error(e2.getMessage(), e2);
                        return null;
                    }
                }
            }
            return file.getAbsolutePath();
        }
        return null;
    }

    public static void dumpSysInfo() {
        logger.info("---------- System_Environments ------------");
        logger.info("PATH=" + System.getenv("PATH"));
        logger.info("LD_LIBRARY_PATH=" + System.getenv("LD_LIBRARY_PATH"));
        logger.info("CLASSPATH=" + System.getenv("CLASSPATH"));
        logger.info("LANG=" + System.getenv("LANG"));
        logger.info("DISPLAY=" + System.getenv("DISPLAY"));
        logger.info("----------   JVM_Properties    ------------");
        for (String key : CORE_PROP_KEYS) {
            logger.info(key + "=" + getProperty(key));
        }
    }

    private static String getProperty(String key) {
        String val = System.getProperty(key);
        if (val == null) {
            return "<N/A>";
        } else {
            return val.replace("\n", "\\n").replace("\r", "\\r");
        }
    }
}
