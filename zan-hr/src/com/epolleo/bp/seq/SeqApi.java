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

package com.epolleo.bp.seq;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibatis.client.SqlMapClient;

import com.ibatis.sqlmap.client.SqlMapSession;

/**
 * SeqApi是获取号码的总入口，具有全局性质、并发性质。
 * <p>
 * Date: 2012-10-15,14:02:25 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
public class SeqApi {
    static final Log logger = LogFactory.getLog(SeqApi.class);

    public static class SeqInfo {
        public String id;
        public long delta;
        public String name;
        public String expr;

        public volatile long value;
        volatile long threshold;

        public boolean hasNext() {
            return delta == 0 || value < threshold;
        }

        public synchronized Object next(Date date, Object... args) {
            long v = value;
            if (delta > 0)
                value++;
            if (expr == null || expr.length() == 0) {
                return v;
            }
            return SeqFormatter.getSeqFormatter(expr).format(v, date, args);
        }
    }

    static Map<String, SeqInfo> currents = new LinkedHashMap<String, SeqInfo>() {

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 2041496185057571757L;

        protected boolean removeEldestEntry(Map.Entry<String, SeqInfo> eldest) {
            return size() > 4096;
        }
    };

    /**
     * 根据key，获取下一个Id对象
     * 
     * @param id
     *            序号的Id
     * @param sqlc
     *            数据库访问对象
     * @return 返回下一个Id，String或Long对象
     * 
     * @throws SQLException
     */
    static Object getNext(SqlMapClient db, SeqDescr desc, Object... args) throws SQLException {
        Date date = new Date();
        
        String key = SeqFormatter.getSeqFormatter(desc.getId()).format(0, date, args);
        if (key == null || key.length() < 0) {
            throw new IllegalArgumentException();
        }
        SqlMapSession sqlc = null;
        SeqInfo seq = null;
        try {
            /**
             * sync whole jvm ("#SeqAPI#_" + key).intern()
             */
            synchronized (currents) {
                seq = currents.get(key);
                if (seq == null) {
                    sqlc = openSession(db);
                    seq = (SeqInfo) queryForObjectArgs(sqlc, "getSeqInfo", key);
                    if (seq == null) {
                        if (desc.getName() == null || desc.getName().length() < 0 || desc.getFetchSize() < 0
                            || desc.getValue() < 0) {
                            throw new IllegalArgumentException();
                        }
                        try {
                            updateArgs(sqlc, "insertNewSeq", key, desc.getName(), desc.getValue(), desc.getFetchSize(),
                                desc.getExpression());
                        } catch (SQLException e) {
                            logger.warn(e.getMessage(), e);
                        }
                        seq = (SeqInfo) queryForObjectArgs(sqlc, "getSeqInfo", key);
                        if (seq == null) {
                            throw new SQLException("Failed to init sequence [" + key + "] data: val=" + desc.getValue()
                                + ", fetch=" + desc.getFetchSize());
                        }
                    }
                    if (seq.delta < 0 || seq.value < 0) {
                        throw new SQLException("Illegal sequence [" + key + "] data: val=" + seq.value + ", fetch="
                            + seq.delta);
                    }

                    currents.put(key, seq);
                } else if (seq.delta == 0) {
                    if (seq.threshold++ > 1000) {
                        // force db sync
                        currents.remove(key);
                    }
                }
            }

            synchronized (seq) {
                if (seq.hasNext()) {
                    return seq.next(date, args);
                }
                if (sqlc == null) {
                    sqlc = openSession(db);
                }
                for (int retry = 0; retry < 100; retry++) {
                    if (seq.threshold == 0) {
                        seq.threshold = seq.value + seq.delta;
                    } else {
                        SeqInfo s = (SeqInfo) queryForObjectArgs(sqlc, "getSeqInfo", key);
                        if (s.delta < 0 || s.value < 0) {
                            throw new SQLException("Illegal sequence [" + key + "] data: val=" + s.value + ", fetch="
                                + s.delta);
                        }
                        seq.delta = s.delta;
                        seq.value = s.value;
                        seq.expr = s.expr;
                        if (seq.delta == 0) {
                            return seq.next(date, args);
                        }
                        seq.threshold = seq.value + seq.delta;
                    }
                    int row = updateArgs(sqlc, "tryUpdateSeqValue", seq.threshold, seq.id, seq.value);
                    if (row == 1) {
                        return seq.next(date, args);
                    } else {
                        logger.warn("Failed to update sequence [" + key + "] data: curr=" + seq.value + ", next="
                            + seq.threshold);
                    }
                }
                throw new SQLException("Failed to update sequence [" + key + "] data: curr=" + seq.value + ", next="
                    + seq.threshold);
            }
        } finally {
            if (sqlc != null) {
                try {
                    sqlc.getCurrentConnection().close();
                } catch (Exception e) {
                }
                try {
                    sqlc.close();
                } catch (Exception e) {
                }
            }
        }
    }

    static SqlMapSession openSession(SqlMapClient db) throws SQLException {
        Connection conn = db.getRealDataSource().getConnection();
        conn.setReadOnly(false);
        conn.setAutoCommit(true);
        return db.openSession(conn);
    }

    static Object queryForObjectArgs(SqlMapSession sqlc, String id, Object... args) throws SQLException {
        Map<?, ?> map = toParameter(args);
        return sqlc.queryForObject(id, map);
    }

    @SuppressWarnings("unchecked")
    static Map<?, ?> toParameter(Object[] args) {
        Map map = new HashMap();
        for (int i = 0; args != null && i < args.length; i++) {
            if (args[i] != null) {
                map.put(String.valueOf(i + 1), args[i]);
            }
        }
        return map;
    }

    static int updateArgs(SqlMapSession sqlc, String id, Object... args) throws SQLException {
        Map<?, ?> map = toParameter(args);
        return sqlc.update(id, map);
    }

    public static String getNextString(SqlMapClient db, SeqDescr desc, Object... args) throws SQLException {
        return getNext(db, desc, args).toString();
    }

    public static long getNextLong(SqlMapClient db, SeqDescr desc, Object... args) throws SQLException {
        Object id = getNext(db, desc, args);
        if (id instanceof Long) {
            return ((Long) id).longValue();
        }
        return Long.parseLong(id.toString());
    }

    public static int getNextInt(SqlMapClient db, SeqDescr desc, Object... args) throws SQLException {
        Object id = getNext(db, desc, args);
        if (id instanceof Long) {
            return ((Long) id).intValue();
        }
        return Integer.parseInt(id.toString());
    }
}
