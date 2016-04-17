package com.tbjfund.framework.tpa;

import com.tbjfund.framework.tpa.config.TableConfig;
import com.tbjfund.framework.tpa.mybatis.InMemoryMapperResource;
import com.tbjfund.framework.tpa.scan.ClassPathScanner;
import com.tbjfund.framework.tpa.velocity.Template;
import com.tbjfund.framework.tpa.velocity.VelocityFactory;
import org.apache.velocity.VelocityContext;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sidawei on 16/4/16.
 */
public class TpaSqlSessionFactoryBean extends SqlSessionFactoryBean {

    private final static Logger logger = LoggerFactory.getLogger(TpaSqlSessionFactoryBean.class);

    private ClassPathScanner scanner = new ClassPathScanner();

    private String scanPackage;

    Resource[] mapperLocations;

    @Override
    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<TableConfig, String> mappers = new HashMap<TableConfig, String>();
        List<TableConfig> tables = scanner.scan(scanPackage);
        if (tables != null){
            for (TableConfig table : tables){
                mappers.put(table, buildMapper(table));
            }
        }

        Resource[] newRes = new Resource[mapperLocations.length + tables.size()];
        for (int i = 0; i < mapperLocations.length; i++) {
            newRes[i] = mapperLocations[i];
        }
        for (int i = tables.size() - 1; i >= 0 ; i--) {
            newRes[i] = new InMemoryMapperResource(tables.get(i).getNamespace(), mappers.get(tables.get(i)));
        }
        super.setMapperLocations(newRes);

        super.afterPropertiesSet();
    }

    private String buildMapper(TableConfig table){
        VelocityContext velocityContext = new VelocityContext();
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        OutputStreamWriter output = new OutputStreamWriter(temp);
        Template template = VelocityFactory.getInstances().getTemplate("template.vm", output, velocityContext);
        velocityContext.put("table", table);
        try {
            template.outPut();
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] source;
        source = temp.toByteArray();
        String mapper = new String(source);
        logger.info("Appen mapper to mybatis:\n" + mapper);
        return mapper;
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }
}
