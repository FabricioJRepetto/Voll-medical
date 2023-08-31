package med.voll.api.dominio.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.dominio.consulta.ConsultaRepository;
import med.voll.api.dominio.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta datos) {
        if (datos.idMedico() == null) {
            return;
        }

        var medicoConConsulta = repository.existsByMedico_idAndFecha(datos.idMedico(), datos.fecha());

        if (medicoConConsulta) {
            throw new ValidationException("El medico ya tiene una cita agendada para la fecha");
        }
    }
}
