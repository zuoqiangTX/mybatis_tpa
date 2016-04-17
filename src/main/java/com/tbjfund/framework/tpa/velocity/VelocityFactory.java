package com.tbjfund.framework.tpa.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

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
		ve = new VelocityEngine();
		ve.init(prop);
	}
	
	public Template getTemplate(String templateName, Writer output, VelocityContext velocityContext){
		return new Template(templateName, velocityContext, output, ve);
	}


	
	public static VelocityFactory getInstances(){
		if(instances == null){
			instances = new VelocityFactory();
		}
		return instances;
	}
	
	private VelocityFactory(){
		this.initVelocity();
	}
	
}