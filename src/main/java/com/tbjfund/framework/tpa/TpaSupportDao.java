package com.tbjfund.framework.tpa;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sidawei on 16/4/16.
 */
public class TpaSupportDao<T, PK> extends SqlSessionDaoSupport {

    private String namaspace;

    {
        this.namaspace = "tpa." + getSuperClassGenricType(getClass(), 0).getCanonicalName();
    }

    /**
     * 主键查询
     * @param id
     * @param <T>
     * @return
     */
    public <T> T findByPrimaryKey(PK id){
        return (T) getSqlSession().selectOne(this.namaspace + ".findByPrimaryKey", id);
    }

    /**
     * 条件查询
     * @param example
     * @param offset
     * @param rows
     * @return
     */
    public List<T> findByExample(T example, Integer offset, Integer rows){
        return findByExample(example, offset, rows, null);
    }

    /**
     * 条件查询
     * @param example
     * @param offset
     * @param rows
     * @param orderby
     * @return
     */
    public List<T> findByExample(T example, Integer offset, Integer rows, OrderBy orderby){
        Map map = new HashMap();
        map.put("example", example);
        map.put("offset", offset);
        map.put("rows", rows);
        map.put("orderby", orderby);
        return getSqlSession().selectList(this.namaspace + ".findByExample", map);
    }

    /**
     * 条件查询－数量
     * @param example
     * @return
     */
    public Integer findCountByExample(T example){
        Map map = new HashMap();
        map.put("example", example);
        return (Integer) getSqlSession().selectOne(this.namaspace + ".findCountByExample", map);
    }

    /**
     * 插入
     * @param example
     * @return
     */
    public Integer insert(T example){
        return getSqlSession().insert(this.namaspace + ".insert", example);
    }

    /**
     * 主键删除
     * @param key
     * @return
     */
    public Integer deleteByPrimaryKey(PK key){
        return getSqlSession().delete(this.namaspace + ".deleteByPrimaryKey", key);
    }

    /**
     * 主键更新
     * @param example
     * @return
     */
    public Integer updateByPrimaryKey(T example){
        return getSqlSession().update(this.namaspace + ".updateByPrimaryKey", example);
    }

    /**
     * 通过反射, 获得定义Class时声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     *
     *@param clazz
     *            clazz The class to introspect
     * @param index
     *            the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     *         determined
     */
    @SuppressWarnings("unchecked")
    public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {

        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            //return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }
}
