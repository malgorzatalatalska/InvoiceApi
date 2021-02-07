package pl.com.gosia.InvoiceApi.Invoice;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class InvoiceDTOIn {
    String invoiceNumber;
    Long sellerId;
    Long buyerId;
    int bankAccountNumber;
    String Comments;
    LocalDateTime dateOfIssue;
    LocalDateTime dateOfSale;
    LocalDateTime dateOfPayment;
    Enum<PaymentMethod> paymentMethod;
}
