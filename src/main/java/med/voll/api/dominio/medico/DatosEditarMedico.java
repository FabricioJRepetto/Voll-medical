package med.voll.api.dominio.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.dominio.direccion.DatosDireccion;

public record DatosEditarMedico(@NotNull Long id, String nombre, String documento, DatosDireccion direccion) {
}
