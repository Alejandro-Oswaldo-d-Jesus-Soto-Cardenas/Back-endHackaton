// src/main/java/pe/edu/vallegrande/demo/Service/NoticiaService.java
package pe.edu.vallegrande.demo.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.vallegrande.demo.Model.Noticia;
import pe.edu.vallegrande.demo.Repository.NoticiaRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticiaService {

    private final NoticiaRepository repository;

    public Page<Noticia> listarPublicadas(Pageable pageable) {
        return repository.findByStatusTrue(pageable);
    }

    public Page<Noticia> listarTodas(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Noticia porId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));
    }

    public Noticia crear(Noticia noticia) {
        validarContenido(noticia);
        return repository.save(noticia);
    }

    public void desactivar(Long id) {
        Noticia n = porId(id);
        n.setStatus(false);
        repository.save(n);
    }

    public void activar(Long id) {
        Noticia n = porId(id);
        n.setStatus(true);
        repository.save(n);
    }

    private void validarContenido(Noticia n) {
        int palabras = n.getContenido().trim().split("\\s+").length;
        if (palabras < 1500 || palabras > 2500) {
            throw new IllegalArgumentException(
                "El contenido debe tener entre 1500 y 2500 palabras. Tiene: " + palabras);
        }
        if (n.getFotos() == null || n.getFotos().isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos 1 foto");
        }
        if (n.getFotos().size() > 3) {
            throw new IllegalArgumentException("Máximo 3 fotos permitidas");
        }
    }
}