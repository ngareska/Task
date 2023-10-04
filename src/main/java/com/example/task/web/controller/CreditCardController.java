package com.example.task.web.controller;

import com.example.task.model.CreditCard;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CreditCardController {

    @GetMapping("/credit-card")
    public String showCreditCardForm(Model model) {
        model.addAttribute("creditCard", new CreditCard());
        return "credit-card-form";
    }
    @PostMapping(value = "api/validate-credit-card", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> validateCreditCardApi(@RequestBody CreditCard creditCard) {
        boolean isValid = validateCreditCardData(creditCard);
        Map<String, Object> response = new HashMap<>();

        if (isValid) {
            response.put("success", true);
            response.put("message", "Successful!");
        } else {
            response.put("success", false);
            response.put("message", "Invalid credit card details. Please check your input.");
        }

        return ResponseEntity.ok(response);
    }

    private boolean validateCreditCardData(CreditCard creditCard) {
        String cardNumber = creditCard.getCardNumber();
        String cvv = creditCard.getCvv();
        String expiryDate = creditCard.getExpiryDate();
        boolean isValid = true;

        // Check card number length and format
        if (cardNumber == null || !cardNumber.matches("\\d{4}[-\\s]?\\d{4}[-\\s]?\\d{4}[-\\s]?\\d{4,7}")) {
            isValid = false;
        } else if (!isValidLuhnAlgorithm(cardNumber)) {
            isValid = false;
        }

        // Check expiry date
        if (!isValidExpiryDate(expiryDate)) {
            isValid = false;
        }

        // Check CVV length and format based on card type
        if (isAmericanExpress(cardNumber)) {
            if (cvv == null || cvv.length() != 4 || !cvv.matches("\\d{4}")) {
                isValid = false;
            }
        } else {
            if (cvv == null || cvv.length() != 3 || !cvv.matches("\\d{3}")) {
                isValid = false;
            }
        }

        return isValid;
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

    private boolean isValidLuhnAlgorithm(String cardNumber) {
        int[] digits = new int[cardNumber.length()];
        boolean isEven = false;
        int sum = 0;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (isEven) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            isEven = !isEven;
        }

        return sum % 10 == 0;
    }
}
