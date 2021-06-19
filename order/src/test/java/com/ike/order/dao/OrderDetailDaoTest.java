package com.ike.order.dao;

import com.ike.order.BaseTest;
import com.ike.order.entity.OrderDetail;
import com.ike.order.entity.OrderMaster;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

class OrderDetailDaoTest extends BaseTest {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Test
    void testA_inert() {
        OrderDetail orderDetail = new OrderDetail();
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("20210616012501");

        orderDetail.setDetailId("202106160319");
        orderDetail.setOrderId(orderMaster);
        orderDetail.setProductId("157875196366160022");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(0.01);
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("//fuss10.elemecdn.com/0/49/65d10ef215d3c770ebb2b5ea962a7jpeg.jpeg");
        orderDetail.setCreateTime(new Date());
        orderDetail.setUpdateTime(new Date());
        int affect1 = orderDetailDao.insertOrderDetail(orderDetail);
        orderDetail.setDetailId("202106160919");
        int affect2 = orderDetailDao.insertOrderDetail(orderDetail);
        Assert.assertSame(affect1, affect2);
    }

    @Test
    void testB_selectAll() {
        OrderDetail orderDetailCondition = new OrderDetail();
        orderDetailCondition.setProductName("new");
        List<OrderDetail> orderDetailList = orderDetailDao.queryOrderDetailList(orderDetailCondition);
        Assert.assertEquals(1,orderDetailList.size());
    }

    @Test
    void testC_selectById() {
        OrderDetail orderDetail = orderDetailDao.queryOrderDetailById("202106160319");
        Assert.assertNotNull(orderDetail);
    }

}
