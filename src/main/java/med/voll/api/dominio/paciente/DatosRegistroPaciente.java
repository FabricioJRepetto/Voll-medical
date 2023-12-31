package med.voll.api.dominio.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.dominio.direccion.DatosDireccion;

/**
 *
 * @param nombre
 * @param email
 * @param telefono
 * @param documento
 * @param direccion
 */
public record DatosRegistroPaciente(
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{6,20}")
        String telefono,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String documento,
        @NotNull
        @Valid
        DatosDireccion direccion
) {
}
