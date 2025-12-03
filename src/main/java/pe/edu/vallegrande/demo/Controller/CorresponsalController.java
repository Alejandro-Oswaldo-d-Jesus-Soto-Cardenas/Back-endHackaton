package pe.edu.vallegrande.demo.Controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.edu.vallegrande.demo.Model.Corresponsal;
import pe.edu.vallegrande.demo.Repository.CorresponsalRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/corresponsales")
@RequiredArgsConstructor
public class CorresponsalController {

    private final CorresponsalRepository repository;

    @GetMapping
    public List<Corresponsal> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/activos")
    public List<Corresponsal> listarActivos() {
        return repository.findByStatusTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Corresponsal> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/por-doc/{idDoc}")
    public ResponseEntity<Corresponsal> obtenerPorIdDoc(@PathVariable("idDoc") String idDoc) {
        return repository.findByIdDoc(idDoc)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<Corresponsal> buscarPorApellido(@RequestParam("surnames") String surnames) {
        return repository.findBySurnamesContainingIgnoreCase(surnames);
    }

    @GetMapping("/registrados")
    public List<Corresponsal> buscarPorRangoFecha(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return repository.findByRegisterDayBetween(start, end);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Corresponsal nuevo) {
        if (repository.existsByIdDoc(nuevo.getIdDoc())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un corresponsal con ese id_doc");
        }
        Corresponsal saved = repository.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Corresponsal cambios) {
        Optional<Corresponsal> opt = repository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Corresponsal existente = opt.get();
        // Actualizar campos (no se cambia id ni register_day automáticamente)
        existente.setIdDoc(cambios.getIdDoc());
        existente.setSurnames(cambios.getSurnames());
        existente.setName(cambios.getName());
        existente.setCountry(cambios.getCountry());
        existente.setDepartment(cambios.getDepartment());
        existente.setProvince(cambios.getProvince());
        existente.setDistrict(cambios.getDistrict());
        existente.setLocality(cambios.getLocality());
        existente.setStatus(cambios.getStatus());

        Corresponsal saved = repository.save(existente);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLogico(@PathVariable Long id) {
        Optional<Corresponsal> opt = repository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Corresponsal c = opt.get();
        c.setStatus(false);
        repository.save(c);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        Optional<Corresponsal> opt = repository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Corresponsal c = opt.get();
        c.setStatus(true);
        repository.save(c);
        return ResponseEntity.ok(c);
    }
}