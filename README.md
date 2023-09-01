# Spring Boot #1 - CRUD
Creando una api rest con Spring boot, parte 1.<br>
Dependencias:<br>
```
Lombok
Bean Validation
Spring Security
```
[Propiedades de configuración para Spring](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)

### Creando el proyecto
* Spring initializr: devtools, test, lombok
* Configuraciones del proyecto
* `@RestController` y `@RequestMapping`
* Hello world!

---
### Request POST
`@RestController` `@RequestMapping("/url")` `@GetMapping` `@PostMapping` `@RequestBody`
* Insomnia
* Configurar [CORS](https://app.aluracursos.com/course/spring-boot-3-desarrollar-api-rest-java/task/83456)
* JSON
* DTO (Data Transfer Object): [Records](https://docs.oracle.com/en/java/javase/16/language/records.html#GUID-6699E26F-4A9B-4393-A08B-1E47D4B2D263)

---
### Spring Data JPA
[@Autowired](https://app.aluracursos.com/course/spring-boot-3-desarrollar-api-rest-java/task/84106)
* Añadiendo MySQL
* Dependencias: Spring Data JPA, MySQL Driver, Flyway Migration.
* `aplication.properties`: Conectando a la database 
* Creando las entidades: Etiquetas de **_Lombok_** para crear `getters`, `constructores`, y ``equals & hashcode``
* **Repository**: Su role es el mismo que tenían los [DAO](https://app.aluracursos.com/course/spring-boot-3-desarrollar-api-rest-java/task/83448) antiguamente, servir de intermediario entre la base de datos y la aplicación.
  <br>_El repositorio es un mecanismo para encapsular el almacenamiento, recuperación y comportamiento de búsqueda, que emula una colección de objetos._
* **Flyway**: Herramienta de migración. Creación automática de las tablas en la db utilizando 
las entidades. <br>Archivo `.sql` de configuración de flyway en `resources.db.migration`.
  ([Solución a posible error de migración](https://app.aluracursos.com/course/spring-boot-3-desarrollar-api-rest-java/task/83460))
* Validaciones: Se realizan en los DTO (records) con notaciones.

Pasos para creación endpoint/tabla
0) Crear Tabla (migración Flyway)
1) Controller: endpoint de la api
2) DTO (record): clase "modelo" de los datos que llegan a la api
3) Repository (interface): mediador entre la api y la base dedatos
4) Validaciones (dentro del DTO): prevenir errores en al base de datos

---
### Request GET
Para mostrar en consola los comandos sql, agregar estas lineas en `application.properties`:

```
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
> Retirar para producción.

* Retornando datos `@GetMapping`, `Repository.findAll()`
* Restringir parametros para el retorno. [Notación @JsonIgnore vs DTO](https://app.aluracursos.com/course/spring-boot-3-desarrollar-api-rest-java/task/83450)
* Paginación & Ordenamiento: `@PageableDefault()`. 
* [Query params](https://app.aluracursos.com/course/spring-boot-3-desarrollar-api-rest-java/task/83451)


---
### Request PUT y DELETE
* `@Transactional`
* Path params: `@PathVariable`
* `.findBy()`: este método se puede personalizar cambiandole el nombre. Spring mapea y crea una nueva query en base al nuevo nombre del método.

--- 
# Spring Boot #2 - Autenticación y Autorización

### Estandarizando respuestas
* Buenas prácticas
  * **GET**: 200 (ok) + recurso.
  * **PUT**: 200 (ok) + recurso actualizado.
  * **POST**: 201 (created) + Header "Location", url recurso. `UriComponentsBuilder`.
  * **DELETE**: 204 (no content).
* ResponseEntity: códigos de respuesta

---
### Tratando errores
Errores del lado DB como `EntityNotFoundException` (ID no encontrada) o `MethodArgumentNotValidException` (validaciónes no superadas) 
* No responder con el stack-trace: `server.error.include-stacktrace=never` en `application.properties`
* Control de errores a nivel global
* `@RestControllerAdvice`
* Idioma de los errores: El header `Accept-Language` sirve para indicar el idioma de respuesta, en el caso de esppañol el valor debe ser `es`.
* [Personalización de mensajes de error](https://app.aluracursos.com/course/spring-boot-3-aplique-practicas-proteja-api-rest/task/83812)

---
### Spring Security
Autenticación, Autorización, Protección contra ataques
* [Hash de contraseñas](https://app.aluracursos.com/course/spring-boot-3-aplique-practicas-proteja-api-rest/task/83814)
* Implementar configuración Spring Security
* Proceso de autenticación

---
### JWT (JSON Web Token)
* Generando un JWT
* `AutenticacionController`: Endpoint para iniciar sesion y recibir un jwt
* Definir variables de entorno

---
### Autorización de las Request
[Filtrar por roles](https://app.aluracursos.com/course/spring-boot-3-aplique-practicas-proteja-api-rest/task/83820) _deprecado?_
<br>
[Control de roles](https://app.aluracursos.com/course/spring-boot-3-aplique-practicas-proteja-api-rest/task/83821) _anotaciones_
* Filtros: aplicando filtro de validación en `SecurityFilter`
* `SecurityConfiguration`: 
  * filtrando todos los endpoints excepto `/login`
  * `addFilterBefore` para utilizar neustro filtro antes de los de Spring

---
# Spring Boot #3 - SOLID, documentación, etc.

### Agendar consultas (nueva función)
* [@JsonAlias](https://app.aluracursos.com/course/spring-boot-3-api-para-su-implementacion/task/84620): Indicar posibles nombres de los json y mapearlos a las propiedades correctas de los DTO.
* [Formato de las fechas](https://app.aluracursos.com/course/spring-boot-3-api-para-su-implementacion/task/84621)
* [Service Pattern](https://app.aluracursos.com/course/spring-boot-3-api-para-su-implementacion/task/84622)
* `@Query`
* Validadores: Interface e implementación automática con una List de componentes que implementan la interface.
* [SOLID](https://app.aluracursos.com/course/spring-boot-3-api-para-su-implementacion/task/84623)
* Testing

---
### Documentación SpringDoc
[Versión JSON](http://localhost:8080/v3/api-docs)<br>
[Interfaz](http://localhost:8080/swagger-ui/index.html)
* OpenAPI - Spring Doc: Instalar dependencias y agregar `/v3/api-docs` y `/swagger-ui.html` en la cofiguración de security (`infra/security/SecurityConfiguration`) para que no pida validación en esas rutas.
* Agregar jwt a las peticiones de prueba de la documentación.
* Agregar información y descripción a la página de documentación.

---
### Tests automatizados
* 
