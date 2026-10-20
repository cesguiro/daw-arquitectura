# daw-arquitectura

Proyecto didáctico (DAW · SI 2ª evaluación, tema 08 — *Arquitectura de aplicación*): un
sistema de varios servidores que colaboran, descrito en un solo `compose.yaml` y arrancado
de una vez.

## La arquitectura

```
navegador
   │  http://localhost:8080   (único puerto publicado)
   ▼
 nginx ─┬─ /           → frontend estático (HTML, CSS, JS)
        └─ /api/**      → reverse proxy
                            ▼
                 app: Spring Boot (Tomcat embebido :8080, sin puerto propio)
                            ├─ JDBC   → db:3306    (MariaDB 12.3, red interna)
                            └─ api S3 → minio:9000 (red interna)
```

- **nginx** es el punto de entrada único: sirve los estáticos y reenvía `location /api/`
  a `http://app:8080` (resolución por el DNS interno de Compose).
- **app**: la API REST en Java (Spring Boot con Tomcat embebido); solo responde al proxy.
- **db**: persistencia relacional (tabla `mensajes`, sembrada por `db/init.sql`).
- **minio**: almacenamiento de objetos, ya en el sistema aunque la API no lo use todavía.

## Arranque

Requisitos: **Docker Desktop** y **Git Bash** (para el script, en Windows).

```bash
./dev.sh up        # todo el sistema (equals a docker compose up -d --build)
./dev.sh up db minio   # solo servicios concretos, p. ej. para desarrollar fuera de Docker
./dev.sh ps        # estado y puertos publicados
./dev.sh logs app  # logs de un servicio (o de todos)
./dev.sh down      # para y elimina contenedores (los volúmenes se conservan)
```

La primera vez tarda más: compila el jar dentro de Docker (etapa `build` del `Dockerfile`),
no necesitas Maven local.

## Pruebas rápidas

| Prueba | Comando / URL |
|---|---|
| Frontend estático | <http://localhost:8080> |
| API vía proxy | `curl http://localhost:8080/api/saludo?nombre=Ana` |
| API leyendo de MariaDB | `curl http://localhost:8080/api/mensajes` |
| Exposición mínima | `docker compose ps` → solo nginx tiene puertos publicados |

## Endpoints

- `GET /api/saludo?nombre=CSC` → JSON con el saludo, quién lo atiende y la hora.
- `GET /api/mensajes` → filas de la tabla `mensajes` de MariaDB.

Los controladores mapean las rutas **con** el prefijo `/api/`: nginx pasa la petición tal
cual (en `proxy_pass` no se indica URI), de modo que Tomcat ve `/api/saludo`.

## Estructura

```
compile.yaml      ← sistema completo: nginx, app, db, minio + volúmenes
dev.sh            ← atajos para docker compose
nginx/default.conf  ← location /api/ → http://app:8080  |  location / → estáticos
frontend/           ← lo que nginx sirve tal cual
db/init.sql         ← esquema + datos la primera vez que se crea el volumen
app/                ← Spring Boot: Dockerfile (2 etapas), pom.xml y src/
```

## Notas

- El puerto se publica como `8080:80` para evitar el conflicto típico del puerto 80 en
  Windows (IIS/http.sys). Se cambia a `80:80` en el compose si lo tienes libre.
- `mariadb:12.3` ejecuta `db/init.sql` solo cuando el volumen `db-data` está vacío. Para
  resembrar: `docker compose down -v` y volver a `up`.
- La consola de MinIO (9001) no se publica: pieza interna. Para asomarla a nivel local,
  añade en el servicio `minio`: `ports: ["9001:9001"]`.
- `depends_on` + healthcheck: la app no arranca hasta que MariaDB acepta conexiones
  (`service_healthy`), lo que evita fallos de arranque al primer intento.

## Lo que aporta (como en el wiki de clase)

| Aporte | Cómo lo da |
|---|---|
| Reproducibilidad | `compose.yaml` describe el sistema completo |
| Paralelo con producción | topología proxy + app + BBDD + storage |
| Aislamiento | cada pieza en su contenedor, con su versión exacta |
| Exposición mínima | solo nginx publica puerto; el resto en la red interna |
