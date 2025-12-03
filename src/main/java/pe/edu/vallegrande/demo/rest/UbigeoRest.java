// src/main/java/pe/edu/vallegrande/demo/rest/UbigeoRest.java
package pe.edu.vallegrande.demo.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.demo.Model.Ubigeo;
import pe.edu.vallegrande.demo.Service.UbigeoService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ubigeo")
@RequiredArgsConstructor
@Tag(name = "Ubigeo", description = "Gestión de distritos del Perú (solo Cañete - Lima)")
public class UbigeoRest {

    private final UbigeoService service;

    @Operation(summary = "Listar todos los distritos de Cañete")
    @GetMapping
    public ResponseEntity<?> todos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Listar solo distritos activos con paginación")
    @GetMapping("/activos")
    public ResponseEntity<?> activos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Ubigeo> pagina = service.listarActivosPaginado(
                PageRequest.of(page, size, Sort.by("distrito")));
        return ResponseEntity.ok(toPageResponse(pagina));
    }

    @Operation(summary = "Buscar distritos por provincia (ej: Cañete)")
    @GetMapping("/provincia/{nombre}")
    public ResponseEntity<?> porProvincia(@PathVariable String nombre) {
        return ResponseEntity.ok(service.porProvincia(nombre));
    }

    @Operation(summary = "Obtener un distrito por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Ubigeo> porId(@PathVariable Long id) {
        return ResponseEntity.ok(service.porId(id));
    }

    @Operation(summary = "Desactivar distrito (eliminado lógico)")
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        service.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activar distrito nuevamente")
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Ubigeo> activar(@PathVariable Long id) {
        service.activar(id);
        return ResponseEntity.ok(service.porId(id));
    }

    private Map<String, Object> toPageResponse(Page<Ubigeo> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", page.getContent());
        response.put("page", page.getNumber());
        response.put("size", page.getSize());
        response.put("totalElements", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("last", page.isLast());
        return response;
    }
}