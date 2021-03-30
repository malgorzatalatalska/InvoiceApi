package pl.com.gosia.api.invoice.invoice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceRequest;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceView;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping(value = "/api/invoices")
    List<InvoiceView> findAllInvoices() {

        log.debug("Querying all invoices");
        
        return invoiceService.findAllInvoices();
    }

    @PostMapping(value = "/api/invoices")
    InvoiceView addInvoice(@RequestBody InvoiceRequest invoiceRequest) {

        log.debug("Adding new invoice from request: {}", invoiceRequest);

        return invoiceService.addInvoice(invoiceRequest);
    }

    @PutMapping(value = "/api/invoices/{invoiceId}")
    InvoiceView updateInvoice(@RequestBody InvoiceRequest invoiceRequest, @PathVariable long invoiceId) {

        log.debug("Updating invoice id: {} with {}", invoiceId, invoiceRequest);

        return invoiceService.updateInvoice(invoiceRequest, invoiceId);
    }

    @DeleteMapping(value = "/api/invoices/{invoiceId}")
    void deleteInvoice(@PathVariable long invoiceId) {

        log.debug("Deleting invoice id: {}", invoiceId);

        invoiceService.deleteInvoice(invoiceId);
    }


}
