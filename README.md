# CConect API - Backend

Backend de una **API REST para una red social**, desarrollada con **Java y Spring Boot**, utilizando **MongoDB** como base de datos NoSQL.  
Permite la gestión de usuarios, publicaciones, solicitudes de amistad y autenticación segura mediante JWT.

## Tecnologias Usadas
-  Java 21
-  Spring Boot
-  Spring Data MongoDB
-  Spring Security + JWT
-  Lombok
-  Maven
-  Docker
  
### Autenticación
| Método | Endpoint | Descripción |
|------|---------|------------|
| POST | `/auth/register` | Registrar usuario |
| POST | `/auth/login` | Iniciar sesión |

### Publicaciones
| Método | Endpoint | Descripción |
|------|---------|------------|
| POST | `/posts/-generar-publicacion` | Crear publicación |
| GET | `/posts/listar-publicaciones` | Listar publicaciones |
| GET | `/listar-publicaciones/{username}` | Obtener publicación de un usuario|
| GET | `/{idPublicacion}` | Buscar publicacion por id|
| GET | `/{idPublicacion}/comments` | Buscar publicacion por id|

### Comentarios
| Método | Endpoint | Descripción |
|------|---------|------------|
| POST | `/comment/agregar` | Crear comentario |


### Solicitudes de amistad
| Método | Endpoint                                      | Descripción                             |
| ------ | --------------------------------------------- | --------------------------------------- |
| POST   | `/solicitudes/mandar-solicitud`               | Enviar una solicitud de amistad         |
| POST   | `/solicitudes/aceptar-amistad`                | Aceptar una solicitud de amistad        |
| POST   | `/solicitudes/rechazar-amistad`               | Rechazar una solicitud de amistad       |
| POST   | `/solicitudes/cancelar-amistad`               | Cancelar una solicitud de amistad       |
| GET    | `/solicitudes/solicitudes-recibidas/{userId}` | Listar solicitudes de amistad recibidas |
| GET    | `/solicitudes/solicitudes-emitidas/{userId}`  | Listar solicitudes de amistad enviadas  |

## Variables de entorno

Este proyecto utiliza **servicios externos**, por lo que es necesario configurar las siguientes variables de entorno antes de ejecutar la aplicación.

---

### MongoDB

La aplicación usa **MongoDB** como base de datos principal.

#### Requisitos:
- Tener una cuenta en **MongoDB Atlas** o una instancia local de MongoDB
- Crear un cluster y una base de datos

#### Variable de entorno:
```env
MONGODBURI=mongodb+srv://usuario:password@cluster.mongodb.net/cconnet
```

### Cloudinary (almacenamiento de imágenes)

El proyecto utiliza Cloudinary para la subida y almacenamiento de imágenes (perfil de usuario, publicaciones, etc.).

### Requisitos:

- Crear una cuenta en Cloudinary
- Obtener las credenciales desde el panel de control
  
#### Variables de entorno:
```env
CLOUD_NAME=tu_cloud_name
API_KEY=tu_api_key
API_SECRET=tu_api_secret
```


## Ejecución del proyecto con Docker (sin archivo .env)

Este proyecto **no utiliza archivo `.env`**. Las variables de entorno se pasan directamente al contenedor al momento de ejecutarlo.

### Requisitos previos

- Tener **Docker** instalado
- Tener acceso a:
    - MongoDB Atlas (o MongoDB accesible públicamente)
    - Credenciales de Cloudinary

---

### Construir la imagen Docker

Desde la raíz del proyecto (donde está el `Dockerfile`):

```bash
docker build -t cconect-backend .
```

---

### Ejecutar el contenedor

```bash
docker run -p 8080:8080 \
  -e MONGODBURI="mongodb+srv://usuario:password@cluster.mongodb.net/cconnet" \
  -e CLOUD_NAME="tu_cloud_name" \
  -e API_KEY="tu_api_key" \
  -e API_SECRET="tu_api_secret" \
  cconect-backend
```

📌 **Notas importantes:**
- El contenedor expone el puerto **8080**

---

### Verificar ejecución

La API estará disponible en:
```
http://localhost:8080/swagger-ui/index.html
```

