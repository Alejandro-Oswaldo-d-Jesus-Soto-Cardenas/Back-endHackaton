// src/main/java/pe/edu/vallegrande/demo/rest/CorresponsalRest.java
package pe.edu.vallegrande.demo.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.demo.Model.Corresponsal;
import pe.edu.vallegrande.demo.Service.CorresponsalService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/corresponsales")
@RequiredArgsConstructor
@Tag(name = "Corresponsales", description = "Gestión de corresponsales del diario ciudadano")
public class CorresponsalRest {

    private final CorresponsalService service;

    @Operation(summary = "Listar todos los corresponsales con paginación",
               description = "Devuelve una página de corresponsales (activos e inactivos)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                     content = @Content(schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodos(
            @Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Orden: campo,direction (ej: id,desc)") @RequestParam(defaultValue = "id,desc") String sort) {

        Sort sortOrder = Sort.by(sort.contains("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                sort.split(",")[0]);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        return ResponseEntity.ok(toPageResponse(service.findAll(pageable)));
    }

    @Operation(summary = "Listar solo corresponsales activos", description = "Útil para formularios y reportes públicos")
    @GetMapping("/activos")
    public ResponseEntity<Map<String, Object>> activos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(toPageResponse(service.findActive(pageable)));
    }

    @Operation(summary = "Buscar corresponsal por apellidos", description = "Búsqueda parcial e insensible a mayúsculas")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorApellido(
            @Parameter(description = "Apellidos a buscar (puede ser parte del texto)") 
            @RequestParam("surnames") String surnames) {
        return ResponseEntity.ok(service.findBySurnames(surnames));
    }

    @Operation(summary = "Buscar por rango de fechas de registro")
    @GetMapping("/registrados")
    public ResponseEntity<?> porRangoFecha(
            @Parameter(description = "Fecha inicial (ISO: 2025-01-01T00:00:00)") 
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @Parameter(description = "Fecha final (ISO)") 
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(service.findByDateRange(start, end));
    }

    @Operation(summary = "Obtener corresponsal por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Corresponsal encontrado"),
        @ApiResponse(responseCode = "404", description = "No existe corresponsal con ese ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Corresponsal> porId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear nuevo corresponsal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Corresponsal creado exitosamente"),
        @ApiResponse(responseCode = "409", description = "Ya existe un corresponsal con ese documento")
    })
    @PostMapping
    public ResponseEntity<Corresponsal> crear(@Valid @RequestBody Corresponsal nuevo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(nuevo));
    }

    @Operation(summary = "Actualizar datos de un corresponsal")
    @PutMapping("/{id}")
    public ResponseEntity<Corresponsal> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody Corresponsal cambios) {
        return ResponseEntity.ok(service.update(id, cambios));
    }

    @Operation(summary = "Desactivar corresponsal (soft delete)", description = "Cambia status a false")
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        service.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activar corresponsal nuevamente")
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Corresponsal> activar(@PathVariable Long id) {
        return ResponseEntity.ok(service.activar(id));
    }

    // Clase auxiliar para que Swagger muestre bien la respuesta paginada
    private Map<String, Object> toPageResponse(Page<Corresponsal> page) {
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

// Clase para que Swagger entienda la estructura paginada
record PageResponse(
    Object content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean last
) {}