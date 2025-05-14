# master-frontend

Front‑end de la aplicación web del proyecto transversal del máster, construido con **React**, **Vite** y metodologías modernas de desarrollo (BEM para CSS, Hooks de React, React Router v6).

---

## 📋 Tabla de contenidos

1. [Descripción](#descripción)
2. [Tecnologías](#tecnologías)
3. [Requisitos](#requisitos)
4. [Instalación](#instalación)
5. [Estructura del proyecto](#estructura-del-proyecto)
6. [Scripts disponibles](#scripts-disponibles)
7. [Convenciones y estilo](#convenciones-y-estilo)
8. [Funcionalidades implementadas](#funcionalidades-implementadas)
9. [Organización BEM](#organización-bem)

---

## Descripción

Este repositorio contiene el front‑end de la aplicación web del proyecto transversal del máster. Se basa en:

* **Vite** como bundler y herramienta de desarrollo.
* **React** (funcional components) con Hooks (`useState`, `useEffect` y hooks personalizados).
* **React Router v6** para navegación entre rutas.
* **SCSS** para estilos, siguiendo la metodología **BEM**.
* Mocks de datos en `src/assets/books.json`.

---

## Tecnologías

* 📦 **Vite**
* ⚛️ **React 18+**
* 📍 **React Router v6**
* 💅 **Sass (SCSS)**
* 🎨 **Methodología BEM**
* 🔗 **classnames** (para gestión dinámica de clases)

---

## Requisitos

* Node.js >= 14.x
* npm >= 6.x

---

## Instalación

1. Clona el repositorio:

   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd master-frontend
   ```

2. Instala dependencias:

   ```bash
   npm install
   ```

3. Levanta el servidor de desarrollo:

   ```bash
   npm run dev
   ```

4. Abre tu navegador en `http://localhost:5173`.

---

## Estructura del proyecto

```tree
src/
├── assets/               # Mocks y recursos (JSON, imágenes)
├── components/           # Componentes React reutilizables
├── hooks/                # Hooks personalizados (useCart, useRedirect, ...)
├── pages/                # Vistas ligadas a rutas
├── styles/               # SCSS globales y variables
├── App.jsx               # Configuración de rutas
├── main.jsx             # Punto de entrada
└── vite.config.js        # Configuración de Vite
```

---

## Scripts disponibles

* `npm run dev` — Levanta servidor de desarrollo
* `npm run build` — Genera bundle de producción en `dist/`
* `npm run preview` — Vista previa local del build de producción

---

## Convenciones y estilo

* Código en **inglés** (comentarios, nombres de variables y componentes).
* Componentes funcionales con extensión `.jsx`.
* Estilos en archivos `.scss`, siguiendo BEM.
* Uso de `classnames` para gestión condicional de clases.

---

## Funcionalidades implementadas

* **Landing** con redirección automática a `/home` tras 5s (`useRedirect`).
* **Home**: listado de libros, filtro por título (`useEffect`, `useState`).
* **BookPage**: detalles de un libro y botón “Añadir al carrito”.
* **Cart**: dropdown/fijo, listado de items, eliminar o proceder a checkout.
* **CheckoutPage**: resumen de compra, confirmación con alerta, vaciado de carrito y redirección.

---

## Organización BEM

Todas las clases siguen el patrón **bloque\_\_elemento--modificador**, por ejemplo:

```scss
.book-card {
  &__image { ... }
  &__info { ... }
  &__title { ... }
  &__price { ... }
  &--highlighted { ... }
}
```

Para componentes dinámicos, se utiliza `classnames`:

```jsx
import cx from 'classnames';

<div className={cx('book-card', { 'book-card--highlighted': highlight })}>
  ...
</div>
```

---

¡Listo para empezar a desarrollar! Sigue las pautas y añade nuevas secciones a este README conforme crezca el proyecto.
