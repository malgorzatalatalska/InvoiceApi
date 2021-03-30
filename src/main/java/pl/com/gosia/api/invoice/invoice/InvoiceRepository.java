package pl.com.gosia.api.invoice.invoice;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceEntity;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceItem;
import pl.com.gosia.api.invoice.invoice.dto.InvoiceView;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static pl.com.gosia.api.invoice.invoice.InvoiceJdbcUtils.*;


@Repository
@RequiredArgsConstructor
public class InvoiceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final InvoiceStatement invoiceStatement;

    List<InvoiceView> findAllInvoice() {

        final var selectInvoicesQuery = invoiceStatement.getSelectInvoicesQuery();

        return jdbcTemplate.query(
                selectInvoicesQuery,
                invoiceRowMapper());
    }

    Optional<InvoiceView> findInvoiceById(long invoiceId) {

        final var selectInvoicesByInvoiceIdQuery = invoiceStatement.getSelectInvoicesByInvoiceIdQuery();

        return jdbcTemplate.query(
                selectInvoicesByInvoiceIdQuery,
                preparedStatement -> preparedStatement.setLong(1, invoiceId),
                invoiceRowMapper())
                .stream()
                .findFirst();
    }

    @Transactional
    public InvoiceView updateInvoice(InvoiceEntity invoiceEntity, long invoiceId) {

        final var sqlQueryUpdateInvoiceByInvoiceId = invoiceStatement.getUpdateInvoiceByInvoiceIdQuery();
        final var sqlQueryDeleteInvoiceItems = invoiceStatement.getDeleteInvoiceItemsQuery();
        final var sqlQueryInsertInvoiceItems = invoiceStatement.getInsertInvoiceItemsQuery();

        if (findInvoiceById(invoiceId).isEmpty()) {
            throw new IllegalArgumentException("Invoice for id: " + invoiceId + " not found");
        }

        jdbcTemplate.update(sqlQueryUpdateInvoiceByInvoiceId, invoiceStatementSetter(invoiceEntity, invoiceId));
        jdbcTemplate.update(sqlQueryDeleteInvoiceItems, invoiceId);
        jdbcTemplate.batchUpdate(sqlQueryInsertInvoiceItems,
                invoiceItemsBatchStatementSetter(invoiceId, invoiceEntity.getInvoiceItems()));

        return findInvoiceById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice for id: " + invoiceId + " not found"));
    }

    @Transactional
    public InvoiceView saveInvoice(InvoiceEntity invoiceEntity) {

        final var sqlQueryInsertInvoice = invoiceStatement.getInsertInvoiceQuery();
        final var invoiceStatementCreator =
                invoiceStatementCreator(invoiceEntity, sqlQueryInsertInvoice);
        final var generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(invoiceStatementCreator, generatedKeyHolder);

        final var invoiceId = requireNonNull(generatedKeyHolder.getKey()).longValue();

        final var sqlQueryInsertInvoiceItems = invoiceStatement.getInsertInvoiceItemsQuery();
        final var batchStatementSetter =
                invoiceItemsBatchStatementSetter(invoiceId, invoiceEntity.getInvoiceItems());

        jdbcTemplate.batchUpdate(sqlQueryInsertInvoiceItems, batchStatementSetter);

        return findInvoiceById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice for id: " + invoiceId + " not found"));
    }

    @Transactional
    public void deleteInvoice(long invoiceId) {
        final var sqlQueryDeleteInvoiceItems = invoiceStatement.getDeleteInvoiceItemsQuery();
        final var sqlQueryDeleteInvoice = invoiceStatement.getDeleteInvoiceQuery();

        jdbcTemplate.update(sqlQueryDeleteInvoiceItems, invoiceId);
        jdbcTemplate.update(sqlQueryDeleteInvoice, invoiceId);
    }

    private RowMapper<InvoiceView> invoiceRowMapper() {
        final var sqlQuerySelectInvoiceItemsByInvoiceId = invoiceStatement.getSelectInvoiceItemsByInvoiceIdQuery();

        return (rs, rowNum) -> {
            List<InvoiceItem> invoiceItemList = jdbcTemplate.query(
                    sqlQuerySelectInvoiceItemsByInvoiceId,
                    preparedStatement -> preparedStatement.setLong(1, rs.getLong("invoice_id")),
                    INVOICE_ITEMS_ROW_MAPPER);

            return createInvoiceDtoOut(rs, invoiceItemList);
        };
    }

}
