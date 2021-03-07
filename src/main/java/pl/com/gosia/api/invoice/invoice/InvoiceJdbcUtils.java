package pl.com.gosia.api.invoice.invoice;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import pl.com.gosia.api.invoice.company.dto.CompanyView;
import pl.com.gosia.api.invoice.invoice.dto.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceJdbcUtils {

    public static final RowMapper<InvoiceItem> INVOICE_ITEMS_ROW_MAPPER = (rs, rowNum) ->
            InvoiceItem.builder()
                    .name(rs.getString("name"))
                    .unitMeasure(UnitMeasure.valueOf(rs.getString("unit")))
                    .quantity(rs.getDouble("quantity"))
                    .vatRate(VatRate.valueOf(rs.getString("vat_rate")))
                    .netPrice(rs.getBigDecimal("net_price"))
                    .netValue(rs.getBigDecimal("net_value"))
                    .grossValue(rs.getBigDecimal("gross_value"))
                    .build();

    public static BatchPreparedStatementSetter invoiceItemsBatchStatementSetter(long invoice_id, List<InvoiceItem> invoiceItemList) {
        return new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                InvoiceItem invoiceItem = invoiceItemList.get(i);
                preparedStatement.setLong(1, invoice_id);
                preparedStatement.setString(2, invoiceItem.getName());
                preparedStatement.setString(3, invoiceItem.getUnitMeasure().name());
                preparedStatement.setDouble(4, invoiceItem.getQuantity());
                preparedStatement.setString(5, invoiceItem.getVatRate().name());
                preparedStatement.setBigDecimal(6, invoiceItem.getNetPrice());
                preparedStatement.setBigDecimal(7, invoiceItem.getNetValue());
                preparedStatement.setBigDecimal(8, invoiceItem.getGrossValue());
            }

            @Override
            public int getBatchSize() {
                return invoiceItemList.size();
            }
        };
    }

    public static PreparedStatementCreator invoiceStatementCreator(InvoiceEntity invoiceEntity, String sqlQuery) {
        return connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sqlQuery, new String[]{"invoice_id"});
            preparedStatement.setString(1, invoiceEntity.getInvoiceNumber());
            preparedStatement.setLong(2, invoiceEntity.getSeller().getCompanyId());
            preparedStatement.setLong(3, invoiceEntity.getBuyer().getCompanyId());
            preparedStatement.setString(4, invoiceEntity.getBankAccountNumber());
            preparedStatement.setString(5, invoiceEntity.getComments());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(invoiceEntity.getDateOfIssue()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(invoiceEntity.getDateOfSale()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(invoiceEntity.getDateOfPayment()));
            preparedStatement.setString(9, invoiceEntity.getPaymentMethod().name());
            preparedStatement.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            return preparedStatement;
        };
    }

    public static PreparedStatementSetter invoiceStatementSetter(InvoiceEntity invoiceEntity, Long invoiceId) {
        return preparedStatement -> {
            preparedStatement.setString(1, invoiceEntity.getInvoiceNumber());
            preparedStatement.setLong(2, invoiceEntity.getSeller().getCompanyId());
            preparedStatement.setLong(3, invoiceEntity.getBuyer().getCompanyId());
            preparedStatement.setString(4, invoiceEntity.getBankAccountNumber());
            preparedStatement.setString(5, invoiceEntity.getComments());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(invoiceEntity.getDateOfIssue()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(invoiceEntity.getDateOfSale()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(invoiceEntity.getDateOfPayment()));
            preparedStatement.setString(9, invoiceEntity.getPaymentMethod().name());
            preparedStatement.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setLong(11, invoiceId);
        };
    }

    public static InvoiceView createInvoiceDtoOut(java.sql.ResultSet rs, List<InvoiceItem> invoiceItemList) throws SQLException {
        return InvoiceView
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
                .invoiceItemList(invoiceItemList)
                .dateOfCreated(rs.getTimestamp("date_of_created").toLocalDateTime())
                .build();
    }
}
