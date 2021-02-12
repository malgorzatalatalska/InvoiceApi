package pl.com.gosia.api.invoice.invoice;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import pl.com.gosia.api.invoice.company.dto.CompanyView;
import pl.com.gosia.api.invoice.invoice.dto.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceJdbcUtils {

    public static final RowMapper<InvoiceItems> INVOICE_ITEMS_ROW_MAPPER = (rs, rowNum) ->
            new InvoiceItems(
                    rs.getString("name"),
                    UnitMeasure.valueOf(rs.getString("unit")),
                    rs.getDouble("quantity"),
                    VatRate.valueOf(rs.getString("vat_rate")),
                    rs.getBigDecimal("net_price"),
                    rs.getBigDecimal("net_value"),
                    rs.getBigDecimal("gross_value")
            );

    public static BatchPreparedStatementSetter invoiceItemsBatchStatementSetter(long invoice_id, List<InvoiceItems> invoiceItemsList) {
        return new BatchPreparedStatementSetter() {
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
        };
    }

    public static PreparedStatementCreator invoiceStatementSetter(InvoiceDTOCompany invoiceDTOCompany, String sqlQuery) {
        return connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sqlQuery, new String[]{"invoice_id"});
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
        };
    }

    public static InvoiceDTOOut createInvoiceDtoOut(java.sql.ResultSet rs, List<InvoiceItems> invoiceItemsList) throws SQLException {
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
    }
}
