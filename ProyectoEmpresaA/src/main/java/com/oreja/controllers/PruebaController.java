package com.oreja.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.oreja.models.Material;
import com.oreja.models.Prueba;
import com.oreja.service.IMaterialService;
import com.oreja.service.MaterialServiceImpl;
import com.oreja.util.RenderizadorPaginas;

@Controller
public class PruebaController {
    
    @Autowired
    private IMaterialService service;
    
    private final int PAGE_SIZE = 3;

    @GetMapping("/pruebas/{index}")
    public String pruebas(@RequestParam(name = "page", defaultValue = "0")int page,@PathVariable(name = "index")int index, Material material, Prueba prueba, Model model) {
	material = ((MaterialServiceImpl) service).getMaterialWithPruebas(index);
	Page<Prueba> pruebas = service.getPageOfPruebaForMaterial(page, material);
	RenderizadorPaginas<Prueba> renderizadorPaginas = new RenderizadorPaginas<Prueba>("/pruebas/"+index, pruebas);
	model.addAttribute("page", renderizadorPaginas);
	model.addAttribute("material", material);
	model.addAttribute("index", index);
	return "pruebas";
    }
    
    @PostMapping("/addPrueba/{index}")
    public String addPrueba(Material material, @Valid Prueba prueba, BindingResult result, @RequestParam(name ="page", defaultValue = "0")int page, @PathVariable("index") int index) {
	if(result.hasErrors()) {
	    return "pruebas";
	}
	material = service.getMaterial(material.getCodigo());
	prueba.setMaterial(material);
	service.savePrueba(prueba);
	return "redirect:/pruebas/" + index;
    }
    
    @GetMapping("/deletePrueba/{index}/{pagActual}/{id}")
    public String deletePrueba(Prueba prueba,@PathVariable(name = "pagActual")int page, @PathVariable(name = "index")int index, @PathVariable("id")int id) {
	prueba = service.getPrueba(id);
	service.deletePrueba(prueba);
	page = ((MaterialServiceImpl)service).getPageNumberBySizeAfterDeleteForPruebas(PAGE_SIZE, index, page);
	return "redirect:/pruebas/"+index + "?page=" + page;
    }
    
}
