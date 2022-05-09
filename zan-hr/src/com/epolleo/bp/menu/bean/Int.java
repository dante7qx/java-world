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

package com.epolleo.bp.menu.bean;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Int
 * <p>
 * Date: 2013-02-01,16:04:57 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
public class Int {
    int value;

    static final Map<Integer, Int> map = new LinkedHashMap<Integer, Int>();

    private Int(Integer val) {
        value = val;
    }

    public static void clear() {
        map.clear();
    }

    public static Int getInt(Integer val) {
        Int i = map.get(val);
        if (i == null) {
            i = new Int(val);
            map.put(val, i);
        }
        return i;
    }

    public int val() {
        return value;
    }

    public static Map<Integer, Int> map() {
        return map;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public void setValue(int val) {
        value = val;
    }
}
