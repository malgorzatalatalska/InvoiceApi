package pl.com.gosia.api.invoice.invoice;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class InvoiceDTOIn {
    String invoiceNumber;
    String sellerNip;
    String buyerNip;
    String bankAccountNumber;
    String Comment;
    LocalDateTime dateOfIssue;
    LocalDateTime dateOfSale;
    LocalDateTime dateOfPayment;
    PaymentMethod paymentMethod;
    InvoiceItems invoiceItems;
}
