package com.epolleo.bp.pub;

import java.util.List;
import java.util.Map;

import com.alibaba.citrus.turbine.util.IParameterFilter;
import com.alibaba.citrus.turbine.util.IllegalParameterException;

public class ParameterFilter implements IParameterFilter {

    List<String> ignoreKeys;
    List<String> illegalValues;
    Map<String, String> filterMap;

    public void setIgnoreKeys(List<String> ignoreKeys) {
        this.ignoreKeys = ignoreKeys;
    }

    public void setIllegalValues(List<String> illegalValues) {
        this.illegalValues = illegalValues;
    }

    public void setFilterMap(Map<String, String> filterMap) {
        this.filterMap = filterMap;
    }

    @Override
    public String filterParameter(String key, String value) throws IllegalParameterException {
        if (ignoreKeys != null && ignoreKeys.contains(key)) {
            return value;
        }

        if (illegalValues != null) {
            for (String v : illegalValues) {
                if (value.contains(v)) {
                    throw new IllegalParameterException(value);
                }
            }
        }

        if (filterMap != null) {
            for (String k : filterMap.keySet()) {
                if (value.indexOf(k) != -1) {
                    value = value.replace(k, filterMap.get(k));
                }
            }
        }
        return value;
    }
}
