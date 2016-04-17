package com.tbjfund.framework.tpa.scan;

import com.tbjfund.framework.tpa.annotation.Column;
import com.tbjfund.framework.tpa.annotation.Table;
import com.tbjfund.framework.tpa.config.ColumnConfig;
import com.tbjfund.framework.tpa.config.TableConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sidawei on 16/4/15.
 */
public class ClassPathScanner {
    //private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private ClassPathScanningCandidateComponentProvider classPathScanningCandidateComponentProvider = new ClassPathScanningCandidateComponentProvider(false);

    {
        classPathScanningCandidateComponentProvider.addIncludeFilter((new AnnotationTypeFilter(Table.class)));
        //Resource[] res = resourcePatternResolver.getResources("classpath*:/com/tbjfund/**/*.class");
    }

    public List<TableConfig> scan(String basePackage){
        List<TableConfig> tableConfigs = new LinkedList<TableConfig>();
        try {
            Set<BeanDefinition> cps = classPathScanningCandidateComponentProvider.findCandidateComponents(basePackage);
            for (BeanDefinition bean : cps){
                if (bean instanceof ScannedGenericBeanDefinition){
                    TableConfig table = new TableConfig();
                    Map<String, Object> attr = ((ScannedGenericBeanDefinition) bean).getMetadata().getAnnotationAttributes(Table.class.getCanonicalName());
                    table.setNamespace(bean.getBeanClassName());
                    table.setTableName(attr.get("name").toString());
                    table.setTypeAlisName(attr.get("aliasName").toString());

                    List<ColumnConfig> columns = new LinkedList<ColumnConfig>();
                    Class beanClass = Class.forName(bean.getBeanClassName());
                    Field[] fields = beanClass.getDeclaredFields();
                    if (fields != null){
                        for (Field field : fields){
                            Annotation[] annotations = field.getAnnotations();
                            if (annotations != null){
                                for (Annotation an : annotations){
                                    if (an instanceof Column){
                                        ColumnConfig column = new ColumnConfig(((Column) an).isPrimaryKey(), ((Column) an).name(), field.getName());

                                        column.setFieldType(field.getType().getCanonicalName());

                                        if(column.isPrimaryKey()){
                                            table.setPrimaryKey(column);
                                        }

                                        check(column);
                                        columns.add(column);
                                    }
                                }
                            }
                        }
                    }

                    table.setColumns(columns);
                    check(table);
                    tableConfigs.add(table);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableConfigs;
    }

    private void check(ColumnConfig column){
        if (column == null
                || StringUtils.isEmpty(column.getColumnName())
                || StringUtils.isEmpty(column.getFieldName())
                || StringUtils.isEmpty(column.getFieldType())){
            throw new IllegalArgumentException(column.getColumnName());
        }
    }

    private void check(TableConfig table){
        if (table == null
                || table.getColumns() == null
                || table.getColumns().size() == 0
                || table.getPrimaryKey() == null
                || StringUtils.isEmpty(table.getTableName())
                || StringUtils.isEmpty(table.getNamespace())){
            throw new IllegalArgumentException(table.getTableName());
        }
    }

}
