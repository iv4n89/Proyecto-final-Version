package com.oreja.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oreja.entities.Material;

@Repository
public interface IMaterialDao extends JpaRepository<Material, Integer>{
    Page<Material> findAllByResistenciaGreaterThan (double resistencia, Pageable pageable);
    Page<Material> findAllByResistenciaGreaterThanEqual (double resistencia, Pageable pageable);
    Page<Material> findAllByResistenciaLessThanEqual (double resistencia, Pageable pageable);
    Page<Material> findAllByResistenciaLessThan (double resistencia, Pageable pageable);
}
