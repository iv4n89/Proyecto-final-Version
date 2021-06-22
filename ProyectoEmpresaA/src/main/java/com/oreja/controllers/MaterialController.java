package com.oreja.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oreja.models.Material;
import com.oreja.service.MaterialServiceImpl;
import com.oreja.util.RenderizadorPaginas;

@Controller
@RequestMapping("/material")
public class MaterialController {
    @Value("${err}")
    private String errorMessage;
    @Value("${err.nombre}")
    private String errorMessageNombre;
    
    @Autowired
    private MaterialServiceImpl service;
    
    private final int PAGE_SIZE = 10;
    
    @GetMapping
    public String page(Model model, @RequestParam(name = "page", defaultValue = "0")int  page) {
	Page<Material> pages = service.getPageOfMaterial(page, "codigo");
	RenderizadorPaginas<Material> materiales = new RenderizadorPaginas<Material>("/material", pages);
	model.addAttribute("page", materiales);
	return "index";
    }
    
    @GetMapping("/byResistencia")
    public String sortedByResistencia(Model model, @RequestParam(name = "page", defaultValue = "0")int page, @RequestParam(name = "res", required = false)Double resistencia) {
	if(resistencia == null) {
	    return "redirect:/material";
	}
	Page<Material> pages = service.getPageOfMaterialByResistenciaLessThan(page, resistencia);
	RenderizadorPaginas<Material> materiales = new RenderizadorPaginas<Material>("/material/byResistencia?res=" + resistencia, pages);
	model.addAttribute("page", materiales);
	model.addAttribute("res", resistencia);
	return "index";
    }
    
    @GetMapping("/add")
    public String addForm( Material material) {
	return "addMaterial";
    }
    
    @PostMapping(value = "/add", produces = "application/json")
    public String addMaterial(@Valid Material material, BindingResult result, Model model) {
	if(result.hasErrors()) {
	    return "addMaterial";
	}
	Material created =  service.saveMaterial(material);
	if(created == null) {
	    model.addAttribute("materialError", errorMessage);
	    return "addMaterial";
	}
	return "redirect:/material";
    }
    
    @GetMapping(value = "/delete/{codigo}/{pagActual}")
    public String deleteMaterial(@PathVariable(name = "codigo")int codigo, @PathVariable(name = "pagActual")int page) {
	service.deleteMaterial(codigo);
	page = ((MaterialServiceImpl)service).getPageNumberBySizeAfterDeleteForMaterial(PAGE_SIZE, page);
	return "redirect:/material?page=" + page;
    }

    @GetMapping("/update/{codigo}")
    public String updateMaterial(@PathVariable int codigo, Model model) {
	Material material = service.getMaterial(codigo);
	model.addAttribute("material", material);
	return "updateMaterial";
    }
    
    @PostMapping(path = "/update", produces = "application/json")
    public String getMaterialUpdated(Material material, Model model) {
	Material updated = service.updateMaterial(material);
	if(updated == null) {
	    model.addAttribute("materialError", errorMessageNombre);
	    return "updateMaterial";
	}
	return "redirect:/material";
    }

}
