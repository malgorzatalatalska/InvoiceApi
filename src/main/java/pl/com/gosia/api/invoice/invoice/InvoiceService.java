package pl.com.gosia.api.invoice.invoice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.gosia.api.invoice.company.CompanyService;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceEntity;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceRequest;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceView;

import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CompanyService companyService;

    List<InvoiceView> findAllInvoices() {
        return invoiceRepository.findAllInvoice();
    }

    InvoiceView addInvoice(InvoiceRequest invoiceRequest) {
        final var invoiceDTOCompany = mapToInvoiceEntity(invoiceRequest);
        return invoiceRepository.saveInvoice(invoiceDTOCompany);
    }
    
    InvoiceView updateInvoice(InvoiceRequest invoiceRequest, long invoiceId) {
        final var invoiceDTOCompany = mapToInvoiceEntity(invoiceRequest);
        return invoiceRepository.updateInvoice(invoiceDTOCompany,invoiceId);
    }

    void deleteInvoice(long invoiceId) {
        invoiceRepository.deleteInvoice(invoiceId);
    }

    private InvoiceEntity mapToInvoiceEntity(InvoiceRequest invoiceRequest) {
        return InvoiceEntity.builder()
                .invoiceNumber(invoiceRequest.getInvoiceNumber())
                .seller(companyService.findCompany(invoiceRequest.getSellerNip()))
                .buyer(companyService.findCompany(invoiceRequest.getBuyerNip()))
                .bankAccountNumber(invoiceRequest.getBankAccountNumber())
                .comments(invoiceRequest.getComments())
                .dateOfIssue(invoiceRequest.getDateOfIssue())
                .dateOfSale(invoiceRequest.getDateOfSale())
                .dateOfPayment(invoiceRequest.getDateOfPayment())
                .paymentMethod(invoiceRequest.getPaymentMethod())
                .invoiceItems(invoiceRequest.getInvoiceItems())
                .build();
    }
}
