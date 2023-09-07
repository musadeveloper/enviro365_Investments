package com.enviro.assessment.grad001.musawenkosimahlangu.service;

import com.enviro.assessment.grad001.musawenkosimahlangu.model.Investor;
import com.enviro.assessment.grad001.musawenkosimahlangu.model.Product;
import com.enviro.assessment.grad001.musawenkosimahlangu.model.WithdrawalNotice;
import com.enviro.assessment.grad001.musawenkosimahlangu.model.enums.InvestmentType;
import com.enviro.assessment.grad001.musawenkosimahlangu.repository.ProductRepository;
import com.enviro.assessment.grad001.musawenkosimahlangu.repository.WithdrawalNoticeRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class WithdrawalNoticeService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WithdrawalNoticeRepository withdrawalNoticeRepository;

    public void createWithdrawalNotice(Long productId, double withdrawalAmount) throws NotFoundException, ValidationException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));


        if (InvestmentType.RETIREMENT.equals(product.getType())) {
            Investor investor = product.getInvestor();
            int investorAge = calculateInvestorAge(investor.getDateOfBirth());


            if (investorAge <= 65) {
                throw new ValidationException("Investor's age must be greater than 65 for RETIREMENT products.");
            }

            if (withdrawalAmount > product.getCurrentBalance()) {
                throw new ValidationException("Withdrawal amount cannot be greater than the current balance.");
            }

            double maxWithdrawalAmount = 0.9 * product.getCurrentBalance();


            if (withdrawalAmount > maxWithdrawalAmount) {
                throw new ValidationException("Withdrawal amount exceeds the maximum limit.");
            }
        }else {
            throw new ValidationException("Product type must be RETIREMENT.");
        }

        WithdrawalNotice notice = new WithdrawalNotice();

        notice.setProduct(product);
        notice.setWithdrawalAmount(withdrawalAmount);
        notice.setWithdrawalDate(LocalDate.now());

        product.setCurrentBalance((product.getCurrentBalance() - withdrawalAmount));

        productRepository.save(product);
        withdrawalNoticeRepository.save(notice);

    }

    private int calculateInvestorAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dateOfBirth, currentDate).getYears();
    }

    public List<WithdrawalNotice> getNoticesByProductAndDateRange(Long productId, LocalDate startDate, LocalDate endDate) {
        return withdrawalNoticeRepository.findByProductIdAndWithdrawalDateBetween(productId, startDate, endDate);
    }

}
