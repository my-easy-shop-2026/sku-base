package com.linkpay.skuBase.controller;

import com.linkpay.skuBase.api.SkuApiDelegate;
import com.linkpay.skuBase.api.SkuGetApiDelegate;
import com.linkpay.skuBase.model.SkuProduct;
import com.linkpay.skuBase.model.SkuProductAddResponse;
import com.linkpay.skuBase.model.SkuProductGetResponse;
import com.linkpay.skuBase.service.SkuProductService;
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
public class SkuProductApiControllerImpl implements SkuApiDelegate, SkuGetApiDelegate {

    @Autowired
    private NativeWebRequest nativeWebRequest;

    @Autowired
    SkuProductService skuProductService;


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return SkuApiDelegate.super.getRequest();
    }

    @Override
    public ResponseEntity<SkuProductAddResponse> skuAdd(SkuProduct cardProduct) {
        return new ResponseEntity<>(skuProductService.skuProductAdd(cardProduct), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<SkuProductGetResponse> skuGet(SkuProduct cardProduct) {
        return new ResponseEntity<>(skuProductService.skuProductGet(cardProduct), HttpStatus.OK);

    }
}