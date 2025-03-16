package com.tuber.inventory.service.domain.helper.inventory;

import com.tuber.inventory.service.domain.entity.Product;

import java.util.Set;

public record GetProductsRecord(Set<Product> products, boolean hasUnavailableProducts) {}
