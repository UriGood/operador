package com.unir.operador.service;

import com.unir.operador.model.Compra;
import com.unir.operador.repository.CompraRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final WebClient.Builder webClientBuilder;

    public CompraService(CompraRepository compraRepository, WebClient.Builder webClientBuilder) {
        this.compraRepository = compraRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Compra> registrarCompra(Compra compra) {
        return webClientBuilder.build()
                .get()
                .uri("http://buscador/items/{id}", compra.getProductoId())
                .retrieve()
                .bodyToMono(ItemDTO.class)
                .flatMap(item -> {
                    if (item.getStock() < compra.getCantidad()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "No hay stock suficiente para el producto con ID " + compra.getProductoId()));
                    }
                    // Guardamos y devolvemos como Mono
                    return Mono.fromCallable(() -> compraRepository.save(compra)).subscribeOn(Schedulers.boundedElastic());
                });
    }

    // DTO interno para recibir respuesta del buscador
    public static class ItemDTO {
        private Long id;
        private String nombre;
        private Integer stock;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public Integer getStock() { return stock; }
        public void setStock(Integer stock) { this.stock = stock; }
    }

    // Métodos no reactivos para otras operaciones
    public List<Compra> obtenerTodas() {
        return compraRepository.findAll();
    }

    public Compra obtenerPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Compra no encontrada con ID " + id));
    }
}