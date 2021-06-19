package com.ike.product.dao;

import com.ike.product.entity.ProductCategory;
import com.ike.product.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductInfoDao {
    int insertProductInfo(ProductInfo productInfo);

    int deleteProductInfo(@Param("productInfoId") int pInfoId);

    int updateProductInfo(@Param("productInfo") ProductInfo pInfo);

    List<ProductCategory> queryProductInfoList(@Param("productInfoCondition") ProductInfo productInfo);

    ProductInfo queryProductInfoById(String productInfoId);
}
