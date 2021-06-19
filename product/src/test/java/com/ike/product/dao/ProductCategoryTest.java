package com.ike.product.dao;

import com.ike.product.BaseTest;
import com.ike.product.entity.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class ProductCategoryTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    void testA_productCategory_queryAll() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("吃");
        productCategory.setCategoryType(22);
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(productCategory);
        Assert.assertEquals(1, productCategoryList.size());
    }

    @Test
    void testB_productCategory_queryById() {
        ProductCategory productCategory = productCategoryDao.queryProductCategoryById(1);
        Assert.assertNotNull(productCategory);
    }
}
