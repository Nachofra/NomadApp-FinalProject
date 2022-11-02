package com.integrator.group2backend.service;

import com.integrator.group2backend.entities.Policy;
import com.integrator.group2backend.repository.PolicyItemRepository;
import com.integrator.group2backend.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PolicyService {
    private final PolicyRepository policyRepository;
    private final PolicyItemRepository policyItemRepository;
    @Autowired
    public PolicyService(PolicyRepository policyRepository, PolicyItemRepository policyItemRepository) {
        this.policyRepository = policyRepository;
        this.policyItemRepository = policyItemRepository;
    }

    public Policy addPolicy(Policy policy){
        return this.policyRepository.save(policy);
    }

    public List<Policy> getAllPolicy(){
        return this.policyRepository.findAll();
    }

    public Optional<Policy> getPolicyById(Long id){
        return this.policyRepository.findById(id);
    }


}
