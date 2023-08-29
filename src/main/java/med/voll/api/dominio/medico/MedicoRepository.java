package med.voll.api.dominio.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicoRepository extends JpaRepository<Medico, Long> {
    // Query personalizada. Spring mapea y crea una nueva query en base al nombre del método
    Page<Medico> findByActivoTrue(Pageable paginacion);
}
