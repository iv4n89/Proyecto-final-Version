package com.oreja.util;

import lombok.Data;

@Data
public class ElementosPagina {
    
    private int numero;
    private boolean actual;
    /**
     * @param numero
     * @param actual
     */
    public ElementosPagina(int numero, boolean actual) {
	super();
	this.numero = numero;
	this.actual = actual;
    }
    /**
     * 
     */
    public ElementosPagina() {
	super();
    }
    
    
}
