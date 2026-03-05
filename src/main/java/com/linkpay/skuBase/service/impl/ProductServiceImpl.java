package com.linkpay.skuBase.service.impl;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.linkpay.orderBase.api.OrderApi;
import com.linkpay.orderBase.model.Order;
import com.linkpay.orderBase.model.OrderGetResponse;
import com.linkpay.orderBase.model.OrderQuery;
import com.linkpay.skuBase.mapper.SkuProductMapper;
import com.linkpay.skuBase.model.SkuProduct;
import com.linkpay.skuBase.model.SkuProductAddResponse;
import com.linkpay.skuBase.model.SkuProductGetResponse;
import com.linkpay.skuBase.model.Product;
import com.linkpay.skuBase.model.ProductDescription;
import com.linkpay.skuBase.model.ProductDisplayRequest;
import com.linkpay.skuBase.model.ProductDisplayResponse;
import com.linkpay.skuBase.model.ProductGetRequest;
import com.linkpay.skuBase.model.ProductGetResponse;
import com.linkpay.skuBase.service.ProductService;
import com.linkpay.commonModule.constant.ResponseCode;
import com.linkpay.commonModule.result.ResultUtil;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl extends MPJBaseServiceImpl<SkuProductMapper, SkuProduct> implements ProductService {

    @Autowired
    OrderApi orderApi;

    private MPJLambdaWrapper<SkuProduct> getQueryWrapper(SkuProduct skuProduct, ProductDescription productDescription) {
        MPJLambdaWrapper<SkuProduct> queryWrapper = new MPJLambdaWrapper<SkuProduct>()
            .selectAll(SkuProduct.class)
            .selectAs(ProductDescription::getIntroduction, Product::getIntroduction)
            .selectAs(ProductDescription::getApplicationInstructions, Product::getApplicationInstructions)
            .selectAs(ProductDescription::getFeeSchedule, Product::getFeeSchedule)
            .selectAs(ProductDescription::getAgreement, Product::getAgreement)
            .leftJoin(ProductDescription.class, ProductDescription::getId, SkuProduct::getProductDescriptionId)
            .eq(ObjectUtils.isNotEmpty(skuProduct.getId()), SkuProduct::getId, skuProduct.getId())
            .eq(ObjectUtils.isNotEmpty(skuProduct.getStoreId()), SkuProduct::getStoreId, skuProduct.getStoreId());

        return queryWrapper;
    }

    @Override
    public ProductDisplayResponse productDisplay(ProductDisplayRequest productDisplayRequest) {

        var products = (List<Product>)null;
        try {
            products = selectJoinList(
                Product.class,
                getQueryWrapper(
                    new SkuProduct(),
                    new ProductDescription())
            );
        } catch (Exception e) {
            log.info("fail to get product, error: [{}]", e.getMessage(), e);
            return ResultUtil.error(
                new ProductDisplayResponse(),
                ResponseCode.DB_QUERY_ERROR
            );
        }

        products.stream().forEach(e -> {
            e.setIsApplied(false);
        });

        var cardOrderGetResult = (ResponseEntity<OrderGetResponse>)null;
        try {
            cardOrderGetResult = orderApi.orderGet(
                new OrderQuery()
                    .userId(productDisplayRequest.getUserId())
            );
        } catch (Exception e) {
            log.info("fail to get card order, error: [{}]", e.getMessage(), e);
            return ResultUtil.error(
                new ProductDisplayResponse(),
                ResponseCode.INTERNAL_REQUEST_ERROR
            );
        }

        if (!cardOrderGetResult.getStatusCode().is2xxSuccessful()
            || cardOrderGetResult.getBody() == null
            || !cardOrderGetResult.getBody().getSuccess()) {
            log.info("fail to get card order, response: [{}]", cardOrderGetResult);
            return ResultUtil.error(
                new ProductDisplayResponse(),
                ResponseCode.INTERNAL_REQUEST_ERROR
            );
        }

        var cardOrders = cardOrderGetResult.getBody().getData() == null ?
            new ArrayList<Order>() :
            cardOrderGetResult.getBody().getData();

        for(var o : cardOrders) {
            for (var p : products) {
                if (o.getProductId().equals(p.getId())) {
                    p.setIsApplied(true);
                }
            }
        }

        return ResultUtil.success(
            new ProductDisplayResponse()
                .data(products)
        );
    }

    @Override
    public ProductGetResponse productGet(ProductGetRequest productGetRequest) {

        var skuProduct = new SkuProduct();
        BeanUtils.copyProperties(productGetRequest, skuProduct);

        var products = (List<Product>)null;
        try {
            products = selectJoinList(
                Product.class,
                getQueryWrapper(
                    skuProduct,
                    new ProductDescription())
            );
        } catch (Exception e) {
            log.info("fail to get product, error: [{}]", e.getMessage(), e);
            return ResultUtil.error(
                new ProductGetResponse(),
                ResponseCode.DB_QUERY_ERROR
            );
        }

        products.stream().forEach(e -> {
            e.setIsApplied(false);
        });

        var cardOrderGetResult = (ResponseEntity<OrderGetResponse>)null;
        try {
            cardOrderGetResult = orderApi.orderGet(
                new OrderQuery()
                    .userId(productGetRequest.getUserId())
            );
        } catch (Exception e) {
            log.info("fail to get card order, error: [{}]", e.getMessage(), e);
            return ResultUtil.error(
                new ProductGetResponse(),
                ResponseCode.INTERNAL_REQUEST_ERROR
            );
        }

        if (!cardOrderGetResult.getStatusCode().is2xxSuccessful()
            || cardOrderGetResult.getBody() == null
            || !cardOrderGetResult.getBody().getSuccess()) {
            return ResultUtil.error(
                new ProductGetResponse(),
                ResponseCode.INTERNAL_REQUEST_ERROR
            );
        }

        var cardOrders = cardOrderGetResult.getBody().getData() == null ?
            new ArrayList<Order>() :
            cardOrderGetResult.getBody().getData();

        for(var o : cardOrders) {
            for (var p : products) {
                if (o.getProductId().equals(p.getId())) {
                    p.setIsApplied(true);
                }
            }
        }

        return ResultUtil.success(
            new ProductGetResponse()
                .data(products)
        );
    }
}
