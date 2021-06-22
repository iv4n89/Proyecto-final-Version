package com.oreja.dao;

import org.springframework.data.domain.Page;
import com.oreja.models.Material;
import com.oreja.models.Prueba;

public interface IMaterialRepository {

    Material saveMaterial(Material material);

    Material getMaterial(int codigo);

    Page<Material> getPageOfMaterial(int page, String sort);
    
    Material updateMaterial(Material material);

    void deleteMaterial(int codigo);

    void addPrueba(Prueba prueba);

    void deletePrueba(Prueba prueba);

    Prueba getPruebaById(int id);

    Page<Material> getPageOfMaterialByResistenciaLessThan(int page, double resistencia);

}