package med.voll.api.dominio.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.dominio.consulta.DatosAgendarConsulta;
import med.voll.api.dominio.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorDeConsultas{

    @Autowired
    private MedicoRepository repository;

    public void validar(DatosAgendarConsulta datos) {
        if (datos.idMedico() == null) {
            return;
        }

        var medicoActivo = repository.findActivoById(datos.idMedico());

        if (!medicoActivo.getActivo()) {
            throw new ValidationException("El medico debe estar activo para agendar una cita");
        }
    }
}
