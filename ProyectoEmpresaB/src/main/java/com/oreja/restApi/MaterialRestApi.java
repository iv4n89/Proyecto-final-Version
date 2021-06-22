package com.oreja.restApi;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oreja.dao.IMaterialDao;
import com.oreja.entities.Material;

@RestController
@RequestMapping(value = "/api/material")
public class MaterialRestApi {
    
    @Autowired
    private IMaterialDao materialDao;
    
    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterialById(@PathVariable("id") int id){
	return materialDao.findById(id)
			.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<Material>> getMateriales() {
	List<Material> list = materialDao.findAll();
	return new ResponseEntity<List<Material>>(list, HttpStatus.OK);
    }
    
    @GetMapping("/page/{page}/{sort}")
    public ResponseEntity<Page<Material>> getPageMaterial(@PathVariable(name = "page")int page, @PathVariable(name = "sort")String sort, @RequestParam(name = "size", defaultValue = "10")int size){
	Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
	Page<Material> materiales = materialDao.findAll(pageable);
	return new ResponseEntity<Page<Material>>(materiales, HttpStatus.OK);
    }
    
    @GetMapping("/sortResistencia/greater/{resistencia}")
    public ResponseEntity<Page<Material>> getByResistencia(@PathVariable("resistencia")double resistencia, @RequestParam(name = "page", defaultValue = "0")int page, @RequestParam(name = "size", defaultValue = "10")int size){
	Pageable pageable = PageRequest.of(page, size, Sort.by("codigo"));
	Page<Material> materiales = materialDao.findAllByResistenciaGreaterThan(resistencia, pageable);
	return new ResponseEntity<Page<Material>>(materiales, HttpStatus.OK);
    }
    
    @GetMapping("/sortResistencia/less/{resistencia}")
    public ResponseEntity<Page<Material>> getByResistenciaLess(@PathVariable("resistencia")double resistencia, @RequestParam(name = "page", defaultValue = "0")int page, @RequestParam(name = "size", defaultValue = "10")int size){
	Pageable pageable = PageRequest.of(page, size, Sort.by("codigo"));
	Page<Material> materiales = materialDao.findAllByResistenciaLessThanEqual(resistencia, pageable);
	return new ResponseEntity<Page<Material>>(materiales, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Material>addMaterial(@RequestBody Material material) {
	try {
	    if(materialDao.existsById(material.getCodigo())) {
		throw new Exception();
	    }
	    Material _material = materialDao.save(material);
	    return new ResponseEntity<>(_material, HttpStatus.CREATED);
	} catch(Exception e) {
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }
    
    @PutMapping
    public ResponseEntity<Material> updateMaterial(@RequestBody Material material){
	try {
	    Material _material = materialDao.save(material);
	    return new ResponseEntity<Material>(_material, HttpStatus.OK);
	} catch (Exception e) {
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }
    
    @DeleteMapping(path =  "/{codigo}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable(name = "codigo")int codigo) {
	Material material = materialDao.getOne(codigo);
	materialDao.delete(material);
	return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
}
