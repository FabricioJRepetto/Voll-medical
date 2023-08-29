package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dominio.direccion.DatosDireccion;
import med.voll.api.dominio.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    /*  No es recomendable utilizar @Autowired a nivel de definición.
        En caso de hacer unitary testing, es muy difícil hacer un mock.
        Otra forma de hacerlo es con Setter (?)
     */
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datos,
                                                                UriComponentsBuilder uri) {
        // Repository
        Medico medico = medicoRepository.save(new Medico(datos));

        // 201 (created)
        // header con url del recurso
        DatosRespuestaMedico respuestaMedico = new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getDocumento(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()
                )
        );

        URI url = uri.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaMedico);


    }

//    @GetMapping
//    public List<DatosListadoMedico> listadoMedico() {
//        // Restringiendo datos en la respuesta con un DTO específico
//        // También se puede utilizar la notación @JsonIgnore
//        return medicoRepository.findAll()
//                .stream()
//                .map(DatosListadoMedico::new)
//                .toList();
//    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedico(@PageableDefault(size = 10, sort = "nombre") Pageable paginacion) {
        // Devolver página especificadas
        // Los params se reciben como argumento: page, size, sort (+ atributo de la db), etc.
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);

        //? findBy() se puede personalizar cambiando el nombre del metodo, WTF
        Page<DatosListadoMedico> body = medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);

        // 200 (ok)
        // retornar recurso
        return ResponseEntity.ok(body);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosEditarMedico datos) {
        // Actualización utilizando un nuevo DTO para cumplir con reglas de negocio
        Medico medico = medicoRepository.getReferenceById(datos.id());
        medico.actualizarDatos(datos);

        // 200 (ok)
        // retornar obj actualizado.
        return ResponseEntity.ok(new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getDocumento(),
                new DatosDireccion(
                    medico.getDireccion().getCalle(),
                    medico.getDireccion().getDistrito(),
                    medico.getDireccion().getCiudad(),
                    medico.getDireccion().getNumero(),
                    medico.getDireccion().getComplemento()
                )
                ));
    }


    // Delete lógico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.ocultarMedico();
        return ResponseEntity.noContent().build();
    }

    // Delete real en base de datos
//    public void eliminarMedico(@PathVariable Long id) {
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);

        // 200 (ok)
        // retornar recurso
        DatosRespuestaMedico respuestaMedico = new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getDocumento(),
                new DatosDireccion(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()
                )
        );
        return ResponseEntity.ok(respuestaMedico);
    }
}