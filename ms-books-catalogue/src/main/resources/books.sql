-- Tabla principal de Libros
CREATE TABLE books (
                       id             BIGSERIAL    PRIMARY KEY,
                       title          VARCHAR(255) NOT NULL,
                       author         VARCHAR(255) NOT NULL,
                       publication_date DATE       NOT NULL,
                       category       VARCHAR(100) NOT NULL,
                       isbn           VARCHAR(20)  NOT NULL UNIQUE,
                       rating         SMALLINT     NOT NULL CHECK (rating BETWEEN 1 AND 5),
                       visible        BOOLEAN      NOT NULL DEFAULT TRUE,
                       stock          INTEGER      NOT NULL CHECK (stock >= 0),
                       price          NUMERIC(10,2)NOT NULL CHECK (price >= 0.0)
);

-- Índices recomendados para búsquedas frecuentes
CREATE INDEX idx_books_title ON books (LOWER(title));
CREATE INDEX idx_books_author ON books (LOWER(author));
CREATE INDEX idx_books_category ON books (LOWER(category));
CREATE INDEX idx_books_publication_date ON books (publication_date);
CREATE INDEX idx_books_rating ON books (rating);
CREATE INDEX idx_books_visible ON books (visible);
