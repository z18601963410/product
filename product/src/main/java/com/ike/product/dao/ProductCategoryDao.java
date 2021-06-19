package com.ike.product.dao;

import com.ike.product.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {

    int insertProductCategory(ProductCategory productCategory);

    int deleteProductCategory(@Param("productCategoryId") int pcId);

    int updateProductCategory(@Param("productCategory") ProductCategory pc);

    List<ProductCategory>queryProductCategoryList(@Param("productCategoryCondition") ProductCategory productCategory);

    ProductCategory queryProductCategoryById(int productCategoryId);
}
