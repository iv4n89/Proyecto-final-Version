package com.oreja.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "codigo")
public class Material implements Serializable{

    private static final long serialVersionUID = 1L;
    @Expose
    @NotNull
    private Integer codigo;
    @NotEmpty
    private String nombre;
    @NotNull
    private Double resistencia;
    @JsonIgnore
    private List<Prueba> pruebas;
    
    public List<Prueba> getPruebas() {
	if(this.pruebas == null) {
	    return new ArrayList<>();
	}
	return this.pruebas;
    }
}
