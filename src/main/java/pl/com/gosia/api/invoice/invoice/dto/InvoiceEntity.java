package pl.com.gosia.api.invoice.invoice.dto;

import lombok.Builder;
import lombok.Value;
import pl.com.gosia.api.invoice.company.dto.CompanyView;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class InvoiceEntity {
    String invoiceNumber;
    CompanyView seller;
    CompanyView buyer;
    String bankAccountNumber;
    String comments;
    LocalDateTime dateOfIssue;
    LocalDateTime dateOfSale;
    LocalDateTime dateOfPayment;
    PaymentMethod paymentMethod;
    List<InvoiceItem> invoiceItems;
}
