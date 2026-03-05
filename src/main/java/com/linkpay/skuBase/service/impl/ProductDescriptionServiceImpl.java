package com.linkpay.skuBase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linkpay.skuBase.common.config.ParameterConfig;
import com.linkpay.skuBase.mapper.ProductDescriptionMapper;
import com.linkpay.skuBase.model.*;
import com.linkpay.skuBase.service.ProductDescriptionService;
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
public class ProductDescriptionServiceImpl extends ServiceImpl<ProductDescriptionMapper, ProductDescription> implements ProductDescriptionService {

    @Autowired
    ParameterConfig parameterConfig;

    private LambdaQueryWrapper<ProductDescription> getQueryWrapper(ProductDescription productDescription) {
        LambdaQueryWrapper<ProductDescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(productDescription.getId()), ProductDescription::getId, productDescription.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(productDescription.getProductId()), ProductDescription::getProductId, productDescription.getProductId());
        queryWrapper.last("LIMIT " + parameterConfig.sqlLimitSize.toString());
        return queryWrapper;
    }

    @Override
    public ProductDescriptionAddResponse productDescriptionAdd(ProductDescription productDescription) {
        var success = false;
        try {
            success = save(productDescription);
        } catch(Exception e) {
            log.info("fail to add product description, error: [{}]", e.getMessage(), e);
            return ResultUtil.error(
                    new ProductDescriptionAddResponse(),
                    ResponseCode.DB_INSERT_ERROR
            );
        }

        return ResultUtil.success(
                new ProductDescriptionAddResponse()
                        .data(
                                new Id()
                                        .id(productDescription.getId())
                        )
        );
    }

    @Override
    public ProductDescriptionGetResponse productDescriptionGet(ProductDescription productDescription) {
        var queryResult = list(getQueryWrapper(productDescription));
        if (queryResult == null) {
            return ResultUtil.success(new ProductDescriptionGetResponse());
        }
        return ResultUtil.success(
                new ProductDescriptionGetResponse()
                        .data(queryResult)
        );
    }
}
