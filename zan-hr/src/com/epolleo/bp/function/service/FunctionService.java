/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * FunctionService.java
 */
package com.epolleo.bp.function.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import com.epolleo.bp.function.bean.FuncTreeAttributeBean;
import com.epolleo.bp.function.bean.FunctionBean;
import com.epolleo.bp.function.bean.FunctionTreeBean;
import com.epolleo.bp.function.dao.FunctionDao;
import com.epolleo.bp.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.util.FunctionUtil;

/**
 * FunctionService
 * <p>
 * Date: 2012-07-10,15:11:21 +0800
 * 
 * @author nj.sun
 * @version 1.0
 */
public class FunctionService {
    private Logger logger = LoggerFactory.getLogger(FunctionService.class);

    @Resource
    private FunctionDao functionDao;

    /**
     * Update a part of Function
     */
    public int updateNote(FunctionBean functionBean, String userId) {
        return functionDao.updateNote(functionBean, userId);
    }

    /**
     * Insert Function
     */
    public FunctionBean save(FunctionBean functionBean, String userId) {
        return functionDao.save(functionBean, userId);
    }

    /**
     * Delete Function
     */
    public int delete(String idStr) {
        return functionDao.delete(idStr);
    }

    /**
     * get Function By Code
     */
    public List<FunctionBean> query(String funcCode) {
        return functionDao.query(funcCode);
    }

    /**
     * Synchronize with database
     */
    public int syncFunction(String userId) {
        // db function
        Map<String, String> fMap = functionDao.getFuncMap();
        // api function
        Map<String, String> fSet = FunctionUtil.allFunctionSet();
        Map<String, String> fNames = FunctionUtil.allFunctionNameSet();
        logger.debug("allFunctionSet -> " + fSet);
        logger.debug("allFunctionNameSet -> " + fNames);

        Map<String, String> newMap = new HashMap<String, String>();
        for (String str : fSet.keySet()) {
            StringTokenizer st = new StringTokenizer(str, ".");
            String f = null;
            while (st.hasMoreTokens()) {
                if (f == null) {
                    f = st.nextToken();
                } else {
                    f += "." + st.nextToken();
                }
                if (!fMap.containsKey(f)) {
                    newMap.put(f, f);
                }
            }
        }
        HashSet<String> all = new HashSet<String>();
        all.addAll(fSet.keySet());
        all.addAll(newMap.keySet());
        all.add("public");
        Date t = DateUtils.getCurrentDate();
        int count = functionDao.updateFuncStateBatch(fMap, all, userId, t);
        
        List<FunctionBean> list = new ArrayList<FunctionBean>();
        for (String nf : newMap.keySet()) {
            FunctionBean model = new FunctionBean();
            model.setFuncCode(nf);
            String name = fNames.get(nf);
            if (name == null || name.length() == 0) {
                model.setFuncName(nf);
            } else {
                model.setFuncName(name);
            }
            model.setState(true);
            model.setFuncOrder(0);
            int idx = nf.lastIndexOf('.');
            if (idx > 0) {
                model.setParent(nf.substring(0, idx));
            } else {
                model.setParent("-1");
            }
            model.setFuncNote(fSet.get(nf));
            list.add(model);
            count++;
        }
        functionDao.saveBatch(list, userId);
        return count;
    }

    /**
     * Get function tree
     */
    public List<FunctionTreeBean> getFunctionTree() {
        List<FunctionBean> functionDbList = functionDao.getFuncAll();
        List<FunctionTreeBean> treeList = new ArrayList<FunctionTreeBean>();
        for (FunctionBean bean : functionDbList) {
            if ("-1".equals(bean.getParent())) {
                FunctionTreeBean treeModel = new FunctionTreeBean();
                treeModel.setId(bean.getFuncCode());
                treeModel.setText(bean.getFuncName());
                treeModel.setChildren(getChildren(bean, functionDbList));
                treeModel.setState(treeModel.getChildren().isEmpty());
                // add attributes
                FuncTreeAttributeBean attribute = new FuncTreeAttributeBean();

                attribute.setState(bean.getState());
                treeModel.setAttributes(attribute);

                treeList.add(treeModel);
            }
        }
        for (FunctionTreeBean tree : treeList) {
            if (tree.getChildren().size() == 0 && !tree.getAttributes().getState()) {
                tree.setIconCls("icon-invalid");
            }
        }
        return treeList;
    }

    /**
     * Get children List
     */
    private List<FunctionTreeBean> getChildren(FunctionBean bean, List<FunctionBean> list) {
        List<FunctionTreeBean> c = new ArrayList<FunctionTreeBean>();
        for (FunctionBean model : list) {
            if (bean.getFuncCode().equals(model.getParent())) {
                FunctionTreeBean tree = new FunctionTreeBean();
                tree.setId(model.getFuncCode());
                tree.setText(model.getFuncName());

                tree.setChildren(getChildren(model, list));
                tree.setState(tree.getChildren().isEmpty());

                if (tree.getChildren().size() == 0 && !model.getState()) {
                    tree.setIconCls("icon-invalid");
                }

                // add attributes
                FuncTreeAttributeBean attribute = new FuncTreeAttributeBean();
                attribute.setState(model.getState());
                tree.setAttributes(attribute);

                c.add(tree);
            }
        }
        return c;
    }

    /**
     * Get All Function
     */
    public List<FunctionBean> getAll() {
        List<FunctionBean> result = functionDao.getFuncAll();
        return result;
    }

    /**
     * Query Functions By UserID
     */
    public Map<String, String> queryUserFunctions(String userId) {
        return functionDao.queryFuncByUserId(userId);
    }
}
