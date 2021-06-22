package com.oreja.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.oreja.models.Prueba;
import com.oreja.util.JsonUtil;

@Component
public class PruebaDaoImpl implements IPruebaDao {

    private List<Prueba> pruebas;
    @Autowired
    private JsonUtil jsonUtil;
    private Resource resource = new ClassPathResource("static/pruebas.json");
    private File file;
    private InputStream inputStream;

    public void setPruebas(List<Prueba> pruebas) {
	this.pruebas = pruebas;
    }

    @PostConstruct
    public void init() {
	try {
	    file = resource.getFile();
	    inputStream = resource.getInputStream();
	    try {
		readFileJson();
	    } catch (Exception e) {
		e.printStackTrace();
		setPruebas(new ArrayList<Prueba>());
	    }
	} catch (IOException e) {
	    e.fillInStackTrace();
	}
    }

    @Override
    public void addPrueba(Prueba prueba) {
	prueba.setId(getLastId());
	this.pruebas.add(prueba);
    }

    @Override
    public List<Prueba> getPruebasByMaterial(int codigo) {
	try {
	    List<Prueba> pMat = pruebas.stream().filter(p -> p.getMaterial().getCodigo() == codigo)
		    .collect(Collectors.toList());
	    return pMat;
	} catch (NullPointerException ex) {
	    List<Prueba> pruebas = new ArrayList<>();
	    setPruebas(pruebas);
	    return pruebas;
	}
    }

    @Override
    public Prueba getPrueba(int id) {
	return pruebas.stream().filter(prueba -> prueba.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void deletePrueba(Prueba prueba) {
	pruebas.remove(prueba);
    }

    @Override
    public void deleteAllPruebaForMaterial(int codigo) {
	getPruebasByMaterial(codigo).stream().forEach(p -> deletePrueba(p));
    }

    public void saveChanges() {
	try {
	    Files.write(file.toPath(), jsonUtil.serialize(pruebas).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
	} catch (Exception ex) {
		ex.printStackTrace();
	}
    }

    private void readFileJson() {
	try (InputStreamReader iSR = new InputStreamReader(inputStream);
		BufferedReader buffer = new BufferedReader(iSR)) {
	    List<Prueba> pruebas = jsonUtil.dererialize(buffer.readLine());
	    this.setPruebas(pruebas);
	}catch (IOException ex){
	    try {
		Files.createFile(resource.getFile().toPath());
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    private int getLastId() {
	    return pruebas.stream().mapToInt(p -> p.getId()).max().orElse(0) + 1;
    }
}
