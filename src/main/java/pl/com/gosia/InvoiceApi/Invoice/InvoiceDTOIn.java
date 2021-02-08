package pl.com.gosia.InvoiceApi.Invoice;

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
    Enum<PaymentMethod> paymentMethod;
    InvoiceItems invoiceItems;
}
