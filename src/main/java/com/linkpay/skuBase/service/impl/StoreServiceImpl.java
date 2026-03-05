package com.linkpay.skuBase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linkpay.skuBase.common.config.ParameterConfig;
import com.linkpay.skuBase.mapper.StoreMapper;
import com.linkpay.skuBase.model.Store;
import com.linkpay.skuBase.model.StoreGetResponse;
import com.linkpay.skuBase.model.Store;
import com.linkpay.skuBase.service.StoreService;
import com.linkpay.commonModule.result.ResultUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@GlobalTransactional
@Transactional
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {

    @Autowired
    ParameterConfig parameterConfig;

    private LambdaQueryWrapper<Store> getQueryWrapper(Store store) {
        LambdaQueryWrapper<Store> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(store.getId()), Store::getId, store.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(store.getName()), Store::getName, store.getName());
        queryWrapper.last("LIMIT " + parameterConfig.sqlLimitSize.toString());
        return queryWrapper;
    }
    @Override
    public StoreGetResponse storeGet(Store store) {
        var queryResult = list(getQueryWrapper(store));
        if (queryResult == null) {
            return ResultUtil.success(new StoreGetResponse());
        }
        return ResultUtil.success(
                new StoreGetResponse()
                        .data(queryResult)
        );
    }
}
