package com.tbjfund.framework.tpa.webapp.controller;

import java.io.IOException;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipOutputStream;

import com.alibaba.fastjson.JSON;
import com.tbjfund.framework.tpa.config.ColumnConfig;
import com.tbjfund.framework.tpa.config.TableConfig;
import com.tbjfund.framework.tpa.constants.ModelType;
import com.tbjfund.framework.tpa.template.TemplateBuilder;
import com.tbjfund.framework.tpa.webapp.HttpController;

import sun.misc.BASE64Decoder;

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
		if (notBlank(beanName)) {
			tableConfig.setBeanName(beanName);
			tableConfig.setInjectName(com.tbjfund.framework.tpa.utils.StringUtils.getFistLowName(beanName));
		}

		String beanComment = req.getParameter("JavaTypeComment");
		if (notBlank(beanComment)) {
			tableConfig.setComment(beanComment);
		}

		String packageName = req.getParameter("package");
		if (notBlank(packageName)) {
			tableConfig.setNamespace(packageName + "." + tableConfig.getBeanName());
		}

		String modelType = req.getParameter("modelType");
		if (notBlank(modelType)) {
			tableConfig.setModelType(modelType);
		} else {
			tableConfig.setModelType(ModelType.COMPLEX_MODEL);
		}

		String findById = req.getParameter("findById");

		List<ColumnConfig> columnConfigs = tableConfig.getColumns();
		if (columnConfigs != null) {
			for (ColumnConfig c : columnConfigs) {
				String javaFieldName = req.getParameter(c.getFieldName() + ".javaName");
				String comment = req.getParameter(c.getFieldName() + ".comment");
				String isWhere = req.getParameter(c.getFieldName() + ".where");
				if (notBlank(javaFieldName)) {
					c.setFieldName(javaFieldName);
				}
				if (notBlank(comment)) {
					c.setComment(comment);
				}
				if (notBlank(isWhere)) {
					c.setWhereParam(isWhere);
				}

				c.setUpperColumnName(c.getColumnName().toUpperCase());
				c.setfUpperColumnName(c.getFieldName().substring(0, 1).toUpperCase() + c.getFieldName().substring(1));

				if (c.isPrimaryKey()) {
					tableConfig.setPrimaryKey(c);
				}

				if (javaFieldName.equals(findById)) {
					tableConfig.setFindByIdKey(c);
					c.setFindByIdKeyInfo(c.getColumnType());
				}
			}
		}

		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-disposition", "attachment;filename=\"Tpa-" + tableConfig.getBeanName() + ".zip\"");

		CheckedOutputStream cos = new CheckedOutputStream(resp.getOutputStream(), new CRC32());
		ZipOutputStream out = new ZipOutputStream(cos);
		try {
			printMapper(tableConfig, out);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		out.close();
		return null;
	}

	public void printMapper(TableConfig table, ZipOutputStream out)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		String modelType = table.getModelType();
		String clazzFullSig = "com.tbjfund.framework.tpa.template." + modelType.substring(0, 1).toUpperCase() + modelType.substring(1)
				+ "TemplateBuilder";

		TemplateBuilder templateBuilder = (TemplateBuilder) Class.forName(clazzFullSig).newInstance();

		templateBuilder.builderMapper(table, out);
	}

	private boolean notBlank(String str) {
		return StringUtils.isNotBlank(str);
	}
}
