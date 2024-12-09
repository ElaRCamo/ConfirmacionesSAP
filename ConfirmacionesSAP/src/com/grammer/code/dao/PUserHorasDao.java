package com.grammer.code.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.grammer.code.entity.UserEntity; //importacion del paquete para su uso

public class PUserHorasDao {
	
        //obtencion de los datos en el constructor para obtener los Usuarios
        public List<UserEntity> getAllUserHoras() {
        	System.out.println("--> class PUserHorasDao - getAllUserHoras() | lista: ");
        	
            SessionFactory factory = new Configuration()
                    .configure("hibernateDB_SCRIPTS.cfg.xml")
                    .addAnnotatedClass(UserEntity.class)
                    .buildSessionFactory();
            Session session = factory.getCurrentSession();

            // Obtención de los datos en el constructor para obtener los Usuarios
            List<UserEntity> userList = new ArrayList<>();
            try {
                session.beginTransaction();
                String sql = "from UserEntity a";
                // Creación de la lista de usuarios en el SQL
                userList = session.createQuery(sql, UserEntity.class).list();
                session.flush();
                session.getTransaction().commit(); // Realiza las instrucciones emitidas

                // Imprimir la lista de usuarios
                for (UserEntity user : userList) {
                    System.out.println(user);
                }

            } catch (HibernateException he) {
                he.printStackTrace(); // Imprime la traza del error de Hibernate
            } catch (Exception e) {
                e.printStackTrace(); // Imprime la traza de cualquier otro error
            } finally {
                // cerrar la sesión y la fábrica en el bloque finally
                if (session.isOpen()) {
                    session.close();
                }
                if (factory.isOpen()) {
                    factory.close();
                }
            }
            return userList;
        }//ultimo corchete de public list

        public void createUserHorasEntity(UserEntity userEntity) {
            
            String respuesta = "";
            SessionFactory factory = null;
            Session session = null;
            try {
                factory = new Configuration()
                        .configure("hibernateDB_SCRIPTS.cfg.xml")
                        .addAnnotatedClass(UserEntity.class)
                        .buildSessionFactory();
                session = factory.getCurrentSession();
                session.beginTransaction();
                session.save(userEntity);
                session.flush();
                session.getTransaction().commit();
                
                respuesta = "Registro creado exitosamente.";

            } catch (HibernateException he) {
                // Manejo de errores y rollback si ocurre una excepción
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
                he.printStackTrace();
                respuesta = "Error al crear el registro.";

            } finally {
                // Cierre de la sesión y la fábrica
                if (session != null && session.isOpen()) {
                    session.close();
                }
                if (factory != null && factory.isOpen()) {
                    factory.close();
                }
            }
            System.out.println("--> class PUserHorasDao - createUserHorasEntity(UserEntity userEntity) | Creación del registro: " + respuesta);
        }

    public UserEntity getAcceso(String idUsuario, String pass) {

    	SessionFactory factory = null;
        Session session = null;
        UserEntity userEntity = new UserEntity();
       
		try {
            factory = new Configuration()
                    .configure("hibernateDB_SCRIPTS.cfg.xml")
                    .addAnnotatedClass(UserEntity.class)
                    .buildSessionFactory();
            session = factory.getCurrentSession();
            session.beginTransaction();
            String sql = " from UserEntity u where u.idUsuario like'" + idUsuario + "' and u.password like '" + pass + "'";
            userEntity = session.createQuery(sql, UserEntity.class).uniqueResult();
            session.flush();
            session.getTransaction().commit();
            
            UserEntity respuesta = userEntity;
            System.out.println("--> class PUserHorasDao - getAcceso(String idUsuario, String pass) | respuesta: " + respuesta);

            if (session.isOpen()) {
                session.close();
            }

            if (factory.isOpen()) {
                factory.close();
            }

        } catch (HibernateException he) {
        	
            if (null != session && session.isOpen()) {
                session.close();
            }

            if (null != factory && factory.isOpen()) {
                factory.close();
            }

            he.printStackTrace();
            String respuesta = "Error HibernateException.";
            System.out.println("--> class PUserHorasDao - getAcceso(String idUsuario, String pass) | respuesta: " + respuesta);

        } catch (Exception e) {
            if (null != factory && factory.isOpen()) {
                factory.close();
            }

            e.printStackTrace();
            String respuesta = "Error Exception.";
            System.out.println("--> class PUserHorasDao - getAcceso(String idUsuario, String pass) | respuesta: " + respuesta);
        } finally {
        	
        }
        return userEntity;
    }

    public UserEntity getAccesoUser(String idUsuario) {

        SessionFactory factory = null;
        Session session = null;
        UserEntity operador = new UserEntity();

        try {
            factory = new Configuration()
                    .configure("hibernateDB_SCRIPTS.cfg.xml")
                    .addAnnotatedClass(UserEntity.class)
                    .buildSessionFactory();
            session = factory.getCurrentSession();
            session.beginTransaction();
            String sql = " from UserEntity u where u.idUsuario like '" + idUsuario + "'";
            operador = (UserEntity) session.createQuery(sql).uniqueResult();
            session.flush();
            session.getTransaction().commit();

            if (session.isOpen()) {
                session.close();
            }
            if (factory.isOpen()) {
                factory.close();
            }

        } catch (Exception e) {

            if (null != session && session.isOpen()) {
                session.close();
            }
            if (null != factory && factory.isOpen()) {
                factory.close();
            }
            e.printStackTrace();
        } finally {

        }
        System.out.println("--> class PUserHorasDao - getAccesoUser(String idUsuario) | respuesta: " + operador);
        return operador;
    }
}
