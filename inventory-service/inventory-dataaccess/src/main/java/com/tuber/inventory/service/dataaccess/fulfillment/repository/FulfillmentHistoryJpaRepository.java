package com.tuber.inventory.service.dataaccess.fulfillment.repository;

import com.tuber.inventory.service.dataaccess.fulfillment.entity.FulfillmentHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FulfillmentHistoryJpaRepository extends JpaRepository<FulfillmentHistoryJpaEntity, UUID> {
}
