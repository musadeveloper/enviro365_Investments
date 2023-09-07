package com.enviro.assessment.grad001.musawenkosimahlangu.service;

import com.enviro.assessment.grad001.musawenkosimahlangu.model.Investor;
import com.enviro.assessment.grad001.musawenkosimahlangu.repository.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class InvestorService {

    private final InvestorRepository investorRepository;

    @Autowired
    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }


    public Investor getInvestorById(Long id) {
        return investorRepository.findById(id).orElse(null);
    }
}
