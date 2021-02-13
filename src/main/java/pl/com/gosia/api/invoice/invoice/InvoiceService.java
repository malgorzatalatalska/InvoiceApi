package pl.com.gosia.api.invoice.invoice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.gosia.api.invoice.company.CompanyService;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceDTOCompany;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceDTOIn;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceDTOOut;

import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CompanyService companyService;

    List<InvoiceDTOOut> findAllInvoices() {
        return invoiceRepository.findAllInvoice();
    }

    InvoiceDTOOut addInvoice(InvoiceDTOIn invoiceDTOIn) {
        InvoiceDTOCompany invoiceDTOCompany =
                InvoiceDTOCompany.builder()
                        .invoiceNumber(invoiceDTOIn.getInvoiceNumber())
                        .seller(companyService.getCompany(invoiceDTOIn.getSellerNip()))
                        .buyer(companyService.getCompany(invoiceDTOIn.getBuyerNip()))
                        .bankAccountNumber(invoiceDTOIn.getBankAccountNumber())
                        .comments(invoiceDTOIn.getComments())
                        .dateOfIssue(invoiceDTOIn.getDateOfIssue())
                        .dateOfSale(invoiceDTOIn.getDateOfSale())
                        .dateOfPayment(invoiceDTOIn.getDateOfPayment())
                        .paymentMethod(invoiceDTOIn.getPaymentMethod())
                        .invoiceItems(invoiceDTOIn.getInvoiceItems())
                        .build();

        return invoiceRepository.saveInvoice(invoiceDTOCompany);
    }

    InvoiceDTOOut updateInvoice(InvoiceDTOIn invoiceDTOIn, int id) {
        return null;
    }

    void deleteInvoice(int id) {
        invoiceRepository.deleteInvoice(id);
    }
}
