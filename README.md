# REST API Prueba técnica backend
## Descripción 
La aplicación sirve para agregar datos sobre Clases y Participantes, la aplicación ha sido desarrollada con Spring framework y Java.
## Requisitos
Java 7.x o superior, MariaDB, Spring Tools Suite 4.
## Instalación
Hay que modificar el archivo `application.properties` y modificar el nombre de la base de datos, usuario y contraseña.
## Uso
Abrir con STS 4 para mayor rapidez en la prueba y ejecutar, la aplicación REST estará disponible en `http://localhost:8080/restapi`
## /participante
Las siguientes operaciones para `http://localhost:8080/restapi/participante` estan disponibles.
### /todos (disponible por GET)
No recibe ningun parametro, devuelve todos los participantes y su Clase asociada, de no haber ninguno devolverá `[{id:-1}]`.
### /id (disponible por GET)
Recibe un parametro nombrado `id`, es el identificador del participante, devuelve sus datos asociados (ej. `http://localhost:8080/restapi/participante/id?id=24`), de no encontrarse devolverá `{id:-1}`.
### /nombre (disponiblepor GET)
Recibe un parametro nombrado `nombre`, es un nombre parcial o completo, devuelve los datos asociados del nombre coincidente (ej. `http://localhost:8080/restapi/participante/nombre?nombre=Bern`), de no encontrarse devolvera `[{id:-1}]`.
### /agrega (disponible por PUT)
Recibe un json con la informacion asociada a un participante a través del cuerpo de la petición http (ej. `{nombre:'Jordi', correo:'kokiri@link.ja',observaciones:'Platica mucho', clase:2}`), devuelve un json con el id asignado, de haber error se devolverá `{id:-1}`.
### /elimina (disponible por DELETE)
Recibe un parametro nombrado `id`, es el identificador del participante, elimina sus datos asociados (ej. `http://localhost:8080/restapi/participante/elimina?id=24`) y devuelve el identificador del participante, de no haber algun error devolverá `{id:-1}`.
## /clase
Las siguientes operaciones para `http://localhost:8080/restapi/clase` estan disponibles.
### /todos (disponible por GET)
No recibe ningun parametro, devuelve todos las clases y sus participantes asociada, de no haber ninguno devolverá `[{id:-1}]`.
### /id (disponible por GET)
Recibe un parametro nombrado `id`, es el identificador de la clase, devuelve sus datos asociados (ej. `http://localhost:8080/restapi/clase/id?id=24`), de no encontrarse devolverá `{id:-1}`.
### /nombre (disponiblepor GET)
Recibe un parametro nombrado `nombre`, es un nombre parcial o completo, devuelve los datos asociados del nombre coincidente (ej. `http://localhost:8080/restapi/clase/nombre?nombre=Sistemas`), de no encontrarse devolvera `[{id:-1}]`.
### /agrega (disponible por PUT)
Recibe un json con la informacion asociada a una clase a través del cuerpo de la petición http (ej. `{descripcion:'Sistemas operativos', tipo:'presencial'}`), devuelve un json con el id asignado, de haber error se devolverá `{id:-1}`.
### /elimina (disponible por DELETE)
Recibe un parametro nombrado `id`, es el identificador de la clase, elimina sus datos asociados (ej. `http://localhost:8080/restapi/clase/elimina?id=24`) y devuelve el identificador de la clase y los identificadores de los participantes asociados, de no haber algun error devolverá `{id:-1}`.

## Notas importantes
* Todo Participante que se quiera agregar necesita que antes exista la Clase, pero toda clase puede agregarse aún si no hay participantes.
* La creación y modificación de las tablas de la base de datos se hace de forma automática al desplegarse la aplicación, así que para modificar la base de datos es necesario modificar los archivos java de las entidades.
