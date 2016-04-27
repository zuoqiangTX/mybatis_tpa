package com.tbjfund.framework.tpa;

import com.tbjfund.framework.tpa.dao.TradeOrderDao;
import com.tbjfund.framework.tpa.lang.OrderBy;
import com.tbjfund.framework.tpa.model.TradeOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by sidawei on 16/4/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring-context.xml")
public class TpaSupportDaoTest {

    @Autowired
    private TradeOrderDao dao;

    @Test
    public void test_select_primarykey(){
        TradeOrder order = dao.findByPrimaryKey(1000);
        System.out.println(order);
    }

    @Test
    public void test_select_example(){
        TradeOrder example = new TradeOrder();
        example.setUserId(0);
        example.setStatus(1);
        List<TradeOrder> orders = dao.findByExample(example, 0, 100);
        if (orders != null){
            for (TradeOrder order : orders){
                System.out.println(order);
            }
        }
    }

    @Test
    public void test_select_example_orderby(){
        TradeOrder example = new TradeOrder();
        List<TradeOrder> orders = dao.findByExample(example, 0, 100, new OrderBy("order_id", OrderBy.ASC));
        if (orders != null){
            for (TradeOrder order : orders){
                System.out.println(order);
            }
        }
    }

    @Test
    public void test_select_count(){
        TradeOrder example = new TradeOrder();
        example.setUserId(0);
        Integer c = dao.findCountByExample(example);
        System.out.println(c);
    }

    @Test
    public void test_update(){
        TradeOrder example = new TradeOrder();
        example.setOrderId(1000);
        example.setProductName("tongbao");
        System.out.println(dao.updateByPrimaryKey(example));
    }

    @Test
    public void test_insert(){
        TradeOrder example = new TradeOrder();
        example.setOrderId(1000);
        example.setUserId(0);
        example.setProductId(1);
        example.setProductName("tongbao");
        example.setStatus(1);
        System.out.println(dao.insert(example));
    }

    @Test
    public void test_delete(){
        System.out.println(dao.deleteByPrimaryKey(1000));
    }

}