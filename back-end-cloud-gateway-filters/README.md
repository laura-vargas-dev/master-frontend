# back-end-cloud-gateway-filters

Gateway (proxy inverso) para la arquitectura de microservicios del proyecto, implementado con **Spring Cloud Gateway** y filtros personalizados para traducción y manipulación de peticiones.

---

## 📋 Tabla de contenidos

1. [Descripción](#descripción)
2. [Tecnologías](#tecnologías)
3. [Requisitos](#requisitos)
4. [Instalación](#instalación)
5. [Estructura del proyecto](#estructura-del-proyecto)
6. [Scripts disponibles](#scripts-disponibles)
7. [Componentes principales](#componentes-principales)
8. [Configuración](#configuración)
9. [Convenciones y estilo](#convenciones-y-estilo)

---

## Descripción

Este servicio actúa como puerta de entrada a los microservicios, gestionando el enrutamiento, la autenticación y la transformación de peticiones/respuestas. Incluye filtros globales personalizados para adaptar los requests antes de ser enviados a los microservicios de negocio.

---

## Tecnologías

- ☕ **Java 21**
- 🌩️ **Spring Boot 3.4**
- 🌐 **Spring Cloud Gateway**
- 🔍 **Spring Cloud Netflix Eureka Client**
- 🧪 **JUnit 5**
- 🐳 **Docker** (opcional)
- 🛠️ **Maven**

---

## Requisitos

- Java 21 o superior
- Maven 3.8+
- (Opcional) Docker

---

## Instalación

1. Clona el repositorio:

   ```sh
   git clone <URL_DEL_REPOSITORIO>
   cd back-end-cloud-gateway-filters
   ```

2. Compila el proyecto:

   ```sh
   ./mvnw clean install
   ```

3. Ejecuta el gateway:

   ```sh
   ./mvnw spring-boot:run
   ```

   Por defecto, el gateway estará disponible en `http://localhost:8762`.

---

## Estructura del proyecto

```tree
src/
├── main/
│   ├── java/com/unir/gateway/
│   │   ├── config/           # Configuración de beans (ObjectMapper, etc)
│   │   ├── decorator/        # Decoradores de peticiones
│   │   ├── filter/           # Filtros globales (RequestTranslationFilter)
│   │   ├── model/            # Modelos de datos (GatewayRequest)
│   │   └── utils/            # Utilidades (RequestBodyExtractor)
│   └── resources/
│       └── application.yml   # Configuración de Spring Boot y Gateway
├── test/                     # Tests unitarios
├── pom.xml                   # Dependencias y configuración de Maven
└── Dockerfile                # (Opcional) Contenedor Docker
```

---

## Scripts disponibles

* `./mvnw spring-boot:run` — Levanta el gateway en modo desarrollo
* `./mvnw test` — Ejecuta los tests
* `./mvnw package` — Genera el JAR ejecutable

---

## Componentes principales

- [`RequestTranslationFilter`](src/main/java/com/unir/gateway/filter/RequestTranslationFilter.java): Filtro global que intercepta y transforma las peticiones entrantes.
- [`RequestBodyExtractor`](src/main/java/com/unir/gateway/utils/RequestBodyExtractor.java): Extrae y transforma el body de la petición.
- [`RequestDecoratorFactory`](src/main/java/com/unir/gateway/decorator/RequestDecoratorFactory.java): Crea decoradores para modificar las peticiones.
- [`UniversalRequestDecorator`](src/main/java/com/unir/gateway/decorator/UniversalRequestDecorator.java): Decorador genérico para peticiones HTTP.
- [`GatewayRequest`](src/main/java/com/unir/gateway/model/GatewayRequest.java): Modelo de datos para representar la petición procesada por el gateway.

---

## Configuración

La configuración principal se encuentra en [`src/main/resources/application.yml`](src/main/resources/application.yml).  
Incluye:

- Puerto del gateway (`8762` por defecto)
- Configuración de Eureka para descubrimiento de servicios
- Filtros y CORS globales
- Exposición de endpoints de Actuator para monitorización

---

## Ejemplos de uso del Gateway

El gateway expone endpoints para interactuar con los microservicios de catálogo y pagos. Todas las operaciones se realizan mediante peticiones `POST` al gateway, especificando el método objetivo y los parámetros en el cuerpo de la petición.

### Formato general de la petición

```json
{
  "targetMethod": "GET" | "POST" | "PUT" | "PATCH" | "DELETE",
  "queryParams": { ... },   // Opcional
  "body": { ... }           // Opcional, según método
}
```

---

### Ejemplos para ms-books-catalogue

#### Obtener todos los libros con filtros

**POST** `/ms-books-catalogue/api/books`

```json
{
  "targetMethod": "GET",
  "queryParams": {
    "visible": [true],
    "title": ["r"]
  }
}
```

#### Añadir un libro

**POST** `/ms-books-catalogue/api/books`

```json
{
  "targetMethod": "POST",
  "queryParams": {},
  "body": {
    "title": "Kafka en la orilla",
    "author": "Haruki Murakami",
    "publicationDate": "2002-09-12",
    "category": "Ficción contemporánea",
    "isbn": "9788499084917",
    "rating": 4.6,
    "visible": true,
    "stock": 9,
    "price": 19.95
  }
}
```

#### Modificar un libro completamente

**POST** `/ms-books-catalogue/api/books/1`

```json
{
  "targetMethod": "PUT",
  "queryParams": {},
  "body": {
    "id": 1,
    "title": "El guardián invisible",
    "author": "Dolores Redondo",
    "publicationDate": "2013-01-15",
    "category": "Misterio",
    "isbn": "9788408114178",
    "rating": 4.5,
    "visible": true,
    "stock": 6,
    "price": 15.99
  }
}
```

#### Modificar parcialmente un libro

**POST** `/ms-books-catalogue/api/books/1`

```json
{
  "targetMethod": "PATCH",
  "queryParams": {},
  "body": {
    "stock": 16,
    "price": 15.99
  }
}
```

#### Eliminar un libro

**POST** `/ms-books-catalogue/api/books/11`

```json
{
  "targetMethod": "DELETE",
  "queryParams": {}
}
```

---

### Ejemplos para ms-books-payments

#### Obtener todas las compras

**POST** `/ms-books-payments/api/purchases`

```json
{
  "targetMethod": "GET"
}
```

#### Añadir una compra

**POST** `/ms-books-payments/api/purchases`

```json
{
  "targetMethod": "POST",
  "queryParams": {},
  "body": {
    "purchaseDate": "2025-06-12",
    "totalAmount": 5,
    "books": ["1"],
    "customerName": "Laura Vargas",
    "customerEmail": "laura@email.com",
    "status": "PENDING"
  }
}
```

#### Modificar una compra completamente

**POST** `/ms-books-payments/api/purchases/1`

```json
{
  "targetMethod": "PUT",
  "queryParams": {},
  "body": {
    "id": 4,
    "purchaseDate": "2025-06-12T00:00:00.000+00:00",
    "totalAmount": 5.0,
    "books": ["1", "3", "4"],
    "customerName": "Laura Vargas",
    "customerEmail": "laura@email.com",
    "status": "PENDING"
  }
}
```

#### Modificar parcialmente una compra

**POST** `/ms-books-payments/api/purchases/1`

```json
{
  "targetMethod": "PATCH",
  "queryParams": {},
  "body": {
    "totalAmount": 3.0,
    "status": "COMPLETED"
  }
}
```

#### Eliminar una compra

**POST** `/ms-books-payments/api/purchases/6`

```json
{
  "targetMethod": "DELETE",
  "queryParams": {}
}
```

---

> Recuerda: Cambia la URL base (`{{urlg}}`) por la dirección real de tu gateway, por ejemplo: `http://localhost:8762`.

---

## Convenciones y estilo

- Código en **inglés** (nombres de clases, variables y comentarios).
- Uso de anotaciones de **Lombok** para reducir boilerplate.
- Filtros y decoradores desacoplados para facilitar la extensión.
- Tests unitarios en [`src/test/java/com/unir/gateway/`](src/test/java/com/unir/gateway/).

## Autores

- Grupo Tokio - UNIR

---

> Para más detalles sobre los filtros y la lógica de transformación, consulta la documentación en el propio código fuente.