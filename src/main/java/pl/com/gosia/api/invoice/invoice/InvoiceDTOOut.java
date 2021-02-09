package pl.com.gosia.api.invoice.invoice;

import lombok.Value;
import pl.com.gosia.api.invoice.company.CompanyView;

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
    PaymentMethod paymentMethod;
    InvoiceItems invoiceItems;
    LocalDateTime dateOfCreated;
}
