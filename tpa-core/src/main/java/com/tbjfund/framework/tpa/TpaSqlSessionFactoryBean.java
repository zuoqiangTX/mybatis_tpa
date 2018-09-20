package com.tbjfund.framework.tpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.tbjfund.framework.tpa.config.TableConfig;
import com.tbjfund.framework.tpa.scan.ClassPathScanner;
import com.tbjfund.framework.tpa.template.ComplexTemplateBuilder;
import com.tbjfund.framework.tpa.template.TemplateBuilder;

/**
 * Created by sidawei on 16/4/16.
 */
public class TpaSqlSessionFactoryBean extends SqlSessionFactoryBean {

    private final static Logger logger = LoggerFactory.getLogger(TpaSqlSessionFactoryBean.class);

    private ClassPathScanner scanner = new ClassPathScanner();

    private String scanPackage;

    private  Resource[] mapperLocations;

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
                // todo log
                mappers.put(table, TemplateBuilder.build(table, ComplexTemplateBuilder.MAPPER));
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



    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }
}
