package com.grammer.code.dao;

import com.grammer.code.entity.MPS;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.ArrayList;
import java.util.List;

public class MpsRes {

	// buscar y devolver un objeto de tipo MPS desde la base de datos que coincida con el valor de la plataforma (mpsPlatform)
	//Útil cuando esperas encontrar exactamente un objeto MPS o ninguno.
	public MPS getByPlatform(String mpsPlatform) {
	    SessionFactory factory = new Configuration()
	            .configure("hibernateDB_SCRIPTS.cfg.xml") 
	            .addAnnotatedClass(MPS.class) // Añade la clase MPS como una entidad anotada
	            .buildSessionFactory(); 
	    Session session = factory.getCurrentSession();

	    // Declara un objeto MPS para almacenar el resultado de la consulta
	    MPS mps = new MPS();

	    try {
	        session.beginTransaction();
	        String sql = " from MPS a where a.mpsPlatform like '" + mpsPlatform + "'";

	        // Ejecuta la consulta y obtiene un único resultado (o null si no hay coincidencias)
	        mps = session.createQuery(sql, MPS.class).uniqueResult();

	        // Limpia la sesión actual, enviando cualquier cambio pendiente a la base de datos
	        session.flush();

	        session.getTransaction().commit();

	        if (session.isOpen()) {
	            session.close();
	        }
	        if (factory.isOpen()) {
	            factory.close();
	        }

	    } catch (HibernateException he) {
	        // Manejo de excepciones específicas de Hibernate
	        if (session.isOpen()) {
	            session.close(); 
	        }
	        if (factory.isOpen()) {
	            factory.close(); 
	        }
	        he.printStackTrace();
	    } catch (Exception e) {
	        // Manejo de excepciones generales
	        if (null != session && session.isOpen()) {
	            session.close(); 
	        }
	        if (factory.isOpen()) {
	            factory.close(); 
	        }
	        e.printStackTrace(); 
	    }
	    System.out.println("--> class MpsRes - getByPlatform(String mpsPlatform) | respuesta = " + mps);
	    // Devuelve el objeto MPS encontrado (o null si no se encontró ninguno)
	    return mps;
	}

	//Útil cuando esperas encontrar cero, uno,
	//o múltiples objetos MPS que coincidan con el criterio de búsqueda.
    public List<MPS> getByPlatformList(String mpsPlatform) {
        SessionFactory factory = new Configuration()
                .configure("hibernateDB_SCRIPTS.cfg.xml")
                .addAnnotatedClass(MPS.class)
                .buildSessionFactory();
        Session session = factory.getCurrentSession();
//        MPS kaizen = new MPS();
        List<MPS> mps = new ArrayList<MPS>();

        try {
            session.beginTransaction();
            String sql = " from MPS a where a.mpsPlatform like '" + mpsPlatform + "'";
            mps = session.createQuery(sql, MPS.class).getResultList();
            session.flush();
            session.getTransaction().commit();

            if (session.isOpen()) {
                session.close();
            }
            if (factory.isOpen()) {
                factory.close();
            }

        } catch (HibernateException he) {
            if (session.isOpen()) {
                session.close();
            }
            if (factory.isOpen()) {
                factory.close();
            }
            he.printStackTrace();
        } catch (Exception e) {
            if (null != session && session.isOpen()) {
                session.close();
            }
            if (factory.isOpen()) {
                factory.close();
            }
            e.printStackTrace();
        }
        System.out.println("--> class MpsRes - getByPlatformList(String mpsPlatform) | respuesta = " + mps);
        return mps;
    }

}
