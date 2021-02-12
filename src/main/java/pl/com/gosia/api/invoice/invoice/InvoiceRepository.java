package pl.com.gosia.api.invoice.invoice;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gosia.api.invoice.invoice.dto.*;

import java.util.List;
import java.util.Objects;

import static pl.com.gosia.api.invoice.invoice.InvoiceJdbcUtils.*;


@Repository
@RequiredArgsConstructor
public class InvoiceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final InvoiceStatement invoiceStatement;

    RowMapper<InvoiceDTOOut> invoiceRowMapper() {
        return (rs, rowNum) -> {
            String sqlQuery = "SELECT * FROM Invoice_items WHERE invoice_id = ?";
            List<InvoiceItems> invoiceItemsList = jdbcTemplate.query(sqlQuery,
                    preparedStatement -> preparedStatement.setLong(1, rs.getLong("invoice_id")),
                    INVOICE_ITEMS_ROW_MAPPER);
            return createInvoiceDtoOut(rs, invoiceItemsList);
        };
    }

    List<InvoiceDTOOut> findAllInvoice() {
        return jdbcTemplate.query(invoiceStatement.getSelectAllInvoices(), invoiceRowMapper());
    }

    @Transactional
    public void deleteInvoice(int invoice_id) {
        String sqlQuery = "DELETE FROM Invoice_items WHERE invoice_id = ?";
        jdbcTemplate.update(sqlQuery, invoice_id);

        String sqlQuery2 = "DELETE FROM Invoice WHERE invoice_id = ?";
        jdbcTemplate.update(sqlQuery2, invoice_id);
    }

    @Transactional
    public InvoiceDTOOut saveInvoice(InvoiceDTOCompany invoiceDTOCompany) {
        //moze wystapic null w comments,bankAccount jakos trzeba to zabezpieczyc przed nullpointerexception
        String sqlQuery2 = "INSERT INTO Invoice (invoice_number, seller_id, buyer_id, bank_account_number,comments,date_of_issue," +
                "date_of_sale,date_of_payment,payment_method,date_of_created) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(invoiceStatementSetter(invoiceDTOCompany, sqlQuery2), generatedKeyHolder);

        long invoiceId = Objects.requireNonNull(generatedKeyHolder.getKey()).longValue();

        List<InvoiceItems> invoiceItemsList = invoiceDTOCompany.getInvoiceItems();

        String sqlQuery = "INSERT INTO Invoice_items (invoice_id, name, unit, quantity,vat_rate,net_price," +
                "net_value,gross_value) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sqlQuery, invoiceItemsBatchStatementSetter(invoiceId, invoiceItemsList));

        return findAllInvoice().stream()
                .filter(invoiceDTOOut -> invoiceDTOOut.getInvoiceId().equals(invoiceId))
                .findFirst().orElseThrow();
    }

}
