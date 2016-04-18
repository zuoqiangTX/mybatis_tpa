package com.tbjfund.framework.tpa.scan;

import com.tbjfund.framework.tpa.annotation.Column;
import com.tbjfund.framework.tpa.annotation.Table;
import com.tbjfund.framework.tpa.annotation.Transient;
import com.tbjfund.framework.tpa.config.ColumnConfig;
import com.tbjfund.framework.tpa.config.TableConfig;
import com.tbjfund.framework.tpa.utils.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;

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
                    table.setBeanName(bean.getBeanClassName().substring(bean.getBeanClassName().lastIndexOf(".") + 1));
                    table.setPackageName(StringUtils.getPackageName(table.getNamespace()));

                    List<ColumnConfig> columns = new LinkedList<ColumnConfig>();
                    Class beanClass = Thread.currentThread().getContextClassLoader().loadClass(bean.getBeanClassName());
                    Field[] fields = beanClass.getDeclaredFields();
                    if (fields != null){
                        for (Field field : fields){
                            Annotation[] annotations = field.getAnnotations();
                            if (isTransient(annotations)){
                                continue;
                            }
                            boolean touch = false;
                            if (annotations != null){
                                for (Annotation an : annotations){
                                    if (an instanceof Column){
                                        ColumnConfig column = new ColumnConfig(((Column) an).isPrimaryKey(), formatColumnName((Column) an, field), field.getName());
                                        column.setJavaType(field.getType().getCanonicalName());
                                        column.setSimpleJavaType(StringUtils.getSimpleName(column.getJavaType()));
                                        if(column.isPrimaryKey()){
                                            table.setPrimaryKey(column);
                                        }

                                        check(column, beanClass.getCanonicalName() + field.getName());
                                        columns.add(column);
                                        touch = true;
                                        break;
                                    }
                                }
                            }
                            if (!touch){
                                // 没有@Column
                                ColumnConfig column = new ColumnConfig(false, formatColumnName(null, field), field.getName());
                                column.setJavaType(field.getType().getCanonicalName());
                                column.setSimpleJavaType(StringUtils.getSimpleName(column.getJavaType()));
                                check(column, beanClass.getCanonicalName() + field.getName());
                                columns.add(column);
                            }
                        }
                    }

                    table.setColumns(columns);
                    check(table, beanClass.getCanonicalName());
                    tableConfigs.add(table);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableConfigs;
    }

    /**
     * Column.name == '' ==> field.name
     * @param column
     * @param field
     * @return
     */
    private String formatColumnName(Column column, Field field){
        if (column != null && column.name() != null && column.name().length() != 0){
            return column.name();
        }
        return StringUtils.getNormalName(field.getName());
    }

    /**
     * 是否有@Transient
     * @param annotations
     * @return
     */
    private boolean isTransient(Annotation[] annotations){
        if (annotations == null || annotations.length == 0){
            return false;
        }
        for (Annotation annotation : annotations){
            if (annotation instanceof Transient){
                return true;
            }
        }
        return false;
    }

    private void check(ColumnConfig column, String error){
        Assert.notNull(column, "@Table " + error);
        Assert.notNull(column.getColumnName(), "@Table name 不能为空 " + error);
        Assert.notNull(column.getFieldName(), "@Table fieldName 不能为空 " + error);
        Assert.notNull(column.getJavaType(), "@Table type 不能为空 " + error);
    }

    private void check(TableConfig table, String error){
        Assert.notNull(table, "@Column " + error);
        if (table.getColumns() != null && table.getColumns().size() > 0){
            Assert.notNull(table.getPrimaryKey(), "@Column 必须要有主键 " + error);
        }
        Assert.notNull(table.getTableName(), "@Column name 不能为空 " + error);
        Assert.notNull(table.getNamespace(), "@Column namespace 不能为空 " + error);

    }

}
