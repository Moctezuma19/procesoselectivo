# REST API Prueba técnica backend
## Descripción 
La aplicación sirve para agregar datos sobre Clases y Alumnos, la aplicación ha sido desarrollada con Spring framework y Java.
## Requisitos
Java 7.x o superior, MariaDB, Spring Tools Suite 4
## Instalación
Hay que modificar el archivo `application.properties` y modificar el nombre de la base de datos, usuario y contraseña
## Uso
Abrir con STS 4 para mayor rapidez en la prueba y ejecutar, la aplicación REST estará disponible en `http://localhost:8080/restapi`
## /participante
Las siguientes operaciones para `http://localhost:8080/restapi/participante` estan disponibles.
### /todos (disponible por GET)
No recibe ningun parametro, devuelve todos los participantes y su Clase asociada, de no haber ninguno devolverá `[{id:-1}]`.
### /id (disponible por GET)
Recibe un parametro nombrado `id`, es el identificador del alumno, devuelve sus datos asociados (ej. `http://localhost:8080/restapi/participante/id?id=24`), de no encontrarse devolverá `{id:-1}`.
### /nombre (disponiblepor GET)
Recibe un parametro nombrado `nombre`, es un nombre parcial o completo, devuelve los datos asociados del nombre coincidente (ej. `http://localhost:8080/restapi/participante/nombre?nombre=Bern`), de no encontrarse devolvera `[{id:-1}]`.
### /agrega (disponible por PUT)
Recibe un json con la informacion asociada a un alumno a través del cuerpo de la petición http (ej. `{nombre:'Jordi', correo:'kokiri@link.ja',observaciones:'Platica mucho', clase:2}`), devuelve un json con el id asignado, de haber error se devolverá `{id:-1}`.
### /elimina (disponible por DELETE)
Recibe un parametro nombrado `id`, es el identificador del alumno, elimina sus datos asociados (ej. `http://localhost:8080/restapi/participante/elimina?id=24`), de no haber algun error devolverá `{id:-1}`.
## /clase
Aún en desarrollo.
