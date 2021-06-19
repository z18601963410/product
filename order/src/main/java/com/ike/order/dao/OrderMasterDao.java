package com.ike.order.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMasterDao {
    List<com.ike.order.entity.OrderMaster> queryOrderMasterList(@Param("orderMasterCondition") com.ike.order.entity.OrderMaster orderMaster);

    com.ike.order.entity.OrderMaster queryOrderMasterById(String OrderMasterId);

    int insertOrderMaster(@Param("orderMaster") com.ike.order.entity.OrderMaster orderMaster);
}
