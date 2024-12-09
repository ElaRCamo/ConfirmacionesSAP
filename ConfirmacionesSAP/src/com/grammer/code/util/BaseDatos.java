package com.grammer.code.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/*
 * La clase `BaseDatos` gestiona la apertura y cierre de conexiones a la base de datos usando Hibernate. 
 * Proporciona métodos para iniciar (`abrirSession()`) y finalizar (`cerrarSesion()`) una transacción, 
 * asegurando que los cambios se confirmen correctamente o se deshagan si ocurre un error. En esencia, 
 * facilita el manejo seguro y eficiente de las sesiones de base de datos en la aplicación.
 */
public abstract class BaseDatos {

    protected SessionFactory factory = HibernateUtil.sessionFactory;
    protected Session session;

    protected void abrirSession(){
        session = factory.getCurrentSession();
        session.beginTransaction();
    }

    protected void cerrarSesion(){
        try {
            session.flush();
            session.getTransaction().commit();
            if (null != session && session.isOpen()) {
                session.close();
            }
            if (null != factory && factory.isOpen()) {
                session.close();
            }
        } catch (HibernateException he) {
            if (null != session.getTransaction()) {
                session.getTransaction().rollback();
            }
            if (null != session && session.isOpen()) {
                session.close();
            }
            if (null != factory && factory.isOpen()) {
                factory.close();
            }
            he.printStackTrace();
        } catch (Exception e) {
            if (null != session.getTransaction()) {
                session.getTransaction().rollback();
            }
            if (null != session && session.isOpen()) {
                session.close();
            }
            if (null != factory && factory.isOpen()) {
                factory.close();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

}
