package pl.com.gosia.api.invoice.invoice;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class InvoiceValidator {

    private boolean isFreeInvoiceNumber(String invoiceNumber, String sellerNip) {
        return false;
    }

    private boolean isCorrectBankAccount(String bankAccountNumber) {
        return false;
    }

    private boolean isCorrectComment(String comment) {
        return false;
    }

    private boolean isCorrectDateOfIssueOrSale(LocalDateTime date) {
        return false;
    }

    private boolean isCorrectDateOfPayment(LocalDateTime date) {
        return false;
    }

    private boolean isCorrectNameInvoiceItems(String nameInvoiceItems) {
        return false;
    }

    private boolean isCorrectQuantity(double quantity) {
        return false;
    }

    private boolean isCorrectNetPrice(BigDecimal netPrice) {
        return false;
    }


}
