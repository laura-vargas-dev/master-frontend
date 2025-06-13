CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_date DATE NOT NULL,
    category VARCHAR(100) NOT NULL,
    isbn VARCHAR(17) NOT NULL UNIQUE,
    rating DECIMAL(3,1) NOT NULL,
    visible BOOLEAN DEFAULT true,
    stock INTEGER NOT NULL,
    price DECIMAL(10,3) NOT NULL
);
