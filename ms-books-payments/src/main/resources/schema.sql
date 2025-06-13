CREATE TABLE purchases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    purchase_date DATETIME NOT NULL,
    total_amount DOUBLE NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL
);

CREATE TABLE purchase_books (
    purchase_id BIGINT NOT NULL,
    book VARCHAR(255) NOT NULL,
    FOREIGN KEY (purchase_id) REFERENCES purchases(id)
);