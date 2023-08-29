package med.voll.api.dominio.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.dominio.direccion.DatosDireccion;

public record DatosEditarPaciente(@NotNull Long id, String nombre, String documento, String email, DatosDireccion direccion) {
}
