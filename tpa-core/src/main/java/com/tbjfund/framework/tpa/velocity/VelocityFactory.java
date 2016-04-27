package com.tbjfund.framework.tpa.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.log.NullLogChute;

import java.io.*;
import java.util.Properties;

public class VelocityFactory {

    private static VelocityFactory instances;
	private VelocityEngine ve;
	
	public void initVelocity(){
		//VM路径
		//初始化参数
		Properties prop = new Properties();
		prop.put("input.encoding", "UTF-8");
		prop.put("output.encoding", "UTF-8");
		prop.setProperty("resource.loader", "class");
		//prop.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
		prop.setProperty("class.resource.loader.class","com.tbjfund.framework.tpa.velocity.URIResourceLoader" );
		prop.setProperty("runtime.log.logsystem.class","org.apache.velocity.runtime.log.Log4JLogChute" );
		prop.setProperty("runtime.log.logsystem.log4j.logger","E" );
		prop.setProperty("log4j.logger.org.apache.velocity","ERROR" );

        ve = new VelocityEngine();
		ve.init(prop);
	}
	
	public Template getTemplate(String templateName, Writer output, VelocityContext velocityContext){
		return new Template(templateName, velocityContext, output, ve);
	}


	
	public static VelocityFactory getInstances(){
		if(instances == null){
            synchronized (VelocityFactory.class){
			    instances = new VelocityFactory();
            }
		}
		return instances;
	}
	
	private VelocityFactory(){
		this.initVelocity();
	}
	
}