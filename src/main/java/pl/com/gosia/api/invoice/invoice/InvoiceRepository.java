package pl.com.gosia.api.invoice.invoice;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gosia.api.invoice.company.dto.CompanyView;
import pl.com.gosia.api.invoice.invoice.dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Repository
@RequiredArgsConstructor
public class InvoiceRepository {

    private final JdbcTemplate jdbcTemplate;

    RowMapper<InvoiceItems> invoiceItemsRowMapper = (rs, rowNum) ->
            new InvoiceItems(
                    rs.getString("name"),
                    UnitMeasure.valueOf(rs.getString("unit")),
                    rs.getDouble("quantity"),
                    VatRate.valueOf(rs.getString("vat_rate")),
                    rs.getBigDecimal("net_price"),
                    rs.getBigDecimal("net_value"),
                    rs.getBigDecimal("gross_value")
            );

    RowMapper<InvoiceDTOOut> invoiceRowMapper() {
        return (rs, rowNum) -> {
            String sqlQuery = "SELECT * FROM Invoice_items WHERE invoice_id = ?";
            List<InvoiceItems> invoiceItemsList = jdbcTemplate.query(sqlQuery,
                    preparedStatement -> preparedStatement.setLong(1, rs.getLong("invoice_id")),
                    invoiceItemsRowMapper);

            return new InvoiceDTOOut(
                    rs.getLong("invoice_id"),
                    rs.getString("invoice_number"),
                    new CompanyView(
                            rs.getLong("seller.company_id"),
                            rs.getString("seller.company_name"),
                            rs.getString("seller.adress"),
                            rs.getString("seller.nip")),
                    new CompanyView(
                            rs.getLong("buyer.company_id"),
                            rs.getString("buyer.company_name"),
                            rs.getString("buyer.adress"),
                            rs.getString("buyer.nip")),
                    rs.getString("bank_account_number"),
                    rs.getString("comments"),
                    rs.getTimestamp("date_of_issue").toLocalDateTime(),
                    rs.getTimestamp("date_of_sale").toLocalDateTime(),
                    rs.getTimestamp("date_of_payment").toLocalDateTime(),
                    PaymentMethod.valueOf(rs.getString("payment_method")),
                    invoiceItemsList,
                    rs.getTimestamp("date_of_created").toLocalDateTime()
            );
        };
    }

    List<InvoiceDTOOut> findAllInvoice() {
        String sqlQuery = "select invoice_id, invoice_number, seller_id, buyer_id , bank_account_number, \"comments\", " +
                "date_of_issue, date_of_sale, date_of_payment , payment_method , date_of_created , seller.company_id as \"seller.company_id\", " +
                "seller.company_name as \"seller.company_name\", seller.adress as \"seller.adress\", seller.nip as \"seller.nip\", buyer.company_id as \"buyer.company_id\", " +
                "buyer.company_name as \"buyer.company_name\", buyer.adress as \"buyer.adress\", buyer.nip as \"buyer.nip\" from invoice i " +
                "left join company seller on i.seller_id = seller.company_id left join company buyer on i.buyer_id = buyer.company_id";
        return jdbcTemplate.query(
                sqlQuery,
                invoiceRowMapper()
        );
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
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sqlQuery2, new String[]{"invoice_id"});
            preparedStatement.setString(1, invoiceDTOCompany.getInvoiceNumber());
            preparedStatement.setLong(2, invoiceDTOCompany.getSeller().getCompanyId());
            preparedStatement.setLong(3, invoiceDTOCompany.getBuyer().getCompanyId());
            preparedStatement.setString(4, invoiceDTOCompany.getBankAccountNumber());
            preparedStatement.setString(5, invoiceDTOCompany.getComments());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(invoiceDTOCompany.getDateOfIssue()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(invoiceDTOCompany.getDateOfSale()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(invoiceDTOCompany.getDateOfPayment()));
            preparedStatement.setString(9, invoiceDTOCompany.getPaymentMethod().name());
            preparedStatement.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            return preparedStatement;
        }, generatedKeyHolder);

        long invoice_id = Objects.requireNonNull(generatedKeyHolder.getKey()).longValue();

        List<InvoiceItems> invoiceItemsList = invoiceDTOCompany.getInvoiceItems();

        String sqlQuery = "INSERT INTO Invoice_items (invoice_id, name, unit, quantity,vat_rate,net_price," +
                "net_value,gross_value) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                InvoiceItems invoiceItems = invoiceItemsList.get(i);
                ps.setLong(1, invoice_id);
                ps.setString(2, invoiceItems.getName());
                ps.setString(3, invoiceItems.getUnitMeasure().name());
                ps.setDouble(4, invoiceItems.getQuantity());
                ps.setString(5, invoiceItems.getVatRate().name());
                ps.setBigDecimal(6, invoiceItems.getNetPrice());
                ps.setBigDecimal(7, invoiceItems.getNetValue());
                ps.setBigDecimal(8, invoiceItems.getGrossValue());
            }

            @Override
            public int getBatchSize() {
                return invoiceItemsList.size();
            }
        });

        return findAllInvoice().stream()
                .filter(invoiceDTOOut -> invoiceDTOOut.getInvoiceId().equals(invoice_id))
                .findFirst().orElseThrow();


    }


}
