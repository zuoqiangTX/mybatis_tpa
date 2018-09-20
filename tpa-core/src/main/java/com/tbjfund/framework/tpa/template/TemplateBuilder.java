package com.tbjfund.framework.tpa.template;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.apache.velocity.VelocityContext;

import com.tbjfund.framework.tpa.config.TableConfig;
import com.tbjfund.framework.tpa.velocity.Template;
import com.tbjfund.framework.tpa.velocity.VelocityFactory;

/**
 * Created by sidawei on 16/4/17.
 */
public abstract class TemplateBuilder {

	// DAO
	public final static String MAPPER = "template.vm";
	public final static String DO = "DO.vm";
	public final static String DAO = "DAO.vm";
	public final static String DAOImpl = "DAOImpl.vm";

	// service
	public final static String Service = "Service.vm";
	public final static String ServiceImpl = "ServiceImpl.vm";
	public final static String DOConverter = "DOConverter.vm";
	public final static String Query = "Query.vm";
	public final static String model = "model.vm";

	// facade
	public final static String dtoModel = "modelDTO.vm";
	public final static String DTOConverter = "DTOConverter.vm";
	public final static String ManageFacade = "ManageFacade.vm";
	public final static String ManageFacadeImpl = "ManageFacadeImpl.vm";
	public final static String QueryFacade = "QueryFacade.vm";
	public final static String QueryFacadeImpl = "QueryFacadeImpl.vm";

	public static String build(TableConfig table, String templateName) {
		VelocityContext velocityContext = new VelocityContext();
		ByteArrayOutputStream temp = new ByteArrayOutputStream();
		OutputStreamWriter output = new OutputStreamWriter(temp);
		Template template = VelocityFactory.getInstances().getTemplate(table.getModelType() + "/" + templateName, output, velocityContext);
		velocityContext.put("table", table);
		try {
			template.outPut();
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] source;
		source = temp.toByteArray();
		String mapper = new String(source);
		return mapper;
	}

	public abstract void builderMapper(TableConfig table, ZipOutputStream out) throws IOException;

	protected void printFile(String buffer, String pkg, String name, ZipOutputStream out) throws IOException {
		ZipEntry entry = new ZipEntry(name);
		out.putNextEntry(entry);
		out.write(buffer.getBytes());
	}
}
