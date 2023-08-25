# Spring Boot #1 - CRUD
Creando una api rest con Spring boot, parte 1.

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