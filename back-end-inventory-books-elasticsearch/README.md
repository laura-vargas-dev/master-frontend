# back-end-inventory-books-elasticsearch

Microservicio para la gestión y consulta de libros en Elasticsearch.

## Endpoints principales

| Método | Endpoint                   | Descripción                             |
|--------|----------------------------|-----------------------------------------|
| GET    | `/elastic/books`           | Listar libros con filtros opcionales    |
| GET    | `/elastic/books/{bookId}`  | Obtener detalles de un libro por ID     |
| POST   | `/elastic/books`           | Crear un nuevo libro                    |
| DELETE | `/elastic/books/{bookId}`  | Eliminar un libro por ID                |

---

## Ejemplos de uso

### Obtener todos los libros

```http
GET /elastic/books
```
Parámetros opcionales: `title`, `author`, `category`, `visible`

### Obtener un libro por ID

```http
GET /elastic/books/{bookId}
```

### Crear un libro

```http
POST /elastic/books
Content-Type: application/json

{
  "title": "Ejemplo",
  "author": "Autor",
  "category": "Categoría",
  ...
}
```

### Eliminar un libro

```http
DELETE /elastic/books/{bookId}
```

---

## Estructura básica

- Controlador principal: [`BooksController`](back-end-inventory-books-elasticsearch/src/main/java/com/unir/back_end_inventory_books_elasticsearch/controller/BooksController.java)
- Servicio: [`BooksService`](back-end-inventory-books-elasticsearch/src/main/java/com/unir/back_end_inventory_books_elasticsearch/service/BooksService.java)
- Modelo de libro: [`Book`](back-end-inventory-books-elasticsearch/src/main/java/com/unir/back_end_inventory_books_elasticsearch/data/model/Book.java)

---

## Requisitos

- Java 17+
- Maven
- Elasticsearch

---

## Ejecución

```sh
./mvnw clean package
java -jar target/back-end-inventory-books-elasticsearch-0.0.1-SNAPSHOT.jar
```

---

> Para más detalles, consulta el código fuente y la documentación de los