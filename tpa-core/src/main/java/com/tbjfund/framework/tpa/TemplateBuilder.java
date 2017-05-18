package com.tbjfund.framework.tpa;

import com.tbjfund.framework.tpa.config.TableConfig;
import com.tbjfund.framework.tpa.velocity.Template;
import com.tbjfund.framework.tpa.velocity.VelocityFactory;
import org.apache.velocity.VelocityContext;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by sidawei on 16/4/17.
 */
public class TemplateBuilder {

    public final static String MAPPER = "template.vm";
    public final static String model = "model.vm";
    public final static String DAO = "DAO.vm";
    public final static String DAOImpl = "DAOImpl.vm";
    public final static String Service = "Service.vm";
    public final static String ServiceImpl = "ServiceImpl.vm";
    public final static String DOConverter = "DOConverter.vm";

    public static String build(TableConfig table, String templateName){
        VelocityContext velocityContext = new VelocityContext();
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        OutputStreamWriter output = new OutputStreamWriter(temp);
        Template template = VelocityFactory.getInstances().getTemplate(templateName, output, velocityContext);
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
}
