package med.voll.api.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.direccion.Direccion;
import med.voll.api.medico.DatosEditarMedico;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String documento;
    private String telefono;
    private Boolean activo;
    @Embedded
    private Direccion direccion;

    /**
     * Constructor que recibe un DTO y lo mapea para crear una instancia de Paciente.
     * @param paciente Una instancia de DatosRegistroMedico
     */
    public Paciente(DatosRegistroPaciente paciente) {
        this.activo = true;
        this.nombre = paciente.nombre();
        this.email = paciente.email();
        this.documento = paciente.documento();
        this.telefono = paciente.telefono();
        this.direccion = new Direccion(paciente.direccion());
    }

    public void actualizarDatos(DatosEditarPaciente datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.documento() != null) {
            this.documento = datos.documento();
        }
        if (datos.direccion() != null) {
            this.direccion = direccion.actualizarDatos(datos.direccion());
        }

    }

    public void ocultarPaciente() {
        this.activo = false;
    }
}
