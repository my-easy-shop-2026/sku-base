package com.linkpay.skuBase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linkpay.skuBase.model.ProductDescription;
import com.linkpay.skuBase.model.ProductDescriptionAddResponse;
import com.linkpay.skuBase.model.ProductDescriptionGetResponse;

public interface ProductDescriptionService extends IService<ProductDescription> {
    ProductDescriptionAddResponse productDescriptionAdd(ProductDescription productDescription);

    ProductDescriptionGetResponse productDescriptionGet(ProductDescription productDescription);
}
