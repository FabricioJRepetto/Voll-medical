package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datos) {
        // Repository
        medicoRepository.save(new Medico(datos));
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
    public Page<DatosListadoMedico> listadoMedico(@PageableDefault(size = 10, sort = "nombre") Pageable paginacion) {
        // Devolver página especificadas
        // Los params se reciben como argumento: page, size, sort (+ atributo de la db), etc.
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);

        //? findBy() se puede personalizar cambiando el nombre del metodo, WTF
        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
    }

    @PutMapping
    @Transactional
    public void actualizarMedico(@RequestBody @Valid DatosEditarMedico datos) {
        // Actualización utilizando un nuevo DTO para cumplir con reglas de negocio
        Medico medico = medicoRepository.getReferenceById(datos.id());
        medico.actualizarDatos(datos);
    }


    // Delete lógico
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.ocultarMedico();
    }

    // Delete real en base de datos
//    public void eliminarMedico(@PathVariable Long id) {
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }
}