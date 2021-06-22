package com.oreja.util;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oreja.models.Prueba;

/**
 * Clase de utilidad con Gson, de serialización y deserialización de objetos Prueba
 * @author iv4n8
 *
 */
@Component
public class JsonUtil {
	private final Gson gson;
	private final Type type = new ParameterizedTypeReference<List<Prueba>>() {}.getType();
	
	public JsonUtil () {
		this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}
	
	public String serialize(List<Prueba> pruebas) {
	    String json = gson.toJson(pruebas, type);
	    return json;
	}
	
	public List<Prueba> dererialize(String json) {
	    List<Prueba> pruebas = gson.fromJson(json, type);
	    return pruebas;
	}
	
}
