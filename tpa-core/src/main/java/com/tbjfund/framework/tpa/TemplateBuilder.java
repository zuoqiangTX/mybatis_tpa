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

    public final static String MAPPER               = "template.vm";
    public final static String SERVICE_INTERFACE    = "service-interface.vm";
    public final static String SERVICE_IMPL         = "service-impl.vm";
    public final static String DUBBO_FACADE         = "facade-interface.vm";
    public final static String DUBBO_IMPL           = "facade-impl.vm";
    public final static String DAO_INTERFACE        = "dao-interface.vm";
    public final static String DAO_IMPL             = "dao-impl.vm";

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
