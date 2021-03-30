--liquibase formatted sql
--changeset mlatalska:1

CREATE TABLE Invoice (
    invoice_id SERIAL PRIMARY KEY,
    invoice_number VARCHAR(20) NOT NULL,
    seller_id BIGINT NOT NULL,
    buyer_id BIGINT NOT NULL,
    bank_account_number VARCHAR(28),
    comments VARCHAR(40),
    date_of_issue TIMESTAMP NOT NULL,
    date_of_sale TIMESTAMP NOT NULL,
    date_of_payment TIMESTAMP NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    date_of_created TIMESTAMP NOT NULL,
    date_of_update TIMESTAMP
);

CREATE TABLE Company (
    company_id SERIAL PRIMARY KEY,
    company_name TEXT NOT NULL,
    address TEXT,
    nip VARCHAR(10) NOT NULL
);

CREATE TABLE Invoice_items (
    invoice_items_id SERIAL PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    name VARCHAR(30) NOT NULL,
    unit VARCHAR(10) NOT NULL,
    quantity DECIMAL NOT NULL,
    vat_rate VARCHAR(20) NOT NULL,
    net_price MONEY NOT NULL,
    net_value MONEY NOT NULL,
    gross_value MONEY NOT NULL
);






