package com.oreja.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.oreja.models.Material;
import com.oreja.models.Prueba;

@Repository
public class MaterialRepositoryImpl implements IMaterialRepository {

    @Autowired
    private IPruebaDao pruebaDao;

    @Autowired
    private GenericClientDao<Material> materialDao; 

    @Override
    public Material saveMaterial(Material material) {
	Material created = materialDao.addObject(material);
	    return created;
    }

    @Override
    public Material getMaterial(int codigo) {
	Material material = materialDao.getObjectById(codigo);
	List<Prueba> pruebas = pruebaDao.getPruebasByMaterial(codigo);
	material.setPruebas(pruebas);
	return material;
    }

    @Override
    public Page<Material> getPageOfMaterial(int page, String sort) {
	return materialDao.getPage(page, sort)
		.map(m -> setPruebasToMaterial(m));
    }

    @Override
    public Page<Material> getPageOfMaterialByResistenciaLessThan(int page, double resistencia) {
	 return ((MaterialClientDaoImpl) materialDao)
		 .getPageByResistenciaLessThan(page, resistencia)
		 .map(m -> setPruebasToMaterial(m));
    }

    @Override
    public void deleteMaterial(int codigo) {
	pruebaDao.deleteAllPruebaForMaterial(codigo);
	((PruebaDaoImpl) pruebaDao).saveChanges();
	materialDao.deleteObject(codigo);
    }

    @Override
    public void addPrueba(Prueba prueba) {
	pruebaDao.addPrueba(prueba);
	((PruebaDaoImpl) pruebaDao).saveChanges();
    }

    @Override
    public Material updateMaterial(Material material) {
	return materialDao.updateObject(material);
    }

    @Override
    public Prueba getPruebaById(int id) {
	return pruebaDao.getPrueba(id);
    }

    @Override
    public void deletePrueba(Prueba prueba) {
	pruebaDao.deletePrueba(prueba);
	((PruebaDaoImpl) pruebaDao).saveChanges();
    }
    
    private Material setPruebasToMaterial(Material material) {
	List<Prueba> pruebas = pruebaDao.getPruebasByMaterial(material.getCodigo());
	material.setPruebas(pruebas);
	return material;
    }

}
