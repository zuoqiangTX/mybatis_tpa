/**  
* <p>Copyright: Copyright (c) 2018</p>  
* <p>Company:铜板街 （ www.tongbanjie.com ）</p>  
* @date 2018年9月18日  
* @version 1.0  
*/
package com.tbjfund.framework.tpa.template;

import java.io.IOException;

import org.apache.tools.zip.ZipOutputStream;

import com.tbjfund.framework.tpa.config.TableConfig;

/** 
 * @author yangxiao
 *<p>Description: </p> 
 */
public class SimpleTemplateBuilder extends TemplateBuilder {

	/*
	 * <p>Description: </p>
	 */
	@Override
	public void builderMapper(TableConfig table, ZipOutputStream out) throws IOException {
		String buffer = TemplateBuilder.build(table, ComplexTemplateBuilder.MAPPER);
		printFile(buffer, table.getNamespace(), table.getTableName() + ".xml", out);

		buffer = TemplateBuilder.build(table, ComplexTemplateBuilder.model);
		printFile(buffer, table.getNamespace(), table.getBeanName() + ".java", out);

		buffer = TemplateBuilder.build(table, ComplexTemplateBuilder.Service);
		printFile(buffer, table.getNamespace(), table.getBeanName() + "Service.java", out);

		buffer = TemplateBuilder.build(table, ComplexTemplateBuilder.ServiceImpl);
		printFile(buffer, table.getNamespace(), table.getBeanName() + "ServiceImpl.java", out);

		buffer = TemplateBuilder.build(table, ComplexTemplateBuilder.DAO);
		printFile(buffer, table.getNamespace(), table.getBeanName() + "DAO.java", out);

		buffer = TemplateBuilder.build(table, ComplexTemplateBuilder.DAOImpl);
		printFile(buffer, table.getNamespace(), table.getBeanName() + "DAOImpl.java", out);

		buffer = TemplateBuilder.build(table, ComplexTemplateBuilder.Query);
		printFile(buffer, table.getNamespace(), table.getBeanName() + "Query.java", out);

		out.flush();
	}

}
