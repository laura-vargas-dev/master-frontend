# ms-books-catalogue

Microservicio encargado de la gestión y consulta del catálogo de libros para el proyecto transversal del máster.

---

## 📋 Tabla de contenidos

1. [Descripción](#descripción)
2. [Tecnologías](#tecnologías)
3. [Requisitos](#requisitos)
4. [Instalación](#instalación)
5. [Estructura del proyecto](#estructura-del-proyecto)
6. [Scripts disponibles](#scripts-disponibles)
7. [Endpoints principales](#endpoints-principales)
8. [Convenciones y estilo](#convenciones-y-estilo)

---

## Descripción

Este microservicio expone una API REST para consultar, crear, actualizar y eliminar libros del catálogo. Forma parte de la arquitectura de microservicios del proyecto y se comunica con otros servicios a través de HTTP y/o mensajería.

---

## Tecnologías

* ☕ **Java 17+**
* 🛠️ **Spring Boot**
* 🗄️ **Spring Data JPA**
* 🐘 **Base de datos relacional** (ej: PostgreSQL/H2)
* 📄 **OpenAPI/Swagger** (documentación de la API)
* 🧪 **JUnit** (tests)

---

## Requisitos

* Java 17 o superior
* Maven 3.8+
* (Opcional) Docker

---

## Instalación

1. Clona el repositorio:

   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd ms-books-catalogue
   ```

2. Compila el proyecto:

   ```bash
   ./mvnw clean install
   ```

3. Ejecuta el microservicio:

   ```bash
   ./mvnw spring-boot:run
   ```

   Por defecto, el servicio estará disponible en `http://localhost:8080`.

---

## Estructura del proyecto

```tree
src/
├── main/
│   ├── java/
│   │   └── ...           # Código fuente Java (controladores, servicios, repositorios, modelos)
│   └── resources/
│       ├── application.yml # Configuración de Spring Boot
│       └── ...           # Otros recursos
├── test/                 # Tests unitarios y de integración
├── pom.xml               # Dependencias y configuración de Maven
└── Dockerfile            # (Opcional) Contenedor Docker
```

---

## Scripts disponibles

* `./mvnw spring-boot:run` — Levanta el microservicio en modo desarrollo
* `./mvnw test` — Ejecuta los tests
* `./mvnw package` — Genera el JAR ejecutable

---

## Endpoints principales

- `GET /api/books` — Listar todos los libros
- `GET /api/books/{id}` — Obtener detalles de un libro
- `POST /api/books` — Crear un nuevo libro
- `PUT /api/books/{id}` — Actualizar un libro existente
- `DELETE /api/books/{id}` — Eliminar un libro

La documentación interactiva de la API está disponible en `/swagger-ui.html` (si está habilitada).

---

## Convenciones y estilo

- Código siguiendo las guías de estilo de Java y Spring Boot.
- Uso de DTOs para la comunicación con el cliente.
- Manejo de errores centralizado con controladores de excepciones.

---