package com.grammer.code.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.grammer.code.entity.ApprovalEntity;
import com.grammer.code.util.BaseDatos;


public class ApprovalDao extends BaseDatos {

    // Método para obtener un registro de ApprovalEntity por ID de usuario.
    public ApprovalEntity getAllAprobacionById(String idUser) {
        ApprovalEntity aprobacion = null;

        try {
            abrirSession();
            // Define una consulta HQL para obtener un registro donde el campo numP coincida con idUser.
            String sql = "from ApprovalEntity a where a.numP like :idUser";
            aprobacion = session.createQuery(sql, ApprovalEntity.class)
                    .setParameter("idUser", idUser)  // Establece el parámetro de la consulta.
                    .setMaxResults(1)  // Limita los resultados a uno.
                    .uniqueResult();  // Obtiene un único resultado.
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarSesion();
        }
        
        System.out.println("--> class ApprovalDao - getAllAprobacionById(String idUser) | aprobacion = " + aprobacion);
        return aprobacion;  // Retorna el objeto ApprovalEntity obtenido.
    }
    
    // Método para obtener un registro de ApprovalEntity por numero de confirmacion
    public ApprovalEntity verificarNumConfirmacion(String numConfirmacion) {
        ApprovalEntity aprobacion = null;

        try {
            abrirSession();
            // Define una consulta HQL para obtener un registro donde el campo numP coincida con idUser.
            String sql = "from ApprovalEntity a where a.numConfirmacion like :numConfirmacion";
            aprobacion = session.createQuery(sql, ApprovalEntity.class)
                    .setParameter("numConfirmacion", numConfirmacion) 
                    .setMaxResults(1)  
                    .uniqueResult(); 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarSesion();
        }
        
        System.out.println("--> class ApprovalDao - verificarNumConfirmacion(String numConfirmacion) | aprobacion = " + aprobacion);
        return aprobacion; 
    }
    
    
    
    public List<ApprovalEntity> consultar(String idUser, String hostname, Date varFeC, String turno, int horas) {
 		
    	LocalDate feC = LocalDate.now();
        String varFeCS = null;
        LocalDate fechaSiguiente = feC.plusDays(1);
        LocalDate fechaAnterior = feC.minusDays(1);
        String fechaAntFor = null;
        List<ApprovalEntity> confirmacionesList = new ArrayList<>();

        String startTime = ""; // Define el horario de inicio del turno
        String endTime = "";   // Define el horario de fin del turno

        // Configura los horarios de inicio y fin en función del turno.
        switch (turno) {
            case "Tercer Turno":
                startTime = "22:30:00.000";
                endTime = "06:30:59.000";
                if (horas > 2230 && horas < 2359) {
                    varFeCS = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    fechaAntFor = fechaSiguiente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else {
                    fechaAntFor = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    varFeCS = fechaAnterior.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                break;
            case "Primer Turno":
                startTime = "06:30:00.000";
                endTime = "14:30:59.000";
                varFeCS = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                fechaAntFor = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
            case "Segundo Turno":
                startTime = "14:30:00.000";
                endTime = "22:30:59.000";
                varFeCS = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                fechaAntFor = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
        }

        try {
            abrirSession();
            // Define una consulta HQL para contar registros que coincidan con los criterios especificados.
            String sql = "from ApprovalEntity a where a.computadora = '" 
                    + hostname + "' and a.usuario = '" 
            		+ idUser + "' and a.fechaIns BETWEEN '" 
                    + varFeCS + " " + startTime + "' and '" 
                    + fechaAntFor + " " + endTime + "' " 
                    + "and a.turno = '" + turno + "' and (a.numConfirmacion is not null) ORDER BY a.fechaIns DESC ";
            
            confirmacionesList = session.createQuery(sql).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarSesion();
        }
      
 		System.out.println("--> class ApprovalDao - consultar | respuesta = " + confirmacionesList);
 		return confirmacionesList;
 	}

    // Método para crear un nuevo registro de ApprovalEntity en la base de datos.
    public void crearApproval(ApprovalEntity aprobacion) {
    	String respuesta = "";
        abrirSession();
        try {
            // Guarda el objeto ApprovalEntity en la base de datos.
            session.save(aprobacion);
            respuesta = "guardado";
            
        } catch (Exception e) {
            e.printStackTrace();
            respuesta = "Error Exception";
        } finally {
            cerrarSesion();
        }
        
        System.out.println("--> class ApprovalDao - crearApproval(ApprovalEntity aprobacion) | aprobacion = " + respuesta);
        
    }

    // Método para contar registros de ApprovalEntity según criterios específicos.
    @SuppressWarnings("unchecked")
    public List<ApprovalEntity> contLinea(String idUser, Date varFeC, String turno, int horas) {
        LocalDate feC = LocalDate.now();
        String varFeCS = null;
        LocalDate fechaSiguiente = feC.plusDays(1);
        LocalDate fechaAnterior = feC.minusDays(1);
        String fechaAntFor = null;
        List<ApprovalEntity> horasExtraList = new ArrayList<>();

        String startTime = ""; // Define el horario de inicio del turno
        String endTime = "";   // Define el horario de fin del turno

        // Configura los horarios de inicio y fin en función del turno.
        switch (turno) {
            case "Tercer Turno":
                startTime = "22:30:00.000";
                endTime = "06:30:59.000";
                if (horas > 2230 && horas < 2359) {
                    varFeCS = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    fechaAntFor = fechaSiguiente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else {
                    fechaAntFor = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    varFeCS = fechaAnterior.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                break;
            case "Primer Turno":
                startTime = "06:30:00.000";
                endTime = "14:30:59.000";
                varFeCS = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                fechaAntFor = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
            case "Segundo Turno":
                startTime = "14:30:00.000";
                endTime = "22:30:59.000";
                varFeCS = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                fechaAntFor = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
        }

        try {
            abrirSession();
            // Define una consulta HQL para contar registros que coincidan con los criterios especificados.
            String sql = "Select count(a.numP) as cantidad from ApprovalEntity a where a.numP " +
                    "= '" + idUser + "' and a.fechaIns BETWEEN '"
                    + varFeCS + " " + startTime + "' and '" + fechaAntFor + " " + endTime + "' " +
                    "and a.turno='" + turno + "' and a.numConfirmacion is not null ";
            horasExtraList = session.createQuery(sql).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarSesion();
        }
        System.out.println("--> class ApprovalDao - contLinea | horasExtraList = " + horasExtraList);
        return horasExtraList;  // Retorna la lista de resultados.
    }

    // Método para contar registros de ApprovalEntity en función del hostname, turno y horas.
    public List<Object> contLineaDos(String idUser,String hostname, String turno, int horas) {
        LocalDate feC = LocalDate.now();
        LocalDate fechaSiguiente = feC.plusDays(1);
        LocalDate fechaAnterior = feC.minusDays(1);
        String varFeCS = null;
        String fechaAntFor = null;
        List<Object> result = new ArrayList<>();

        String startTime = ""; // Define el horario de inicio del turno
        String endTime = "";   // Define el horario de fin del turno

        // Configura los horarios de inicio y fin en función del turno.
        switch (turno) {
            case "Tercer Turno":
                startTime = "22:30:00.000";
                endTime = "06:30:59.000";
                if (horas > 2230 && horas < 2359) {
                    varFeCS = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    fechaAntFor = fechaSiguiente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else {
                    fechaAntFor = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    varFeCS = fechaAnterior.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                break;
            case "Primer Turno":
                startTime = "06:30:00.000";
                endTime = "14:30:59.000";
                varFeCS = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                fechaAntFor = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
            case "Segundo Turno":
                startTime = "14:30:00.000";
                endTime = "22:30:59.000";
                varFeCS = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                fechaAntFor = feC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
        }

        try {
            abrirSession();
            // Define una consulta HQL para obtener el número de registros por computadora (hostname) que coincidan con los criterios especificados.
            String sql = "Select a.numP ,count(a.numP) as contador from ApprovalEntity a where a.computadora " +
                    "= '" + hostname + "' and a.usuario = '" 
                    		+ idUser + "' and a.fechaIns BETWEEN '"
                    + varFeCS + " " + startTime + "' and '" + fechaAntFor + " " + endTime + "' " +
                    "and a.turno='" + turno + "' and (a.numConfirmacion is not null) group by a.numP";
            List<Object[]> queryResult = session.createQuery(sql).getResultList();
            for (Object[] row : queryResult) {
                result.add(row[0]);  // Agrega el numP a los resultados.
                result.add(row[1]);  // Agrega el contador a los resultados.
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarSesion();
        }
        System.out.println("--> class ApprovalDao - contLineaDos | número de registros por computadora = " + result);
        return result;  // Retorna la lista de resultados.
    }

    // Método para obtener un registro de ApprovalEntity donde la liga coincida y la confirmación no sea nula.
    public ApprovalEntity getConfirmacion(String var4) {
        ApprovalEntity aprobacion = null;

        try {
            abrirSession();
            // Define una consulta HQL para obtener un registro donde la liga coincida con var4 y la confirmación no sea nula.
            String sql = "from ApprovalEntity a where a.liga like :var4 and a.numConfirmacion is not null";
            aprobacion = session.createQuery(sql, ApprovalEntity.class)
                    .setParameter("var4", var4)  // Establece el parámetro de la consulta.
                    .uniqueResult();  // Obtiene un único resultado.
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarSesion();
        }
        System.out.println("--> class ApprovalDao - getConfirmacion | aprobacion = " + aprobacion);
        return aprobacion;  // Retorna el objeto ApprovalEntity obtenido.
    }

    // Método para actualizar la confirmación de un registro en ApprovalEntity basado en el usuario y la liga.
    public void updateConfirmacion(String usuario, String confirmacion, String searchStr) {
    	boolean respuesta = false;

        try {
            abrirSession();
            // Define una consulta HQL para actualizar el campo numConfirmacion donde el usuario y la liga coincidan.
            String sql = "update ApprovalEntity set numConfirmacion = :confirmacion " +
                    "where liga like :searchStr and numP like :usuario";
            session.createQuery(sql)
                    .setParameter("confirmacion", confirmacion)  // Establece el parámetro para la confirmación.
                    .setParameter("searchStr", searchStr)  // Establece el parámetro para la liga.
                    .setParameter("usuario", usuario)  // Establece el parámetro para el usuario.
                    .executeUpdate();  // Ejecuta la actualización.
            respuesta = true;
        } catch (Exception e) {
            e.printStackTrace();
            respuesta = false;
        } finally {
            cerrarSesion();
        }
        
        System.out.println("--> class ApprovalDao - updateConfirmacion | respuesta = " + respuesta);
    }
    
 
}
