package pl.com.gosia.InvoiceApi.Invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Validator {

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
