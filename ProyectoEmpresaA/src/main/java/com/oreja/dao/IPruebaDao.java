package com.oreja.dao;

import java.util.List;

import com.oreja.models.Prueba;

public interface IPruebaDao {

    void addPrueba(Prueba prueba);

    List<Prueba> getPruebasByMaterial(int codigo);

    void deletePrueba(Prueba prueba);

    void deleteAllPruebaForMaterial(int codigo);

    Prueba getPrueba(int id);

}