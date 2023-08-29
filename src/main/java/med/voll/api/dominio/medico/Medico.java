package med.voll.api.dominio.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dominio.direccion.Direccion;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String documento;
    private String telefono;
    private Boolean activo;

    @Enumerated(EnumType.STRING)
    private Epecialidad especialidad;
    @Embedded
    private Direccion direccion;

    /**
     * Constructor que recibe un DTO y lo mapea para crear una instancia de Medico.
     * @param medico Una instancia de DatosRegistroMedico
     */
    public Medico(DatosRegistroMedico medico) {
        this.activo = true;
        this.nombre = medico.nombre();
        this.email = medico.email();
        this.documento = medico.documento();
        this.telefono = medico.telefono();
        this.especialidad = medico.especialidad();
        this.direccion = new Direccion(medico.direccion());
    }

    public void actualizarDatos(DatosEditarMedico datos) {
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

    public void ocultarMedico() {
        this.activo = false;
    }
}
