package com.epolleo.pub.att.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.pub.att.bean.AttBean;
import com.epolleo.pub.att.service.AttService;
import com.epolleo.util.common.service.FileIOService;

public class AttAction {
	protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AttService service;

    @Resource
    private SeqService seqService;
    
    @Resource
    FileIOService fileIOService;

    @Resource
    private ParserRequestContext parser;
    
    
    public static AttService attService;
    public static FileIOService fileIO;
    
    @PostConstruct
    public void init(){
    	attService = this.service;
    	fileIO = this.fileIOService;
    }
    
    
    
    
    /**
     * 处理指定页及条件的查询结果
     * 
     * @param form
     *            分页参数
     * @param lotId
     *            查询过滤参数
     * @see Context
     * @see PagingForm
     * @see AttBean
     */
    public void doQuery(Context context, @Params PagingForm form,
        @Param(name = "lotId") String lotId) {
        Map<String, Object> filter = new HashMap<String, Object>();
        PagingResult<AttBean> result  = new PagingResult<AttBean>(new ArrayList<AttBean>(), 0);//查询结果为空设置
        if (StringUtils.isNotEmpty(lotId)) {
            filter.put("lotId", lotId);
            form.setFilterMap(filter);
            result = service.findPaging(form);
        }
        context.put("json", result);
    }

    /**
     * @param entity
     *            新增附件表对象
     * @see Context
     * @see AttBean
     */
    public void doSave(Context context, @Params AttBean entity) {
        entity.setId(seqService.getNewId(IdKind.AttId));
        service.save(entity);
        context.put("json", entity);
    }

    /**
     * @param id
     *            待删除附件表的关键值（比如主键Key）
     * 
     * @see Context
     */
    public void doDelete(Context context, @Param(name = "id") Long id) {
        throw new UnsupportedOperationException(
            "!!!The Operation is not allow !!!");
        /*
         * HashMap<String, Object> result = new HashMap<String, Object>(); int
         * delete = 0; if (id != null) { AttBean attEntity = service.find(id);
         * if (fileIOService.deleteAttEntity(attEntity)) {// 从文件服务器删除对应的附件文件
         * delete = service.delete(id); } }else{
         * logger.warn("!!!Delete Failed,Att Id is null!!!"); }
         * result.put("success", delete == 1); context.put("json", result);
         */
    }

    /**
     * @param entity
     *            修改附件表对象
     * @see Context
     * @see AttBean
     */
    public void doUpdate(Context context, @Params AttBean entity) {
        service.update(entity);
        context.put("json", entity);
    }
    
    public void doUploadImg(Context context, HttpServletRequest request, 
        @Param(name="key") String key,
        @Param(name="file") String file
        ) throws IOException{
        request.getSession().setAttribute(key, parser.getParameters().getFileItem(file));
        context.put("json", 1);
    }
    
    public void doImg(HttpServletRequest request, HttpServletResponse response, 
        @Param(name="lotId") String lotId, 
        @Param(name="id") Long id,
        @Param(name="key") String key) throws IOException{
        if(lotId==null && key==null)
            return;
        OutputStream out=null;
        InputStream in=null;
        response.setContentType("image/gif;charset=utf-8");//设定输出的类型
          
        try{
            out = new BufferedOutputStream(response.getOutputStream());
            
            if(lotId==null){
                FileItem fileItem = (FileItem) request.getSession().getAttribute(key);
                if(fileItem==null)return;
                in = new BufferedInputStream(fileItem.getInputStream());
            }else{
                List<AttBean> list = service.queryByLotId(lotId);
                if(list==null || list.isEmpty()){
                    return;
                }
                AttBean attBean = list.get(0);
                if(id!=null){
                    for(AttBean att : list){
                        if(att.getId().equals(id)){
                            attBean = att;
                            break;
                        }
                    }
                }
                String filePath = fileIOService.getFilePathByAttBean(attBean);
                in = new BufferedInputStream(new FileInputStream(filePath));
            }
            
            byte[] buff = new byte[1024];
            while(in.read(buff)!=-1){
                out.write(buff);
            }
        }finally{
            if(in!=null)
                in.close();
            if(out!=null)
                out.close();
        }
    }

    /**
     * 获取批次号下的所有附件
     * 
     * @param context
     * @param form
     * @param lotId
     */
    public void doQueryByLotId(Context context,
        @Param(name = "lotId") String lotId) {
        List<AttBean> list = new ArrayList<AttBean>();
        if (StringUtils.isNotEmpty(lotId)) {
            list = service.queryByLotId(lotId);
        }
        context.put("json", list);
    }
    
    
    /**
     * 获多个批次号下的所有附件
     * 
     * @param context
     * @param form
     * @param lotId
     */
    public void doQueryByLotIds(Context context,
        @Param(name = "lotId") String lotId) {
        List<AttBean> list = new ArrayList<AttBean>();
        if (StringUtils.isNotEmpty(lotId)) {
            list = service.queryByLotIds(lotId);
        }
        context.put("json", list);
    }
}
