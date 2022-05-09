package com.epolleo.bp.seq.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.seq.SeqApi;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.bean.SeqBean;

public class SeqDao extends AbstractDao {

    public PagingResult<SeqBean> findPaging(PagingForm pagingParam) {
        String sort = pagingParam.getSort();
        String order = pagingParam.getOrder();
        if (sort != null) {
            checkName(sort);
        }
        if (order != null) {
            checkName(order);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("limit", pagingParam.getPageSize());
        params.put("start", pagingParam.getSkip());
        params.put("sort", pagingParam.getSort());
        params.put("order", pagingParam.getOrder());
        params.putAll(pagingParam.getFilter());
        /*-
        int total = (Integer) getSqlMapClientTemplate().queryForObject(
            "selectSeqCount", params);
        List<SeqBean> records = (List<SeqBean>) getSqlMapClientTemplate()
            .queryForList("selectSeq", params, pagingParam.getStart(),
                pagingParam.getLimit());
        PagingResult<SeqBean> result = new PagingResult<SeqBean>(records, total);
         */
        ArrayList<SeqBean> page = new ArrayList<SeqBean>();
        int total = getSqlMapClientTemplate().queryForPage(page, "selectSeq",
            params, pagingParam.getSkip(), pagingParam.getPageSize());
        PagingResult<SeqBean> results = new PagingResult<SeqBean>(page, total);
        return results;
    }

    public int update(SeqBean seqGen) {
        getSqlMapClientTemplate().update("updateSeq", seqGen, 1);
        return 1;
    }

    public SeqBean save(SeqBean seqGen) {
        return (SeqBean) getSqlMapClientTemplate().insert("insertSeq", seqGen);
    }

    public int delete(String id) {
        getSqlMapClientTemplate().delete("deleteSeq", id, 1);
        return 1;
    }

    public long getNewId(IdKind kind) {
        try {
            return SeqApi.getNextLong(getSqlMapClient(), kind);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNewIdString(IdKind kind, Object... args) {
        try {
            return SeqApi.getNextString(getSqlMapClient(), kind);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getNewIdLong(IdKind kind, Object... args) {
        try {
            return SeqApi.getNextLong(getSqlMapClient(), kind);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*-
    public long getNewId(IdKind kind) {
        synchronized (kind) {
            if (kind.hasNext()) {
                return kind.next();
            } else {
                SqlMapClientTemplate db = getSqlMapClientTemplate();
                long id = -1;
                while (id == -1) {
                    logger.info("get current id, kind = " + kind.kind);
                    SeqBean bean = (SeqBean) db.queryForObjectArgs(
                        "getSeqBean", kind.kind);
                    
                    if (bean == null) {
                        kind.current = 1;
                        kind.threshold = kind.delta;
                        logger.info("Create new ID Record for " + kind);
                        int row = db.updateArgs("insertNewSeq", kind.kind,
                            kind.name, kind.threshold, kind.delta, 20, kind.getFormatStr());
                        if (row == 1) {
                            id = kind.current;
                        }
                    } else {
                        kind.setFormatStr(bean.formatStr);
                        kind.current = bean.getValue() + 1;
                        long delta = Math.max(1, bean.fetchSize);
                        kind.threshold = bean.value + delta;

                        int row = db.updateArgs("tryUpdateSeqValue",
                            kind.threshold, kind.kind, bean.value);
                        if (row == 1) {
                            id = kind.current;
                        }
                    }
                }
                return id;
            }
        }
    }

    public String getNewIdString(IdKind kind, Object... args) {
        long val = getNewId(kind);
        return kind.formatter.format(val, new Date(), args);
    }

    public long getNewIdLong(IdKind kind, Object... args) {
        String id = getNewIdString(kind, args);
        return Long.parseLong(id);
    }
     */
}
