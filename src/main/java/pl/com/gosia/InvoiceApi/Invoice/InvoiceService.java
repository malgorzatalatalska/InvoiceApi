package pl.com.gosia.InvoiceApi.Invoice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceService {
     List<InvoiceDTOOut> findAllInvoices() {
        return null;
    }

    InvoiceDTOOut addInvoice(InvoiceDTOIn invoiceDTOIn) {
        return null;
    }

    InvoiceDTOOut updateInvoice(InvoiceDTOIn invoiceDTOIn,int id) {
        return null;
    }

    boolean deleteInvoice(int id) {
        return false;
    }
}
