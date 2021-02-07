--liquibase formatted sql
--changeset mlatalska:2

ALTER TABLE Invoice
ADD CONSTRAINT fk_seller
FOREIGN KEY (seller_id)
REFERENCES Company (company_id);

ALTER TABLE Invoice
ADD CONSTRAINT fk_buyer
FOREIGN KEY (buyer_id)
REFERENCES Company (company_id);

ALTER TABLE Company
ADD CONSTRAINT fk_company_adress
FOREIGN KEY (address_id)
REFERENCES Adress (address_id);

ALTER TABLE Invoice_items
ADD CONSTRAINT fk_invoice_items
FOREIGN KEY (invoice_id)
REFERENCES Invoice (invoice_id);