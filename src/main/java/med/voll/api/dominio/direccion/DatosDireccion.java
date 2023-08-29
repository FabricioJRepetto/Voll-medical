package med.voll.api.dominio.direccion;

import jakarta.validation.constraints.NotBlank;

/**
 *
 * @param calle
 * @param distrito
 * @param ciudad
 * @param numero
 * @param complemento
 */
public record DatosDireccion(
        @NotBlank
        String calle,
        @NotBlank
        String distrito,
        @NotBlank
        String ciudad,
        @NotBlank
        String numero,
        @NotBlank
        String complemento
) {
}
