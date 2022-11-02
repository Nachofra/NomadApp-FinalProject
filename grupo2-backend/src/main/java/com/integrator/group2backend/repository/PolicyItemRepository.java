package com.integrator.group2backend.repository;

import com.integrator.group2backend.entities.PolicyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyItemRepository extends JpaRepository<PolicyItem, Long> {
}
