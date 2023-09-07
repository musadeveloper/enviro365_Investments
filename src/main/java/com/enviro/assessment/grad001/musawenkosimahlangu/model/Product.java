package com.enviro.assessment.grad001.musawenkosimahlangu.model;

import com.enviro.assessment.grad001.musawenkosimahlangu.model.enums.InvestmentType;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;


@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InvestmentType type;

    private String name;
    private double currentBalance;

    @JsonIgnore
    @ManyToOne
    private Investor investor;

    public Product() {
    }

    public Product(InvestmentType type, String name, double currentBalance) {
        this.type = type;
        this.name = name;
        this.currentBalance = currentBalance;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvestmentType getType() {
        return type;
    }

    public void setType(InvestmentType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    @JsonIgnore
    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }
}
