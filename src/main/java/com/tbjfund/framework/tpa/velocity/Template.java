package com.tbjfund.framework.tpa.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;

public class Template {
    private String templateName;
	private VelocityContext velocityContext;
	private Writer output;
	private VelocityEngine velocityEngine;
	private ByteArrayOutputStream outTmp = new ByteArrayOutputStream();
	
	public Template(String templateName, VelocityContext velocityContext, Writer output, VelocityEngine velocityEngine){
		this.templateName = templateName;
		this.velocityContext = velocityContext;
		this.output = output;
		this.velocityEngine = velocityEngine;
	}
	public void outPut() throws IOException{
		org.apache.velocity.Template t = velocityEngine.getTemplate(templateName);
		//将要绑定的内容进行上下文的写入
		//velocityContext.put("test", "hello");
		//velocityContext.put("test2", "world");
		//StringWriter writer = new StringWriter();
		//Writer writer = new FileWriter(outPutFile);
		
		//Writer writer = new OutputStreamWriter(outTmp) ;
		t.merge(velocityContext, output);
		//System.out.println( writer.toString() );
        output.flush();
        //output.close();

		//formatXML();
	}
	
	/*private void formatXML() throws FileNotFoundException, IOException{
		ByteArrayInputStream inputTmp = new ByteArrayInputStream(outTmp.toByteArray());
		SAXBuilder sb = new SAXBuilder();
		Document doc = null;
		try {
			doc = sb.build(inputTmp);
		} catch (Exception e) {
			Log.error("创建XML格式化文件解析器失败");
			e.printStackTrace();
		}
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        out.output(doc, new FileOutputStream(outPutFile));
	}*/
	
	
}