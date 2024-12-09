package com.grammer.code.util;

import com.grammer.code.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
 * Clase que se encarga de configurar y manejar la conexi�n a la base de datos en la aplicaci�n Java. 
 * Hace que sea f�cil abrir y cerrar conexiones a la base de datos cuando necesitas guardar, actualizar o buscar datos, 
 * sin tener que preocuparte por los detalles t�cnicos de c�mo se hace. Esta clase prepara todo 
 * para trabajar con la base de datos de forma m�s sencilla y eficiente.
 */

public class HibernateUtil {

    public static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernateDB_SCRIPTS.cfg.xml")
                    .addAnnotatedClass(ApprovalEntity.class)
                    .addAnnotatedClass(Areas.class)
                    .addAnnotatedClass(Computadora.class)
                    //.addAnnotatedClass(Kaizen.class)
                    //.addAnnotatedClass(Matriz.class)
                    //.addAnnotatedClass(MaterialCostumer.class)
                    .addAnnotatedClass(MPS.class)
                    .addAnnotatedClass(Platform.class)
                    //.addAnnotatedClass(SAP.class)
                    .addAnnotatedClass(ScriptEntity.class)
                    .addAnnotatedClass(ScriptsCarton.class)
                    .addAnnotatedClass(UserEntity.class)
                    .addAnnotatedClass(Versionador.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }

    }

    public static Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


}
