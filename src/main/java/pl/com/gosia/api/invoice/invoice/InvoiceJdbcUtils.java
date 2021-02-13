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
            InvoiceItems.builder()
                    .name(rs.getString("name"))
                    .unitMeasure(UnitMeasure.valueOf(rs.getString("unit")))
                    .quantity(rs.getDouble("quantity"))
                    .vatRate(VatRate.valueOf(rs.getString("vat_rate")))
                    .netPrice(rs.getBigDecimal("net_price"))
                    .netValue(rs.getBigDecimal("net_value"))
                    .grossValue(rs.getBigDecimal("gross_value"))
                    .build();

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
        return InvoiceDTOOut
                .builder()
                .invoiceId(rs.getLong("invoice_id"))
                .invoiceNumber(rs.getString("invoice_number"))
                .seller(
                        CompanyView.builder()
                                .companyId(rs.getLong("seller.company_id"))
                                .companyName(rs.getString("seller.company_name"))
                                .adress(rs.getString("seller.adress"))
                                .nip(rs.getString("seller.nip"))
                                .build()
                )
                .buyer(
                        CompanyView.builder()
                                .companyId(rs.getLong("buyer.company_id"))
                                .companyName(rs.getString("buyer.company_name"))
                                .adress(rs.getString("buyer.adress"))
                                .nip(rs.getString("buyer.nip"))
                                .build()
                )
                .bankAccountNumber(rs.getString("bank_account_number"))
                .comments(rs.getString("comments"))
                .dateOfIssue(rs.getTimestamp("date_of_issue").toLocalDateTime())
                .dateOfSale(rs.getTimestamp("date_of_sale").toLocalDateTime())
                .dateOfPayment(rs.getTimestamp("date_of_payment").toLocalDateTime())
                .paymentMethod(PaymentMethod.valueOf(rs.getString("payment_method")))
                .invoiceItemsList(invoiceItemsList)
                .dateOfCreated(rs.getTimestamp("date_of_created").toLocalDateTime())
                .build();
    }
}
