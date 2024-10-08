# Sprint 2 - Módulo de Pagos A

Este repositorio contiene el código del **backend** del módulo de pagos A, desarrollado con **Spring Boot** y **GraphQL**, dockerizado para facilitar su despliegue y ejecución.

---

## 📋 Descripción del Proyecto

El objetivo principal del proyecto es gestionar transacciones y pagos en un sistema de reservas de vuelos. Durante el **Sprint 1**, se implementaron las siguientes funcionalidades clave:

- Creación y gestión de transacciones de pago.
- Actualización del estado de las transacciones.
- Consulta de detalles de los pagos.
- Simulación de pagos y cargos adicionales.

El backend utiliza **PostgreSQL** como base de datos y está diseñado para ser consumido por un frontend **React**. Además, se ha integrado la simulación de pagos utilizando las plataformas **Stripe** y **Mercado Pago** en modo sandbox.

---

## 🚀 Tecnologías Utilizadas

- **Spring Boot** - Framework backend
- **GraphQL** - Consulta y manipulación de datos
- **PostgreSQL** - Base de datos relacional
- **Maven** - Gestión de dependencias
- **Docker** - Contenerización para facilitar el despliegue

---

## 🛠️ Instrucciones para Correr el Proyecto

### 1. Clonar el Repositorio

Primero, clona este repositorio en tu máquina local:

```bash
git clone https://github.com/KevEstr/Sprint2-Aerolines
```


### 2. Configurar el Entorno

Antes de correr el proyecto, asegúrate de configurar las variables de entorno necesarias en el archivo application.properties en resources:

```bash
export STRIPE_SECRET_KEY=tu_clave_secreta
export STRIPE_WEBHOOK_SECRET=tu_clave_webhook
```

### 3. Construir y Ejecutar los Contenedores

Construye y ejecuta los contenedores del backend y la base de datos utilizando el siguiente comando:

```bash
docker-compose up --build
```
Esto asegurará que se instalen las librerias y dependencias necesarias para correr el proyecto.

### ⚠️ Advertencia

Si la base de datos no se creó correctamente o los **seeders** no se aplicaron, ejecuta el siguiente comando para ejecutar los scripts de inicialización manualmente:

```bash
docker-compose exec db psql -U postgres -d <nombre_database> -f /docker-entrypoint-initdb.d/data.sql
```

### 4. Configurar Stripe

Para recibir los eventos de webhook de **Stripe**, asegúrate de instalar el **Stripe CLI** y de configurar el reenvío de los webhooks al puerto 8081:

```bash
stripe listen --forward-to localhost:8081/stripe-webhook
```

### 5. Detener los Contenedores

Para detener los contenedores, simplemente ejecuta:

```bash
docker-compose down
```

---

## 📂 Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/udea/modulo_pagos/
│   │       ├── controller/        # Controladores REST
│   │       ├── entities/          # Entidades JPA
│   │       ├── graphql/           # Resolutores GraphQL
│   │       ├── repositories/      # Repositorios de datos
│   │       ├── service/           # Lógica de negocio
│   │       ├── utils/             # Utilidades
│   │       └── ModuloPagosApplication.java # Clase principal
│   ├── resources/
│   │   ├── graphql/
│   │   │   └── schema.graphqls     # Definición del esquema GraphQL
│   │   └── application.properties  # Configuración de la base de datos
├── Dockerfile                      # Archivo Docker para contenerizar la app
└── docker-compose.yml               # Configuración de Docker Compose
```

---

## 📝 Notas Adicionales

- **Playground de GraphQL**: Disponible en `http://localhost:8081/graphiql` haciendo uso de **Postman**
- **Base de Datos**: La configuración de la base de datos **PostgreSQL** está en el archivo `docker-compose.yml`. Si deseas modificar credenciales o puertos, puedes ajustar estos valores ahí.
- **Webhooks de Stripe**: Asegúrate de que las claves de Stripe estén configuradas correctamente en tus variables de entorno antes de ejecutar el contenedor.

---
