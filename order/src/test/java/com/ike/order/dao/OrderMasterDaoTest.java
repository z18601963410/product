package com.ike.order.dao;

import com.ike.order.BaseTest;
import com.ike.order.entity.OrderMaster;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

class OrderMasterDaoTest extends BaseTest {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Test
    void testA_orderMasterDao_insertOrderMaster() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("20210616012501");
        orderMaster.setBuyerName("ike");
        orderMaster.setBuyerPhone("18601963410");
        orderMaster.setBuyerAddress("北京市昌平区");
        orderMaster.setBuyerOpenid("8638284483");
        orderMaster.setOrderAmount(1999.0);
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        int affect1 = orderMasterDao.insertOrderMaster(orderMaster);
        orderMaster.setOrderId("20210616012502");
        int affect2 = orderMasterDao.insertOrderMaster(orderMaster);
        Assert.assertSame(affect1, affect2);
    }

    @Test
    void testB_orderMasterDao_selectAll() {
        OrderMaster orderMasterCondition = new OrderMaster();
        orderMasterCondition.setOrderStatus(0);
        List<OrderMaster> orderMasterList = orderMasterDao.queryOrderMasterList(null);
        Assert.assertEquals(2, orderMasterList.size());
    }

    @Test
    void testC_orderMasterDao_selectById() {
        OrderMaster orderMaster = orderMasterDao.queryOrderMasterById("20210616012501");
        Assert.assertNotNull(orderMaster.getOrderId());
    }

}
