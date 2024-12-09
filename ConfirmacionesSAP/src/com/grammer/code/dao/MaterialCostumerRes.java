package com.grammer.code.dao;

import com.grammer.code.util.BaseDatos;

public class MaterialCostumerRes extends BaseDatos {
	
	
    public Object consultarNumero(String numeroMaterial) {
    	Object result = null;
        try {
            abrirSession();
            String sql = "SELECT MATERIAL FROM NUMERO_CLIENTE WHERE CUSTOMER_MATERIAL = :numeroMaterial";
            result = session.createNativeQuery(sql).setParameter("numeroMaterial", numeroMaterial).uniqueResult();
            cerrarSesion();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--> class MaterialCostumerRes - consultarNumero(String numeroMaterial) | respuesta = " + result);
        return result;
    }
}
