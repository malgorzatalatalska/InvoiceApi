package pl.com.gosia.InvoiceApi.Invoice;

import lombok.Value;
import pl.com.gosia.InvoiceApi.Company.CompanyDTO;

import java.time.LocalDateTime;

@Value
public class InvoiceDTOOut {
    Long invoiceId;
    String invoiceNumber;
    CompanyDTO companyDTOSeller;
    CompanyDTO companyDTOBuyer;
    String bankAccountNumber;
    String Comments;
    LocalDateTime dateOfIssue;
    LocalDateTime dateOfSale;
    LocalDateTime dateOfPayment;
    Enum<PaymentMethod> paymentMethod;
    LocalDateTime dateOfCreated;
}
