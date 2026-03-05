package com.linkpay.skuBase.controller;

import com.linkpay.skuBase.api.StoreGetApiDelegate;
import com.linkpay.skuBase.model.Store;
import com.linkpay.skuBase.model.StoreGetResponse;
import com.linkpay.skuBase.service.StoreService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;


@Component
@Transactional
@GlobalTransactional
public class StoreApiControllerImpl implements StoreGetApiDelegate {

    @Autowired
    private NativeWebRequest nativeWebRequest;

    @Autowired
    StoreService storeService;

    @Override
    public ResponseEntity<StoreGetResponse> storeGet(Store cardIssuer) {
        var request = getRequest();
        return new ResponseEntity<>(storeService.storeGet(cardIssuer), HttpStatus.OK);
    }

}