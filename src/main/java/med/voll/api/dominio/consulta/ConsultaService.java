package med.voll.api.dominio.consulta;

import med.voll.api.dominio.consulta.validaciones.HorarioDeAnticipacion;
import med.voll.api.dominio.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.dominio.medico.Medico;
import med.voll.api.dominio.medico.MedicoRepository;
import med.voll.api.dominio.paciente.Paciente;
import med.voll.api.dominio.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos) {

        if (!pacienteRepository.existsById(datos.idPaciente())) {
            throw new ValidacionDeIntegridad("Este ID de paciente no fue encontrado");
        }
        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionDeIntegridad("Este ID de medico no fue encontrado");
        }

        // Validaciones
        validadores.forEach(v -> v.validar(datos));


        //! Si no hay ningún medico disponible ???
        Medico medico = seleccionarMedico(datos);

        if (medico == null) {
            throw new ValidacionDeIntegridad("No hay medicos disponibles en este horario");
        }

        Paciente paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var consulta = new Consulta(null, paciente, medico, datos.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        // Si hay un id correcto para el medico, retornarlo
        if (datos.idMedico() != null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        // Es necesario indicar una especialidad para asignar un medico aleatorio
        if (datos.especialidad() == null) {
            throw new ValidacionDeIntegridad("Debe indicar una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
    }
}
