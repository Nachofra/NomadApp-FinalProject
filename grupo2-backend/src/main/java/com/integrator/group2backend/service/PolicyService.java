package com.integrator.group2backend.service;

import com.integrator.group2backend.entities.Policy;
import com.integrator.group2backend.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PolicyService {
    private final PolicyRepository policyRepository;

    @Autowired
    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;

    }

    public Policy addPolicy(Policy policy){
        return this.policyRepository.save(policy);
    }

    public List<Policy> addPolicyList(List<Policy> policyList){
        return this.policyRepository.saveAll(policyList);
    }

    public List<Policy> getAllPolicy(){
        return this.policyRepository.findAll();
    }

    public Optional<Policy> getPolicyById(Long id){
        return this.policyRepository.findById(id);
    }


}
