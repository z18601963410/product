package com.ike.product.dao;

import com.ike.product.BaseTest;
import com.ike.product.entity.ProductInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductInfoDaoTest extends BaseTest {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    void testA_productInfo_selectById() {
        ProductInfo productInfo = productInfoDao.queryProductInfoById("157875196366160022");
        System.out.println(productInfo);
    }
}
