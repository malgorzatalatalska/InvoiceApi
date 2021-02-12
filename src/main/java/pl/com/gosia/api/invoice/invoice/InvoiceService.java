package pl.com.gosia.api.invoice.invoice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.gosia.api.invoice.company.CompanyService;
import pl.com.gosia.api.invoice.company.dto.CompanyView;
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
        // invoiceDTOIn -> InvoiceDTOCopmany
        CompanyView seller = companyService.getCompany(invoiceDTOIn.getSellerNip());
        CompanyView buyer = companyService.getCompany(invoiceDTOIn.getBuyerNip());

        InvoiceDTOCompany invoiceDTOCompany = new InvoiceDTOCompany(invoiceDTOIn.getInvoiceNumber(), seller, buyer, invoiceDTOIn.getBankAccountNumber(), invoiceDTOIn.getComments(),
                invoiceDTOIn.getDateOfIssue(), invoiceDTOIn.getDateOfSale(), invoiceDTOIn.getDateOfPayment(), invoiceDTOIn.getPaymentMethod(), invoiceDTOIn.getInvoiceItems());

        return invoiceRepository.saveInvoice(invoiceDTOCompany);
    }

    InvoiceDTOOut updateInvoice(InvoiceDTOIn invoiceDTOIn,int id) {
        return null;
    }

    void deleteInvoice(int id) {
        invoiceRepository.deleteInvoice(id);
    }
}
