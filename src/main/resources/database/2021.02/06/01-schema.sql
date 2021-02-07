--liquibase formatted sql
--changeset mlatalska:1

CREATE TABLE Invoice (
    invoice_id SERIAL PRIMARY KEY,
    invoice_number VARCHAR(250) NOT NULL,
    seller_id BIGINT NOT NULL,
    buyer_id BIGINT NOT NULL,
    bank_account_number VARCHAR(250),
    comments VARCHAR(250),
    date_of_issue TIMESTAMP NOT NULL,
    date_of_sale TIMESTAMP NOT NULL,
    date_of_payment TIMESTAMP NOT NULL,
    payment_method VARCHAR(250) NOT NULL,
    date_of_created TIMESTAMP NOT NULL,
    date_of_update TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES Seller(seller_id),
    FOREIGN KEY (buyer_id) REFERENCES Buyer(buyer_id)
);

CREATE TABLE Seller (
    seller_id SERIAL PRIMARY KEY,
    company_name VARCHAR(250) NOT NULL,
    address_id BIGINT,
    nip INTEGER NOT NULL,
    FOREIGN KEY (address_id) REFERENCES Adress(address_id)

);

CREATE TABLE Buyer (
    buyer_id SERIAL PRIMARY KEY,
    company_name VARCHAR(250) NOT NULL,
    address_id BIGINT,
    nip INTEGER NOT NULL,
    FOREIGN KEY (address_id) REFERENCES Adress(address_id)
);

CREATE TABLE Adress (
    address_id SERIAL PRIMARY KEY,
    street VARCHAR(250),
    city VARCHAR(250),
    zip_code VARCHAR(250)
);

CREATE TABLE Invoice_items (
    invoice_items_id SERIAL PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    name VARCHAR(250) NOT NULL,
    unit VARCHAR(250) NOT NULL,
    quantity DECIMAL NOT NULL,
    vat_rate INTEGER NOT NULL,
    net_price MONEY NOT NULL,
    net_value MONEY NOT NULL,
    gross_value MONEY NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES Invoice(invoice_id)
);






