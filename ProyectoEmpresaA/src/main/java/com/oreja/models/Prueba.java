package com.oreja.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.google.gson.annotations.Expose;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Prueba implements Serializable{
    private static final long serialVersionUID = 1L;
    @Expose
    int id;
    @Expose
    @DateTimeFormat(pattern =  "yyyy-MM-dd")
    @NotNull
    private Date fecha;
    @Expose
    @NotEmpty
    private String comentario;
    @Expose
    private Material material;   
}
