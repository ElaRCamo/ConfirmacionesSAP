package com.grammer.code.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.grammer.code.entity.ScriptEntity;

public class ScriptDao {
	
	public List<ScriptEntity>getScript(){
		SessionFactory factory = new Configuration()
				.configure("hibernateDB_SCRIPTS.cfg.xml")
				.addAnnotatedClass(ScriptEntity.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		List<ScriptEntity>scripts = new ArrayList <ScriptEntity>();
		
		try {
			
			session.beginTransaction();
			String sql = " from ScriptEntity a where a.id like 'C560430B-FCF7-4D8C-B546-C38029A1902E'";
			scripts = session.createQuery(sql, ScriptEntity.class).getResultList();
			session.flush();
			session.getTransaction().commit();
			
			if(null != session && session.isOpen()) {
				session.close();
			}
			
			if(null != factory && factory.isOpen()) {
				factory.close();
			}
			
		}catch(HibernateException he) {
			
			if(null != session && session.isOpen()) {
				session.close();
			}
			if(null != factory && factory.isOpen()) {
				factory.close();
			}
			he.printStackTrace();
			
		}catch(Exception e) {
			
			if(null != session && session.isOpen()) {
				session.close();
			}
			
			if(null != factory && factory.isOpen()) {
				factory.close();
			}
			
			e.printStackTrace();
			
		}finally {
			
		}
		
		return scripts;
	}
	
	
	public boolean updateScriptU(ScriptEntity scriptEntity) {
		SessionFactory factory = null;
		Session session = null;
		
		try {
			factory = new Configuration()
					.configure("hibernateDB_SCRIPTS.cfg.xml")
					.addAnnotatedClass(ScriptEntity.class)
					.buildSessionFactory();
			session = factory.getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(scriptEntity);
			session.flush();
			session.getTransaction().commit();
			
		}catch(HibernateException he) {
			
			if(null != session.getTransaction()) {
				session.getTransaction().rollback();
			}
			if(null != factory && factory.isOpen()) {
				factory.close();
			}
			
			he.printStackTrace();
			
		}catch(Exception e) {
			
			if(null != session.getTransaction()) {
				session.getTransaction().rollback();
			}
			
			if(null != session && session.isOpen()) {
				session.close();
			}
			
			if(null != factory && factory.isOpen()) {
				factory.close();
			}
			
			e.printStackTrace();
			return false;
		
		}finally {
			
		}
		
		return true;
	}
}