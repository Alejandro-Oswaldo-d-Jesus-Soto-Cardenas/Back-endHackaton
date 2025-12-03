// src/main/java/pe/edu/vallegrande/demo/Service/UbigeoService.java
package pe.edu.vallegrande.demo.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.vallegrande.demo.Model.Ubigeo;
import pe.edu.vallegrande.demo.Repository.UbigeoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UbigeoService {

    private final UbigeoRepository repository;

    public List<Ubigeo> listarTodos() {
        return repository.findAll();
    }

    public List<Ubigeo> listarActivos() {
        return repository.findByStatusTrue();
    }

    public Page<Ubigeo> listarActivosPaginado(Pageable pageable) {
        return repository.findByStatusTrue(pageable);
    }

    public List<Ubigeo> porProvincia(String provincia) {
        return repository.findByProvinciaIgnoreCase(provincia);
    }

    public Ubigeo porId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubigeo no encontrado: " + id));
    }

    public void desactivar(Long id) {
        Ubigeo u = porId(id);
        u.setStatus(false);
        repository.save(u);
    }

    public void activar(Long id) {
        Ubigeo u = porId(id);
        u.setStatus(true);
        repository.save(u);
    }
}