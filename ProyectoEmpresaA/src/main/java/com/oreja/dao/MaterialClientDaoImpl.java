package com.oreja.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.oreja.models.Material;
import com.oreja.util.RestPageImpl;

import reactor.core.publisher.Mono;

@Component
public class MaterialClientDaoImpl implements GenericClientDao<Material>{

    @Autowired
    private WebClient webClient;
    
    private final String URL_BY_RESISTENCIA = "/sortResistencia/less/";
    
    @Override
    public Class<Material> getGenericClass() {
	return Material.class;
    }

    @Override
    public WebClient webClientBuilder() {
	return webClient;
    }

    @Override
    public String getSingleObjectPath() {
	return "/";
    }
    
    @Override
    public String getPageObjectPath(int page, String sort) {
	return "/page/" + page + "/" + sort;
    }
    
    @Override
    public String getListObjectPath() {
	return "/list";
    }

    @Override
    public String getDeleteObjectPath() {
	return "/";
    }
    
    public Page<Material> getPageByResistenciaLessThan(int page, double resistencia) {
	Mono<RestPageImpl<Material>> response = webClient.get()
		.uri( URL_BY_RESISTENCIA + resistencia + "?page=" + page)
		.retrieve()
		.bodyToMono(new ParameterizedTypeReference<RestPageImpl<Material>>() {});
	return response.block();
    }

    @Override
    public Page<Material> getPage(int page, String sort) {
	Mono<RestPageImpl<Material>> response = webClientBuilder().get().uri(getPageObjectPath(page, sort)) //
		.retrieve().bodyToMono(new ParameterizedTypeReference<RestPageImpl<Material>>() {
		});
	return response.block();
    }

    @Override
    public List<Material> getList() {
	Mono<List<Material>> response = webClientBuilder().get()
		.uri(getListObjectPath())
		.retrieve()
		.bodyToMono(new ParameterizedTypeReference<List<Material>>() {
		});
	return response.block();
    }

}
