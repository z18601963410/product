package com.ike.order.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailDao {
    List<com.ike.order.entity.OrderDetail> queryOrderDetailList(@Param("orderDetailCondition") com.ike.order.entity.OrderDetail orderDetail);

    com.ike.order.entity.OrderDetail queryOrderDetailById(String orderDetailId);

    int insertOrderDetail(@Param("orderDetail") com.ike.order.entity.OrderDetail orderDetail);
}
