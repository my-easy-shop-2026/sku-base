package com.linkpay.skuBase.controller;

import com.linkpay.skuBase.api.ApiUtil;
import com.linkpay.skuBase.api.ProductDescriptionApi;
import com.linkpay.skuBase.api.ProductDescriptionApiDelegate;
import com.linkpay.skuBase.api.ProductDescriptionGetApiDelegate;
import com.linkpay.skuBase.model.*;
import com.linkpay.skuBase.service.ProductDescriptionService;
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
public class ProductDescriptionApiControllerImpl implements ProductDescriptionApiDelegate,
    ProductDescriptionGetApiDelegate {

    @Autowired
    private NativeWebRequest nativeWebRequest;

    @Autowired
    ProductDescriptionService productDescriptionService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ProductDescriptionApiDelegate.super.getRequest();
    }

    @Override
    public ResponseEntity<ProductDescriptionAddResponse> productDescriptionAdd(ProductDescription productDescription) {
        return new ResponseEntity<>(productDescriptionService.productDescriptionAdd(productDescription), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ProductDescriptionGetResponse> productDescriptionGet(ProductDescription productDescription) {
        return new ResponseEntity<>(productDescriptionService.productDescriptionGet(productDescription), HttpStatus.OK);
    }
}