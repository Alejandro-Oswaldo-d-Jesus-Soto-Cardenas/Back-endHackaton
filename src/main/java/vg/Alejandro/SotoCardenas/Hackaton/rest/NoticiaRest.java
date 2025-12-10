// src/main/java/pe/edu/vallegrande/demo/rest/NoticiaRest.java
package vg.Alejandro.SotoCardenas.Hackaton.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vg.Alejandro.SotoCardenas.Hackaton.Model.Noticia;
import vg.Alejandro.SotoCardenas.Hackaton.Service.NoticiaService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/noticias")
@RequiredArgsConstructor
@Tag(name = "Noticias Ciudadanas", description = "Gestión completa de noticias del Diario Ciudadano")
public class NoticiaRest {

    private final NoticiaService service;

    @Operation(summary = "Listar noticias PUBLICADAS (para el público)")
    @GetMapping("/publicadas")
    public ResponseEntity<?> publicadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var pageable = PageRequest.of(page, size, Sort.by("fechaPublicacion").descending());
        return ResponseEntity.ok(toPage(service.listarPublicadas(pageable)));
    }

    @Operation(summary = "Listar TODAS las noticias (admin)")
    @GetMapping
    public ResponseEntity<?> todas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var pageable = PageRequest.of(page, size, Sort.by("fechaPublicacion").descending());
        return ResponseEntity.ok(toPage(service.listarTodas(pageable)));
    }

    @Operation(summary = "Obtener noticia por ID con corresponsal y fotos")
    @GetMapping("/{id}")
    public ResponseEntity<Noticia> porId(@PathVariable Long id) {
        return ResponseEntity.ok(service.porId(id));
    }

    @Operation(summary = "Crear nueva noticia (valida 1500-2500 palabras + fotos)")
    @PostMapping
    public ResponseEntity<Noticia> crear(@RequestBody Noticia noticia) {
        return ResponseEntity.ok(service.crear(noticia));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        service.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Long id) {
        service.activar(id);
        return ResponseEntity.noContent().build();
    }

    private Map<String, Object> toPage(org.springframework.data.domain.Page<Noticia> page) {
        Map<String, Object> res = new HashMap<>();
        res.put("content", page.getContent());
        res.put("page", page.getNumber());
        res.put("size", page.getSize());
        res.put("totalElements", page.getTotalElements());
        res.put("totalPages", page.getTotalPages());
        res.put("last", page.isLast());
        return res;
    }
}