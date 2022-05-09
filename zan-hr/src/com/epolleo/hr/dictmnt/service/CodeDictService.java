package com.epolleo.hr.dictmnt.service;

import java.util.List;

import javax.annotation.Resource;

import com.epolleo.bp.pub.AbstractService;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.util.DateUtils;
import com.epolleo.hr.dictmnt.bean.CodeDictBean;
import com.epolleo.hr.dictmnt.dao.ibatis.CodeDictDao;

/**
 * @Description: 基础数据字典表的业务类
 * 
 * @author dante
 * @date 2015-09-23 下午11:45:59
 *
 */
public class CodeDictService extends AbstractService<CodeDictBean, CodeDictDao> {
    @Resource
    private SeqService seqService;
    @Resource
    public void setDao(CodeDictDao dao) {
        this.dao = dao;
    }


    @Override
    public CodeDictBean save(CodeDictBean entity) {
  //      entity.setId(seqService.getNewId(IdKind.PlaneManageReportCleanCostId));
        entity.setUpdateBy(LoginUser.getCurrentUser().getUserId());
        entity.setUpdateDate(DateUtils.getCurrentDate());
        return super.save(entity);
    }
    
    /**
     * 根据Type获取基础数据字典表
     * 
     * @param type
     * @return
     */
    public List<CodeDictBean> queryByType(String type) {
    	return dao.queryByType(type);
    }

}