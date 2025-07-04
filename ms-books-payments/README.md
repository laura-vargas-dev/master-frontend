# ms-books-payments

Microservicio para la gestión de pagos y compras de libros.

## Descripción

Este microservicio permite registrar, consultar, modificar y eliminar compras de libros realizadas por clientes. Forma parte de un sistema distribuido y se integra con otros microservicios, como el catálogo de libros, a través de Eureka y peticiones HTTP.

## Tecnologías

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- Spring Web / WebFlux
- H2 Database (en memoria)
- Eureka Client
- Maven
- Docker

## Endpoints principales

| Método | Endpoint                | Descripción                        |
|--------|-------------------------|------------------------------------|
| GET    | `/api/purchases`            | Listar todas las compras           |
| GET    | `/api/purchases/{id}`       | Obtener una compra por ID          |
| POST   | `/api/purchases`            | Crear una nueva compra             |
| PUT    | `/api/purchases/{id}`       | Modificar completamente una compra |
| PATCH  | `/api/purchases/{id}`       | Modificar parcialmente una compra  |
| DELETE | `/api/purchases/{id}`       | Eliminar una compra                |

## Ejemplo de request para crear una compra

```json
{
  "purchaseDate": "2025-06-12",
  "totalAmount": 39.99,
  "books": ["1", "2"],
  "customerName": "Laura Pérez",
  "customerEmail": "laura.perez@email.com",
  "status": "COMPLETED"
}
```

## Ejemplo de respuesta

```json
{
  "id": 1,
  "purchaseDate": "2025-06-12T00:00:00.000+00:00",
  "totalAmount": 39.99,
  "books": [1, 2],
  "customerName": "Laura Pérez",
  "customerEmail": "laura.perez@email.com",
  "status": "COMPLETED"
}
```

## Configuración

La configuración principal se encuentra en [`src/main/resources/application.yml`](src/main/resources/application.yml).  
Incluye la configuración de la base de datos H2, Eureka y el endpoint del catálogo de libros.

## Ejecución local

1. **Compilar el proyecto:**
   ```sh
   ./mvnw clean package
   ```
2. **Ejecutar la aplicación:**
   ```sh
   java -jar target/ms-books-payments-0.0.1-SNAPSHOT.jar
   ```
   O usando Docker:
   ```sh
   docker build -t ms-books-payments .
   docker run -p 8082:8082 ms-books-payments
   ```

3. **Acceso a la consola H2:**  
   [http://localhost:8082/h2-console](http://localhost:8082/h2-console)  
   JDBC URL: `jdbc:h2:mem:payments;Mode=MySQL`

## Pruebas

Las pruebas unitarias se encuentran en [`src/test/java/com/unir/ms_books_payments/MsBooksPaymentsApplicationTests.java`](src/test/java/com/unir/ms_books_payments/MsBooksPaymentsApplicationTests.java).

## Autores

- Grupo Tokio - UNIR

---

> Para más detalles sobre los endpoints y ejemplos de uso, consulta la documentación incluida en este repositorio.