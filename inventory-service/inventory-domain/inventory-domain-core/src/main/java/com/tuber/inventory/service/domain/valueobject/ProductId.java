package com.tuber.inventory.service.domain.valueobject;

import com.tuber.domain.valueobject.id.BaseId;

public class ProductId extends BaseId<ProductIdVO> {
    public ProductId(ProductIdVO productIdVO) {
        super(productIdVO);
    }
}
