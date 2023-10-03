package com.example.task.web.controller;

import com.example.task.model.CreditCard;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Controller
public class CreditCardController {

    @GetMapping("/credit-card")
    public String showCreditCardForm(Model model) {
        model.addAttribute("creditCard", new CreditCard());
        return "credit-card-form";
    }

    @PostMapping("/validate-credit-card")
    public String validateCreditCard(@ModelAttribute("creditCard") CreditCard creditCard, Model model) {
        boolean isValid = validateCreditCardData(creditCard);


        if (isValid) {
            model.addAttribute("validationMessage", "Validation successful!");
        } else {
            model.addAttribute("validationMessage", "Validation failed!");
        }
        return "validation-result";
    }

    private boolean validateCreditCardData(CreditCard creditCard) {
        String cardNumber = creditCard.getCardNumber();
        String cvv = creditCard.getCvv();
        String expiryDate = creditCard.getExpiryDate();

        // Check card number length and format
        if (cardNumber == null || !cardNumber.matches("\\d{16,19}")) {
            return false;
        }

        // Check expiry date
        if (!isValidExpiryDate(expiryDate)) {
            return false;
        }

        // Check CVV length and format based on card type
        if (isAmericanExpress(cardNumber)) {
            if (cvv == null || cvv.length() != 4 || !cvv.matches("\\d{4}")) {
                return false;
            }
        } else {
            if (cvv == null || cvv.length() != 3 || !cvv.matches("\\d{3}")) {
                return false;
            }
        }

        return true;
    }

    private boolean isAmericanExpress(String cardNumber) {
        return cardNumber != null && (cardNumber.startsWith("34") || cardNumber.startsWith("37"));
    }

    private boolean isValidExpiryDate(String expiryDate) {
        if (expiryDate == null || expiryDate.isEmpty()) {
            return false;
        }

        try {
            // Parse the expiry date string into a YearMonth object
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth expiryYearMonth = YearMonth.parse(expiryDate, formatter);

            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Check if the expiry date is after the current date
            return expiryYearMonth.isAfter(YearMonth.from(currentDate));
        } catch (Exception e) {
            return false;
        }
    }
}
