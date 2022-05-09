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

/**
 * SeqInfo接口用来描述一个序列的信息。
 * <p>
 * Date: 2012-10-15,15:01:50 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
public interface SeqDescr {
    /**
     * 获得序列的Id，可以是符合{@linkplain SeqFormatter}规约的字符串
     */
    String getId();

    /**
     * 获得序列的名称，在初始化序列时需要此信息。
     */
    String getName();

    /**
     * 序列增长时预取的个数。数值越大，性能越好，但浪费的可能性也加大
     */
    int getFetchSize();

    /**
     * 序列的初始值，一般为0或1。
     */
    long getValue();

    /**
     * 获得序列的格式化表达式，是符合{@linkplain SeqFormatter}规约的字符串
     */
    String getExpression();
}
