package com.linkpay.skuBase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linkpay.skuBase.model.*;

public interface StoreService extends IService<Store> {
    StoreGetResponse storeGet(Store store);
}
