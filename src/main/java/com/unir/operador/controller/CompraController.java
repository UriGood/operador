package com.unir.operador.controller;

import com.unir.operador.model.Compra;
import com.unir.operador.service.CompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    // Registrar nueva compra
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Compra registrarCompra(@RequestBody Compra compra) {
        return compraService.registrarCompra(compra).block();
    }

    // Obtener todas las compras
    @GetMapping
    public List<Compra> obtenerTodas() {
        return compraService.obtenerTodas();
    }

    // Obtener compra por ID
    @GetMapping("/{id}")
    public Compra obtenerPorId(@PathVariable Long id) {
        return compraService.obtenerPorId(id);
    }


    @PutMapping("/{id}/devolver")
    public ResponseEntity<Compra> devolverCompra(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.devolverCompra(id));
    }

    @GetMapping("/devueltas")
    public List<Compra> getComprasDevueltas() {
        return compraService.obtenerDevueltas();
    }
}