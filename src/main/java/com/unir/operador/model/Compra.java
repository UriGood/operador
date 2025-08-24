package com.unir.operador.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productoId;
    private Integer cantidad;
    private Double total;
    private boolean devuelto = false;
    private LocalDateTime fecha;

    public Compra() {
        this.fecha = LocalDateTime.now();
    }

    public Compra(String  productoId, Integer cantidad, Double total) {
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.total = total;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String  getProductoId() {
        return productoId;
    }

    public void setProductoId(String  productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }
}