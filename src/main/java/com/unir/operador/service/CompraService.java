package com.unir.operador.service;

import com.unir.operador.exception.CompraNotFoundException;
import com.unir.operador.exception.OutOfStockException;
import com.unir.operador.model.Compra;
import com.unir.operador.repository.CompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final RestTemplate restTemplate;

    public CompraService(CompraRepository compraRepository, RestTemplate restTemplate) {
        this.compraRepository = compraRepository;
        this.restTemplate = restTemplate;
    }

    public Compra registrarCompra(Compra compra) {
        // Llamada a buscador con RestTemplate (sin WebFlux)
        ItemDTO item = restTemplate.getForObject(
                "http://buscador/items/{id}",
                ItemDTO.class,
                compra.getProductoId()
        );

        if (item == null) {
            throw new CompraNotFoundException("No existe el producto con ID " + compra.getProductoId());
        }

        if (item.getStock() < compra.getCantidad()) {
            throw new OutOfStockException("No hay stock suficiente para el producto con ID " + compra.getProductoId());
        }

        // Calcular total
        compra.setTotal(item.getPrice() * compra.getCantidad());

        // Guardar en la BD
        return compraRepository.save(compra);
    }

    // DTO interno para recibir respuesta del microservicio buscador
    public static class ItemDTO {
        private String id;
        private String title;   // 🔹 debe coincidir con el JSON del buscador
        private Integer stock;
        private Double price;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public Integer getStock() { return stock; }
        public void setStock(Integer stock) { this.stock = stock; }

        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
    }

    // Métodos no reactivos para consultas simples
    public List<Compra> obtenerTodas() {
        return compraRepository.findByDevueltoFalse();
    }

    public Compra obtenerPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new CompraNotFoundException("Compra no encontrada con ID " + id));
    }

    public Compra devolverCompra(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new CompraNotFoundException("Compra no encontrada"));
        compra.setDevuelto(true);
        return compraRepository.save(compra);
    }

    public List<Compra> obtenerDevueltas() {
        return compraRepository.findByDevueltoTrue();
    }
}