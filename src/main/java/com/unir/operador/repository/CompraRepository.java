package com.unir.operador.repository;

import com.unir.operador.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByDevueltoTrue();

    List<Compra> findByDevueltoFalse();


}