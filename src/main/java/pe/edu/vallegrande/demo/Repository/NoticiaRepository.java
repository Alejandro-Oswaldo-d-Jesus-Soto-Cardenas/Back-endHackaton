// src/main/java/pe/edu/vallegrande/demo/Repository/NoticiaRepository.java
package pe.edu.vallegrande.demo.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.vallegrande.demo.Model.Noticia;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    Page<Noticia> findByStatusTrue(Pageable pageable);
    Page<Noticia> findAllByOrderByFechaPublicacionDesc(Pageable pageable);
}