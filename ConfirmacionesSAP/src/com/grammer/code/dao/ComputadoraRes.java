package com.grammer.code.dao;

import com.grammer.code.util.BaseDatos;

/*
 * La clase `ComputadoraRes` gestiona el estatus de las computadoras en la base de datos. 
 * Verifica si una computadora está activa o inactiva, y realiza operaciones como insertar nuevos registros, 
 * actualizar el estatus cuando una sesión comienza o termina, y manejar errores. En esencia, 
 * maneja la creación y actualización de registros relacionados con el estado de las computadoras.
 */
public class ComputadoraRes extends BaseDatos {

    public boolean getEstatus(String computadora) {
        Object result;
        boolean respuesta = false;
        try {
            abrirSession();
            String sql = "SELECT ESTATUS FROM COMPUTADORA WHERE COMPUTADORA = :computadora";
            result = session.createNativeQuery(sql).setParameter("computadora", computadora).uniqueResult();
            cerrarSesion();
            System.out.println("--> class ComputadoraRes - getEstatus(String computadora) | result = "+result +" | computadora = "+computadora);
            if (result == null || result.equals("")) {
            	respuesta = insertComputadora(computadora);
            } else if (result.equals("A")) {
                respuesta = false;
            } else if (result.equals("I")) {
                respuesta = updateSesion(computadora);
            } else {
                respuesta = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            respuesta = false;
        }
        
        System.out.println("--> class ComputadoraRes - getEstatus(String computadora) | respuesta = "+respuesta);
        return respuesta;
    }

    public boolean insertComputadora(String computadora) {
    	boolean respuesta = false;
        try {
            abrirSession();
            String sql = "INSERT INTO Computadora(COMPUTADORA, ESTATUS, INICIO_SESION) VALUES " +
                    "(:computadora, 'A', SYSDATETIME())";
            session.createNativeQuery(sql)
                    .setParameter("computadora", computadora)
                    .executeUpdate();

            respuesta =  true;
        } catch (Exception e) {
            e.printStackTrace();
            respuesta = false;
        } finally {
            cerrarSesion();
        }
        System.out.println("--> class ComputadoraRes - insertComputadora(String computadora) | respuesta = "+respuesta);
        return respuesta;
    }

    public boolean updateSesion(String computadora) {
    	boolean respuesta = false;
        try {
            abrirSession();
            String sql = "UPDATE Computadora SET ESTATUS = 'A', INICIO_SESION = SYSDATETIME(), CIERRE_SESION = null " +
                    "WHERE COMPUTADORA = :computadora";
            session.createNativeQuery(sql)
                    .setParameter("computadora", computadora)
                    .executeUpdate();

            respuesta =  true;
        } catch (Exception e) {
            e.printStackTrace();
            respuesta =  false;
        } finally {
            cerrarSesion();
        }
        System.out.println("--> class ComputadoraRes - updateSesion(String computadora) | respuesta = "+respuesta);
        return respuesta;
    }

    public boolean updateCerrarSesion(String computadora) {
    	boolean respuesta = false;
        try {
            abrirSession();
            String sql = "UPDATE Computadora SET ESTATUS = 'I', CIERRE_SESION = SYSDATETIME(), INICIO_SESION = null " +
                    "WHERE COMPUTADORA = :computadora";
            session.createNativeQuery(sql)
                    .setParameter("computadora", computadora)
                    .executeUpdate();

            respuesta = true;
        } catch (Exception e) {
            e.printStackTrace();
            respuesta = false;
        } finally {
            cerrarSesion();
        }
        System.out.println("Class ComputadoraRes - updateCerrarSesion(String computadora) | respuesta = "+respuesta);
        return respuesta;
    }
}
