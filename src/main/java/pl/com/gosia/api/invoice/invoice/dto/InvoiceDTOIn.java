package pl.com.gosia.api.invoice.invoice.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class InvoiceDTOIn {
    String invoiceNumber;
    String sellerNip;
    String buyerNip;
    String bankAccountNumber;
    String comments;
    LocalDateTime dateOfIssue;
    LocalDateTime dateOfSale;
    LocalDateTime dateOfPayment;
    PaymentMethod paymentMethod;
    List<InvoiceItems> invoiceItems;
}
