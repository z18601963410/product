package com.ike.order.service;

import com.ike.order.dto.OrderMasterExecution;
import com.ike.order.entity.OrderMaster;

public interface OrderMasterService {
     OrderMasterExecution addOrder(OrderMaster orderMaster) ;
}
