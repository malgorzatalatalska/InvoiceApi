package pl.com.gosia.InvoiceApi.Invoice;

import lombok.Value;
import pl.com.gosia.InvoiceApi.Company.CompanyView;

import java.time.LocalDateTime;

@Value
public class InvoiceDTOOut {
    Long invoiceId;
    String invoiceNumber;
    CompanyView seller;
    CompanyView buyer;
    String bankAccountNumber;
    String Comment;
    LocalDateTime dateOfIssue;
    LocalDateTime dateOfSale;
    LocalDateTime dateOfPayment;
    Enum<PaymentMethod> paymentMethod;
    InvoiceItems invoiceItems;
    LocalDateTime dateOfCreated;
}
