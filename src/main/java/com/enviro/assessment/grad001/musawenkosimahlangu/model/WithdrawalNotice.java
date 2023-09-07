package com.enviro.assessment.grad001.musawenkosimahlangu.model;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class WithdrawalNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate withdrawalDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private double withdrawalAmount;

    public WithdrawalNotice() {
    }

    public WithdrawalNotice(Long id, LocalDate withdrawalDate, Product product, double withdrawalAmount) {
        this.id = id;
        this.withdrawalDate = withdrawalDate;
        this.product = product;
        this.withdrawalAmount = withdrawalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(LocalDate withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }
}
