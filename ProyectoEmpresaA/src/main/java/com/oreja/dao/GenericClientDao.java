package com.oreja.dao;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.oreja.models.Material;
import com.oreja.util.RestPageImpl;

import reactor.core.publisher.Mono;

@Component
public interface GenericClientDao<T> {

    String getSingleObjectPath();

    String getPageObjectPath(int page, String sort);
    
    String getListObjectPath();

    String getDeleteObjectPath();

    Class<T> getGenericClass();

    WebClient webClientBuilder();

    default T getObjectById(int id) {
	try {
	    Mono<T> response = webClientBuilder().get().uri(getSingleObjectPath() + id).retrieve()
		.bodyToMono(getGenericClass());
	    return response.block();
	}catch(Exception ex) {
	    return null;
	}
    }
    
    Page<T> getPage(int page, String sort);
    
    List<T> getList();

    default T addObject(T object) {
	try {
	    Mono<T> created = webClientBuilder()
		    .post()
		    .body(Mono.just(object), getGenericClass())
		    .retrieve()
		    .bodyToMono(getGenericClass());
	    return created.block();
	} catch (Exception ex) {
	    return null;
	}
    }

    default T updateObject(T object) {
	try {
	    Mono<T> updated = webClientBuilder().put().body(Mono.just(object), getGenericClass()).retrieve()
		.bodyToMono(getGenericClass());
	    return updated.block();
	}catch(Exception ex) {
	    return null;
	}
    }

    default Void deleteObject(int id) {
	Mono<Void> response = webClientBuilder().delete().uri(getDeleteObjectPath() + id).retrieve()
		.bodyToMono(Void.class);
	return response.block();
    }

}
