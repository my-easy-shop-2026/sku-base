package com.linkpay.skuBase.controller;

import com.linkpay.skuBase.api.ProductApiDelegate;
import com.linkpay.skuBase.api.ProductGetApiDelegate;
import com.linkpay.skuBase.model.ProductDisplayRequest;
import com.linkpay.skuBase.model.ProductDisplayResponse;
import com.linkpay.skuBase.model.ProductGetRequest;
import com.linkpay.skuBase.model.ProductGetResponse;
import com.linkpay.skuBase.service.ProductService;
import io.seata.spring.annotation.GlobalTransactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;


@Component
@Transactional
@GlobalTransactional
public class ProductApiControllerImpl implements ProductApiDelegate, ProductGetApiDelegate {

    @Autowired
    private NativeWebRequest nativeWebRequest;

    @Autowired
    ProductService productService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ProductApiDelegate.super.getRequest();
    }

    @Override
    public ResponseEntity<ProductDisplayResponse> productDisplay(
        ProductDisplayRequest productDisplayRequest) {
        return new ResponseEntity<>(productService.productDisplay(productDisplayRequest), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductGetResponse> productGet(ProductGetRequest productGetRequest) {
        return new ResponseEntity<>(productService.productGet(productGetRequest), HttpStatus.OK);
    }

}