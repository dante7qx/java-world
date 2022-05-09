package com.epolleo.bp.seq.action;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;

import com.epolleo.bp.pub.AbstractAction;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.seq.bean.SeqBean;
import com.epolleo.bp.seq.service.SeqService;

@Function
@FunctionDir(id="bp.seq", name="号码")
public class SeqAction extends AbstractAction {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    SeqService seqService;

    @Function("bp.seq.update")
    @FunctionName("号码更新")
    public void doUpdate(Context context, @Params SeqBean seqGen) {
        logger.debug("to update Id Gen : " + seqGen);
        String seqKey = seqGen.getSeqKey();
        // TODO Assert.notNull(seqKey,
        // "the id should be presented for update.");
        seqService.update(seqGen);
        context.put("json", seqGen);
    }

    @Function("bp.seq.query")
    @FunctionName("号码查询")
    public void doQuery(Context context, @Params PagingForm form,
        @Params SeqBean seqGen) {
        HashMap<String, Object> filter = new HashMap<String, Object>();
        Object seqKey = seqGen.getSeqKey();
        if (seqKey != null) {
            if (seqKey instanceof String) {
                filter.put("seqKey", "%" + seqKey + "%");
            } else {
                filter.put("seqKey", seqKey);
            }
        }
        Object name = seqGen.getName();
        if (name != null) {
            if (name instanceof String) {
                filter.put("name", "%" + name + "%");
            } else {
                filter.put("name", name);
            }
        }
        form.setFilterMap(filter);
        PagingResult<SeqBean> result = seqService.findPaging(form);
        context.put("json", result);
    }

    @Function("bp.seq.insert")
    @FunctionName("号码保存")
    public void doSave(Context context, @Params SeqBean seqGen) {
        logger.debug("to Save Id Gen : " + seqGen);
        seqService.save(seqGen);
        context.put("json", seqGen);
    }

    @Function("bp.seq.delete")
    @FunctionName("号码删除")
    public void doDelete(Context context, @Param(name = "id") String id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        int delete = seqService.delete(id);
        result.put("success", delete == 1);
        context.put("json", result);
    }
}
