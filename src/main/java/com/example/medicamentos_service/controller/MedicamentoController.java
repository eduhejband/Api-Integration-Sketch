package com.example.medicamentos_service.controller;

import com.example.medicamentos_service.dto.MedicamentoDTO;
import com.example.medicamentos_service.service.MedicamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {
    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    // Criar medicamento
    @PostMapping
    public ResponseEntity<MedicamentoDTO> salvar(@RequestBody MedicamentoDTO medicamentoDTO) {
        return ResponseEntity.ok(medicamentoService.salvar(medicamentoDTO));
    }

    // Buscar medicamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> buscarPorId(@PathVariable Long id) {
        Optional<MedicamentoDTO> medicamento = medicamentoService.buscarPorId(id);
        return medicamento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar todos os medicamentos
    @GetMapping
    public ResponseEntity<List<MedicamentoDTO>> buscarTodos() {
        return ResponseEntity.ok(medicamentoService.buscarTodos());
    }

    // Atualizar medicamento
    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> atualizar(@PathVariable Long id, @RequestBody MedicamentoDTO medicamentoDTO) {
        Optional<MedicamentoDTO> atualizado = medicamentoService.atualizar(id, medicamentoDTO);
        return atualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar medicamento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (medicamentoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
