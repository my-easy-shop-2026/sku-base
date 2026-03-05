package com.linkpay.skuBase.service;

import com.github.yulichang.base.MPJBaseService;
import com.linkpay.skuBase.api.ApiUtil;
import com.linkpay.skuBase.model.*;

public interface ProductService extends MPJBaseService<SkuProduct> {
    ProductDisplayResponse productDisplay(
        ProductDisplayRequest productDisplayRequest);

    ProductGetResponse productGet(ProductGetRequest productGetRequest);
}
