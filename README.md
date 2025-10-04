# Pinturillo - Tablero colaborativo en línea

Pinturillo es una aplicación web desarrollada con **Spring Boot** (Java) y **P5.js** (JavaScript) que permite a múltiples usuarios dibujar simultáneamente en un tablero compartido. Cada usuario inicia con un color diferente y puede borrar el tablero para todos los participantes.

---

## Arquitectura

### Backend (Spring Boot)
- **API REST**: expone endpoints para crear, consultar y borrar trazos (`strokes`).
- **Almacenamiento en memoria**: los trazos se guardan en una lista compartida (no se usa base de datos).
- **Sincronización**: todos los usuarios interactúan con la misma lista de trazos, lo que garantiza que el tablero sea el mismo para todos.
- **Endpoints principales:**
  - `GET /api/strokes?since={id}`: obtiene los trazos nuevos desde el último id conocido.
  - `POST /api/strokes`: agrega un nuevo trazo.
  - `DELETE /api/strokes`: borra todos los trazos (limpia el tablero).
  - `GET /api/strokes/epoch`: permite a los clientes detectar si el tablero fue borrado.

### Frontend (P5.js)
- **Canvas interactivo**: cada usuario puede dibujar con su color y grosor.
- **Polling**: el frontend consulta el backend cada 200 ms para obtener nuevos trazos y actualizaciones del tablero.
- **Colaboración**: los trazos de todos los usuarios se muestran en tiempo real (simulado por polling).
- **Botón de borrado**: al presionarlo, se borra el tablero para todos los usuarios.
- **Asignación de color**: cada usuario recibe un color aleatorio al iniciar la sesión.

---

## Funcionamiento

1. **Inicio**: el usuario accede a la web y recibe un color aleatorio.
2. **Dibujo**: al presionar y mover el mouse sobre el canvas, se envían trazos al backend.
3. **Sincronización**: el frontend consulta periódicamente la API para obtener nuevos trazos y actualiza el tablero.
4. **Borrado**: al presionar el botón de borrar, se elimina el contenido del tablero para todos los usuarios.


![alt text](/img/image.png)

---

## Instalación y ejecución

### Requisitos
- Java 17 o superior
- Maven

### Pasos
1. Clona el repositorio:
   ```sh
   git clone https://github.com/Juan-Jose-D/Pinturillo.git
   cd Pinturillo
   ```
2. Compila y ejecuta el backend:
   ```sh
   mvn spring-boot:run
   ```
3. Accede a la aplicación en tu navegador:
   [http://localhost:8080/](http://localhost:8080/)

---

## API REST

| Método | Endpoint                | Descripción                       |
|--------|-------------------------|-----------------------------------|
| GET    | /api/strokes?since={id} | Obtiene trazos nuevos             |
| POST   | /api/strokes            | Agrega un nuevo trazo             |
| DELETE | /api/strokes            | Borra todos los trazos            |
| GET    | /api/strokes/epoch      | Obtiene el estado de borrado      |

- Los trazos se envían/reciben como JSON:
  ```json
  {
    "id": 123,
    "x": 100,
    "y": 200,
    "color": "#ff006e",
    "size": 8,
    "timestamp": 1696360000000
  }
  ```

---

## Colaboración en tiempo real

- **Sin WebSockets**: la colaboración se logra mediante llamado frecuente al backend.
- **Tablero único**: todos los usuarios ven y modifican el mismo tablero.
- **Borrado global**: el botón de borrar afecta a todos los usuarios simultáneamente.

---



## Autor

- Juan José Díaz Gómez

