package com.linkpay.skuBase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linkpay.skuBase.model.*;

public interface SkuProductService extends IService<SkuProduct> {
    SkuProductAddResponse skuProductAdd(SkuProduct skuProduct);

    SkuProductGetResponse skuProductGet(SkuProduct skuProduct);

}
