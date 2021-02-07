--liquibase formatted sql
--changeset mlatalska:2

ALTER TABLE Invoice
ADD CONSTRAINT fk_seller
FOREIGN KEY (seller_id)
REFERENCES Seller (seller_id);

--changeset mlatalska:3

ALTER TABLE Invoice
ADD CONSTRAINT fk_buyer
FOREIGN KEY (buyer_id)
REFERENCES Buyer (buyer_id);

ALTER TABLE Seller
ADD CONSTRAINT fk_seller_adress
FOREIGN KEY (address_id)
REFERENCES Adress (address_id);

ALTER TABLE Buyer
ADD CONSTRAINT fk_buyer_adress
FOREIGN KEY (address_id)
REFERENCES Adress (address_id);

ALTER TABLE Invoice_items
ADD CONSTRAINT fk_invoice_items
FOREIGN KEY (invoice_id)
REFERENCES Invoice (invoice_id);