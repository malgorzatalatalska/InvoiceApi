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
            List<InvoiceItems> invoiceItemsList = jdbcTemplate.query(invoiceStatement.getSelectInvoiceItemsByInvoiceId(),
                    preparedStatement -> preparedStatement.setLong(1, rs.getLong("invoice_id")),
                    INVOICE_ITEMS_ROW_MAPPER);
            return createInvoiceDtoOut(rs, invoiceItemsList);
        };
    }

    List<InvoiceDTOOut> findAllInvoice() {
        return jdbcTemplate.query(invoiceStatement.getSelectInvoices(), invoiceRowMapper());
    }

    @Transactional
    public void deleteInvoice(int invoiceId) {
        jdbcTemplate.update(invoiceStatement.getDeleteInvoiceItems(), invoiceId);
        jdbcTemplate.update(invoiceStatement.getDeleteInvoice(), invoiceId);
    }

    @Transactional
    public InvoiceDTOOut saveInvoice(InvoiceDTOCompany invoiceDTOCompany) {
        //moze wystapic null w comments,bankAccount jakos trzeba to zabezpieczyc przed nullpointerexception

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(invoiceStatementSetter(invoiceDTOCompany, invoiceStatement.getInsertInvoice()), generatedKeyHolder);

        long invoiceId = Objects.requireNonNull(generatedKeyHolder.getKey()).longValue();

        List<InvoiceItems> invoiceItemsList = invoiceDTOCompany.getInvoiceItems();

        jdbcTemplate.batchUpdate(invoiceStatement.getInsertInvoiceItems(), invoiceItemsBatchStatementSetter(invoiceId, invoiceItemsList));

        return findAllInvoice().stream()
                .filter(invoiceDTOOut -> invoiceDTOOut.getInvoiceId().equals(invoiceId))
                .findFirst().orElseThrow();
    }

}
