package com.oreja.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name ="material")
public class Material implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "nombre", unique = true, length = 100, columnDefinition = "varchar(100) unique")
    private String nombre;
    @Column(name = "resistencia", length = 8, precision = 2, columnDefinition = "numeric(8, 2)")
    private Double resistencia;

}
