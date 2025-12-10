// src/main/java/pe/edu/vallegrande/demo/Repository/NoticiaRepository.java
package vg.Alejandro.SotoCardenas.Hackaton.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vg.Alejandro.SotoCardenas.Hackaton.Model.Noticia;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    Page<Noticia> findByStatusTrue(Pageable pageable);
    Page<Noticia> findAllByOrderByFechaPublicacionDesc(Pageable pageable);
}