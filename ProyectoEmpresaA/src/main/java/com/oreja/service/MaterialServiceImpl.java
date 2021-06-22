package com.oreja.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oreja.dao.IMaterialRepository;
import com.oreja.models.Material;
import com.oreja.models.Prueba;

@Service
public class MaterialServiceImpl implements IMaterialService{

    @Autowired
    private IMaterialRepository repository;
    
    //Lista de materiales auxiliar usada para cargar las pruebas
    private List<Material> materialesAux;
    
    //Método auxiliar para obtener las pruebas de un material concreto, dentro de la lista de materialesAux, de modo se pueda actuallizar la lista de pruebas una vez se añadan o eliminen
    private List<Prueba> getPruebasAux(Material material) {
	return this.materialesAux.get(materialesAux.indexOf(material)).getPruebas();
    }
    
    public Material getMaterialWithPruebas(int index) {
	return this.materialesAux.get(index);
    }
    
    @Override
    public Material saveMaterial(Material material) {
	return repository.saveMaterial(material);
    }

    @Override
    public void savePrueba(Prueba prueba) {
	repository.addPrueba(prueba);
	getPruebasAux(prueba.getMaterial()).add(prueba);
    }

    @Override
    public Material getMaterial(int codigo) {
	return repository.getMaterial(codigo);
    }

    @Override
    public Prueba getPrueba(int id) {
	return repository.getPruebaById(id);
    }

    @Override
    public Page<Material> getPageOfMaterial(int page, String sort) {
	Page<Material> materiales  = repository.getPageOfMaterial(page, sort);
	materialesAux = materiales.toList();
	return materiales;
    }

    @Override
    public Page<Material> getPageOfMaterialByResistenciaLessThan(int page, double resistencia) {
	Page<Material> materiales  = repository.getPageOfMaterialByResistenciaLessThan(page, resistencia);
	materialesAux = materiales .toList();
	return materiales;
    }

    @Override
    public Page<Prueba> getPageOfPruebaForMaterial(int page, Material material) {
	List<Prueba> pruebas = material.getPruebas();
	Pageable pageable = PageRequest.of(page, 3);
	final int start = (int) pageable.getOffset();
	final int end = Math.min((start + pageable.getPageSize()), pruebas.size());
	return new PageImpl<>(pruebas.subList(start, end), pageable, pruebas.size());
    }
    
   @Override
    public Material updateMaterial(Material material) {
        return repository.updateMaterial(material);
    }

    @Override
    public void deleteMaterial(int codigo) {
	repository.deleteMaterial(codigo);
    }

    @Override
    public void deletePrueba(Prueba prueba) {
	repository.deletePrueba(prueba);
	getPruebasAux(prueba.getMaterial()).remove((Prueba) prueba);
    }
    
    public int getPageNumberBySizeAfterDeleteForPruebas(int size, int index, int page) {
	return materialesAux.get(index).getPruebas().size() % size == 0 ? page-1 : page;
    }
    
    public int getPageNumberBySizeAfterDeleteForMaterial(int size, int page) {
	return materialesAux.size() % size-1 == 0 ? page-1 : page;
    }

}
