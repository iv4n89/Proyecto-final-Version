package com.oreja.service;

import org.springframework.data.domain.Page;
import com.oreja.models.Material;
import com.oreja.models.Prueba;

public interface IMaterialService {
   Material saveMaterial(Material material);
    void savePrueba(Prueba prueba);
    Material getMaterial(int codigo);
    Prueba getPrueba(int id);
    Page<Material> getPageOfMaterial(int page, String sort);
    Page<Material> getPageOfMaterialByResistenciaLessThan(int page, double resistencia);
    Page<Prueba> getPageOfPruebaForMaterial(int page, Material material);
    Material updateMaterial(Material material);
    void deleteMaterial(int codigol);
    void deletePrueba(Prueba pruebal);
}
