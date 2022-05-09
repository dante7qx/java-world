package com.epolleo.webx.valve;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.awt.Dimension;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintElementIndex;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRWrappingSvgRenderer;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.Renderable;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.RenderableTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.alibaba.citrus.util.StringUtil;
import com.epolleo.bp.dao.ReportDao;
import com.epolleo.bp.entity.BPReportRecord;

/**
 * 报表输出Valve。
 */
public class JasperReportValve extends AbstractValve {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private HttpServletRequest req;
    @Resource
    private HttpServletResponse resp;

    @Resource
    ReportDao reportDao;

    String prefix;

    public void invoke(PipelineContext pipelineContext) throws Exception {
        TurbineRunData rundata = getTurbineRunData(req);
        Context ctx = ((TurbineRunDataInternal) rundata).getContext();

        // 检查重定向标志，如果是重定向，则不需要将页面输出。
        if (!rundata.isRedirected()) {
            String target = req.getRequestURI();
            logger.info(target);
            BPReportRecord rpt = (BPReportRecord) ctx.get("bp.report");
            if (rpt != null) {
                String imgPath = prefix() + "$" + StringUtil.longToString(req.getSession().getId().hashCode()) + "/"
                    + rpt.getSeqId() + "/";
                // export html
                JasperPrint jasperPrint = rpt.getReport();
                JRHtmlExporter exporter = new JRHtmlExporter();
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, imgPath);
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                // 输出html内容到response
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, rundata.getResponse().getWriter());
                exporter.exportReport();
                rundata.getResponse().getWriter().close();
            } else {
                int s = String.valueOf(target).lastIndexOf('$');
                if (s < 0) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                String[] strs = target.substring(s + 1).split("[/]");
                if (strs.length != 3) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                String hashCode = StringUtil.longToString(req.getSession().getId().hashCode());
                if (!hashCode.equals(strs[0])) {
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                int seqId = 0;
                try {
                    seqId = Integer.parseInt(strs[1]);
                } catch (Exception e) {
                }
                if (seqId < 1) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                if (!strs[2].startsWith(JRHtmlExporter.IMAGE_NAME_PREFIX)) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                // get it from cache
                rpt = reportDao.findById(seqId);
                if (rpt == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                String imgUrl = strs[2].substring(JRHtmlExporter.IMAGE_NAME_PREFIX.length());
                JRPrintElementIndex pei = JRPrintElementIndex.parsePrintElementIndex(imgUrl);
                JasperPrint jasperPrint = rpt.getReport();

                JRPrintPage page = jasperPrint.getPages().get(pei.getPageIndex());

                Integer[] elementIndexes = pei.getAddressArray();
                Object element = page.getElements().get(elementIndexes[0].intValue());

                for (int i = 1; i < elementIndexes.length; ++i) {
                    JRPrintFrame frame = (JRPrintFrame) element;
                    element = frame.getElements().get(elementIndexes[i].intValue());
                }
                if (!(element instanceof JRPrintImage)) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                JRPrintImage image = (JRPrintImage) element;
                Renderable renderer = image.getRenderable();
                if (renderer.getTypeValue() == RenderableTypeEnum.SVG) {
                    renderer = new JRWrappingSvgRenderer(renderer, new Dimension(image.getWidth(), image.getHeight()),
                        ModeEnum.OPAQUE == image.getModeValue() ? image.getBackcolor() : null);
                }

                String imageMimeType = renderer.getImageTypeValue().getMimeType();

                byte[] imageData = renderer.getImageData(DefaultJasperReportsContext.getInstance());
                if (imageData != null && imageData.length > 0) {
                    if (imageMimeType != null) {
                        resp.setHeader("Content-Type", imageMimeType);
                    }
                    resp.setContentLength(imageData.length);
                    ServletOutputStream ouputStream = resp.getOutputStream();
                    ouputStream.write(imageData, 0, imageData.length);
                    ouputStream.flush();
                    ouputStream.close();
                }
            }
        }

        pipelineContext.invokeNext();
    }

    String prefix() {
        if (prefix == null || prefix.length() < 1) {
            return "";
        }
        String ctx = req.getContextPath();
        if (ctx == null || ctx.length() == 0) {
            ctx = "/";
        }
        if (!ctx.endsWith("/")) {
            ctx += "/";
        }
        return ctx + prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}