package com.tbjfund.framework.tpa.webapp.controller;

import com.alibaba.fastjson.JSON;
import com.tbjfund.framework.tpa.TemplateBuilder;
import com.tbjfund.framework.tpa.config.ColumnConfig;
import com.tbjfund.framework.tpa.config.TableConfig;
import com.tbjfund.framework.tpa.webapp.HttpController;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

/**
 * Created by sidawei on 16/4/25.
 */
public class Download implements HttpController {

    @Override
    public String service(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String tableConfigJson = req.getParameter("tableConfigJson");
        BASE64Decoder decoder = new BASE64Decoder();
        tableConfigJson = new String(decoder.decodeBuffer(tableConfigJson), "utf-8");
        TableConfig tableConfig = JSON.parseObject(tableConfigJson, TableConfig.class);

        String beanName = req.getParameter("JavaTypeName");
        if (notBlank(beanName)){
            tableConfig.setBeanName(beanName);
            tableConfig.setInjectName(com.tbjfund.framework.tpa.utils.StringUtils.getFistLowName(beanName));
        }

        String beanComment = req.getParameter("JavaTypeComment");
        if (notBlank(beanComment)){
            tableConfig.setComment(beanComment);
        }

        List<ColumnConfig> columnConfigs = tableConfig.getColumns();
        if (columnConfigs != null){
            for (ColumnConfig c : columnConfigs){
                String javaFieldName = req.getParameter(c.getFieldName()+".javaName");
                String comment = req.getParameter(c.getFieldName()+".comment");
                if (notBlank(javaFieldName)){
                    c.setFieldName(javaFieldName);
                }
                if (notBlank(comment)){
                    c.setComment(comment);
                }
                if (c.isPrimaryKey()){
                    tableConfig.setPrimaryKey(c);
                }
            }
        }

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-disposition", "attachment;filename=\"Tpa-" + tableConfig.getBeanName() + ".zip\"");

        CheckedOutputStream cos = new CheckedOutputStream(resp.getOutputStream(), new CRC32());
        ZipOutputStream out = new ZipOutputStream(cos);
        printMapper(tableConfig, out);
        out.close();
        return null;
    }

    private void printMapper(TableConfig table, ZipOutputStream out) throws IOException {

        String buffer = TemplateBuilder.build(table, TemplateBuilder.MAPPER);
        printFile(buffer, table.getNamespace(), table.getTableName() + ".xml", out);

        buffer = TemplateBuilder.build(table, TemplateBuilder.SERVICE_INTERFACE);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "Service.java", out);

        buffer = TemplateBuilder.build(table, TemplateBuilder.SERVICE_IMPL);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "ServiceImpl.java", out);

        buffer = TemplateBuilder.build(table, TemplateBuilder.DAO_INTERFACE);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "Dao.java", out);

        buffer = TemplateBuilder.build(table, TemplateBuilder.DAO_IMPL);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "DaoImpl.java", out);

        buffer = TemplateBuilder.build(table, TemplateBuilder.DUBBO_FACADE);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "Facade.java", out);

        buffer = TemplateBuilder.build(table, TemplateBuilder.DUBBO_IMPL);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "FacadeImpl.java", out);

        buffer = TemplateBuilder.build(table, TemplateBuilder.MODEL);
        printFile(buffer, table.getNamespace(), table.getBeanName() + ".java", out);

        out.flush();
    }

    private void printFile(String buffer, String pkg,  String name, ZipOutputStream out) throws IOException {
        ZipEntry entry = new ZipEntry(name);
        out.putNextEntry(entry);
        out.write(buffer.getBytes());
    }

    private boolean notBlank(String str){
        return StringUtils.isNotBlank(str);
    }
}
