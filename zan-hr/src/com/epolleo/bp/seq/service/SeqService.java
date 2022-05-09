package com.epolleo.bp.seq.service;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.bean.SeqBean;
import com.epolleo.bp.seq.dao.SeqDao;

public class SeqService {
    static final Log logger = LogFactory.getLog(SeqService.class);

    @Resource
    private SeqDao seqDao;

    public int update(SeqBean seqBean) {
        return seqDao.update(seqBean);
    }

    public PagingResult<SeqBean> findPaging(PagingForm pagingParam) {
        return seqDao.findPaging(pagingParam);
    }

    public SeqBean save(SeqBean seqBean) {
        return seqDao.save(seqBean);
    }

    public int delete(String idStr) {
        return seqDao.delete(idStr);
    }

    public long getNewId(IdKind kind) {
        return seqDao.getNewId(kind);
    }

    public String getNewIdString(IdKind kind) {
        return String.valueOf(getNewId(kind));
    }

    public String getNewIdString(IdKind kind, Object... args) {
        return seqDao.getNewIdString(kind, args);
    }

    public long getNewIdLong(IdKind kind, Object... args) {
        return seqDao.getNewIdLong(kind, args);
    }
}
