package com.linkpay.skuBase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linkpay.skuBase.common.config.ParameterConfig;
import com.linkpay.skuBase.mapper.SkuProductMapper;
import com.linkpay.skuBase.model.*;
import com.linkpay.skuBase.service.SkuProductService;
import com.linkpay.commonModule.constant.ResponseCode;
import com.linkpay.commonModule.result.ResultUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@GlobalTransactional
@Transactional
@Service
@Slf4j
public class SkuProductServiceImpl extends ServiceImpl<SkuProductMapper, SkuProduct> implements SkuProductService {

    @Autowired
    ParameterConfig parameterConfig;

    private LambdaQueryWrapper<SkuProduct> getQueryWrapper(SkuProduct skuProduct) {
        LambdaQueryWrapper<SkuProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(skuProduct.getId()), SkuProduct::getId, skuProduct.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(skuProduct.getName()), SkuProduct::getName, skuProduct.getName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(skuProduct.getStoreId()), SkuProduct::getStoreId, skuProduct.getStoreId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(skuProduct.getCurrency()), SkuProduct::getCurrency, skuProduct.getCurrency());
        queryWrapper.last("LIMIT " + parameterConfig.sqlLimitSize.toString());
        return queryWrapper;
    }

    @Override
    public SkuProductAddResponse skuProductAdd(SkuProduct skuProduct) {

        var success = false;
        try {
            success = save(skuProduct);
        } catch(Exception e) {
            log.info("fail to add card product, error: [{}]", e.getMessage(), e);
            return ResultUtil.error(
                    new SkuProductAddResponse(),
                    ResponseCode.DB_INSERT_ERROR
            );
        }

        if (!success) {
            return ResultUtil.error(
                    new SkuProductAddResponse(),
                    ResponseCode.DB_INSERT_ERROR
            );
        }

        return ResultUtil.success(
                new SkuProductAddResponse()
                        .data(
                                new Id()
                                        .id(skuProduct.getId())
                        )
        );
    }

    @Override
    public SkuProductGetResponse skuProductGet(SkuProduct skuProduct) {
        var queryResult = list(getQueryWrapper(skuProduct));
        if (queryResult == null) {
            return ResultUtil.success(new SkuProductGetResponse());
        }
        return ResultUtil.success(
                new SkuProductGetResponse()
                        .data(queryResult)
        );
    }
}
