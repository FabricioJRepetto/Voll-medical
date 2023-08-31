package med.voll.api.dominio.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface MedicoRepository extends JpaRepository<Medico, Long> {
    // Query personalizada. Spring mapea y crea una nueva query en base al nombre del método
    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query("""
            select m from Medico m
            where m.activo=true and
            m.especialidad=:especialidad and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.fecha=:fecha
            )
            order by rand() limit 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);

    Medico findActivoById(Long id);
}
