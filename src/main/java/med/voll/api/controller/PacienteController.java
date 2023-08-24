package med.voll.api.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    public void registrarPaciente(@RequestBody PacienteController datos) {
        System.out.println(datos);
    }
}
