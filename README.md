# PersonApp - Hexagonal Architecture Spring Boot

Aplicación de gestión de personas implementada con Arquitectura Hexagonal (Puertos y Adaptadores) usando Spring Boot.

## 🏗️ Arquitectura

El proyecto sigue el patrón de Arquitectura Hexagonal con los siguientes módulos:

- **domain**: Entidades del dominio (Person, Profession, Phone, Study)
- **application**: Casos de uso y puertos (interfaces)
- **maria-output-adapter**: Adaptador de persistencia para MariaDB
- **mongo-output-adapter**: Adaptador de persistencia para MongoDB
- **cli-input-adapter**: Adaptador de entrada CLI (línea de comandos)
- **rest-input-adapter**: Adaptador de entrada REST API

## 🚀 Inicio Rápido

### Opción 1: Usando Docker Compose (Recomendado)

```bash
# Levantar todos los servicios
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down

# Detener y eliminar volúmenes (resetear datos)
docker-compose down -v
```

**Servicios disponibles:**
- **MongoDB**: `localhost:27017`
- **Mongo Express** (UI): http://localhost:8081
- **MariaDB**: `localhost:3307`
- **REST API**: http://localhost:3000
- **Swagger UI**: http://localhost:3000/swagger-ui.html
- **CLI Service**: `docker attach personapp-cli-service`

### Opción 2: Ejecución Local

#### 1. Iniciar bases de datos

```bash
docker-compose up -d mongo mariadb mongo-express
```

#### 2. Compilar el proyecto

```bash
mvn clean install -DskipTests
```

#### 3. Ejecutar aplicaciones

**REST API:**
```bash
java -jar rest-input-adapter/target/rest-input-adapter-0.0.1-SNAPSHOT.jar
```

**CLI:**
```bash
java -jar cli-input-adapter/target/cli-input-adapter-0.0.1-SNAPSHOT.jar
```

## 🗄️ Bases de Datos

### MongoDB
- **Host**: localhost:27017
- **Database**: persona_db
- **Usuario**: persona_db
- **Password**: persona_db
- **Auth DB**: admin

### MariaDB
- **Host**: localhost:3307
- **Database**: persona_db
- **Usuario**: persona_db
- **Password**: persona_db

### Mongo Express (UI)
Interfaz web para administrar MongoDB: http://localhost:8081

## 📡 API REST

### Endpoints Principales

**Personas:**
- `GET /api/v1/persona` - Listar todas
- `GET /api/v1/persona/{database}/{id}` - Obtener por ID
- `POST /api/v1/persona/{database}` - Crear
- `PUT /api/v1/persona/{database}/{id}` - Actualizar
- `DELETE /api/v1/persona/{database}/{id}` - Eliminar

**Profesiones:**
- `GET /api/v1/profesion` - Listar todas
- `POST /api/v1/profesion/{database}` - Crear
- `PUT /api/v1/profesion/{database}/{id}` - Actualizar
- `DELETE /api/v1/profesion/{database}/{id}` - Eliminar

**Teléfonos:**
- `GET /api/v1/telefono` - Listar todos
- `POST /api/v1/telefono/{database}` - Crear
- `PUT /api/v1/telefono/{database}/{number}` - Actualizar
- `DELETE /api/v1/telefono/{database}/{number}` - Eliminar

**Estudios:**
- `GET /api/v1/estudio` - Listar todos
- `POST /api/v1/estudio/{database}` - Crear
- `PUT /api/v1/estudio/{database}/{idProfesion}/{idPersona}` - Actualizar
- `DELETE /api/v1/estudio/{database}/{idProfesion}/{idPersona}` - Eliminar

**Nota:** `{database}` puede ser `maria` o `mongo`

### Documentación Swagger
http://localhost:3000/swagger-ui.html

## 🖥️ CLI (Interfaz de Línea de Comandos)

La aplicación CLI ofrece un menú interactivo para:
- Gestionar Personas
- Gestionar Profesiones
- Gestionar Teléfonos
- Gestionar Estudios
- Seleccionar base de datos (MariaDB o MongoDB)
- Operaciones CRUD completas

## 🛠️ Desarrollo

### Requisitos
- Java 21
- Maven 3.9+
- Docker y Docker Compose
- Lombok configurado en el IDE

### Estructura del Proyecto

```
personapp-hexa-spring-boot/
├── common/                    # Clases comunes
├── domain/                    # Entidades del dominio
├── application/               # Casos de uso y puertos
├── maria-output-adapter/      # Adaptador MariaDB
├── mongo-output-adapter/      # Adaptador MongoDB
├── cli-input-adapter/         # Adaptador CLI
├── rest-input-adapter/        # Adaptador REST
├── scripts/                   # Scripts de inicialización DB
├── docker-compose.yml         # Configuración Docker
├── Dockerfile                 # Imagen Docker de la app
└── pom.xml                    # POM padre
```

### Configurar Lombok en IDEs

**IntelliJ IDEA:**
1. Instalar plugin "Lombok"
2. Settings → Build → Compiler → Annotation Processors → Enable annotation processing

**Eclipse:**
1. Descargar lombok.jar
2. Ejecutar: `java -jar lombok.jar`
3. Seleccionar instalación de Eclipse

**VS Code:**
1. Instalar extensión "Lombok Annotations Support"

## 🐳 Docker

### Build de imágenes

```bash
# Build CLI
docker-compose build personapp-cli-service

# Build REST
docker-compose build personapp-rest-service
```

### Variables de Entorno

Las aplicaciones soportan las siguientes variables:
- `MARIADB_HOST` (default: localhost)
- `MARIADB_PORT` (default: 3307)
- `MONGODB_HOST` (default: localhost)
- `MONGODB_PORT` (default: 27017)
- `APP_TYPE` (cli o rest, para contenedor Docker)

## 📝 Notas

- El proyecto usa Spring Boot 2.7.11
- Lombok 1.18.30 para reducir código boilerplate
- Arquitectura Hexagonal para independencia de frameworks
- Soporte dual: MariaDB (SQL) y MongoDB (NoSQL)
- Los scripts de inicialización se ejecutan automáticamente en el primer arranque

## 📄 Licencia

Este proyecto está bajo la licencia especificada en el archivo LICENSE.
