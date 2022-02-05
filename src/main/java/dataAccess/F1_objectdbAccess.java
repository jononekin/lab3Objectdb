package dataAccess;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import domain.Pilot;


	public class F1_objectdbAccess {
		private  EntityManager  db;
		private EntityManagerFactory emf;
	  	String fileName = "formula1.odb";

	  public F1_objectdbAccess() {
		  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
		  db = emf.createEntityManager();
		  System.out.println("DataBase opened");
	  }
	  //klaseko datubase metodoak
	  
	  public void storePilot(String name,String naz, int points) {
		    
		    db.getTransaction().begin();
	        Pilot pilot = new Pilot(name, naz, points);
	        db.persist(pilot);
	        db.getTransaction().commit();
	        
	        System.out.println("Gordeta " + pilot);
	}
	  
	  public List<Pilot> getPilotByNationality(String naz) {
			 //TypedQuery<Pilot> query = db.createQuery("SELECT p FROM Pilot p WHERE p.nationality='"+naz+"'",Pilot.class);   
			 TypedQuery<Pilot> query = db.createQuery("SELECT p FROM Pilot p WHERE p.nationality=?1",Pilot.class);   
			 query.setParameter(1, naz);
			 List<Pilot> pilots = query.getResultList();
			 System.out.println("Datu basearen edukia");
	 		 for (Pilot p:pilots) System.out.println(p.toString());	 
	 		 return pilots;
	 }
	  
	  public Pilot getPilotByName(String name) {
			 TypedQuery<Pilot> query = db.createQuery("SELECT p FROM Pilot p WHERE p.name='"+name+"'",Pilot.class);   
			 List<Pilot> pilots = query.getResultList();
			 if (pilots.isEmpty()) 
				 	return null;	
			   else 
		   		     return (Pilot) pilots.get(0);		  
	 }

	  public void getAllPilots() {
		  db.getTransaction().begin();
		 TypedQuery<Pilot> query = db.createQuery("SELECT p FROM Pilot p",Pilot.class);   
		 List<Pilot> pilots = query.getResultList();
		 
		 System.out.println("Datu basearen edukiaa");
 		 for (Pilot p:pilots) System.out.println(p.toString());		
 		 db.getTransaction().commit();


	  }

	 public void deletePilotByName(String name) {
		
		 db.getTransaction().begin();
		 Query query = db.createQuery("DELETE FROM Pilot p WHERE p.name='"+name+"'");   
		 int deteledPilots = query.executeUpdate();
 		 System.out.println("Deleted pilots " + deteledPilots);
 		 db.getTransaction().commit();

	  	}
	 
	 public void deletePilotByName2(String name) {
		 Pilot pilot=getPilotByName(name);
		   if (pilot==null) 
		    System.out.println(name + " it is not in the database");  
		   else {
		 db.getTransaction().begin();
		 db.remove(pilot);
 		 System.out.println("Deleted pilot " + pilot.toString());
 		 db.getTransaction().commit();
		}
	  	}
	 
	 public void updatePilotByNameAddingPoints2(String name, int points) {
		 Pilot pilot=getPilotByName(name);
		   if (pilot==null) 
		    System.out.println(name + " it is not in the database");  
		   else {
		 db.getTransaction().begin();
		 Query query = db.createQuery("UPDATE Pilot SET points = ?1 where name = ?2");
		 query.setParameter(1, pilot.points+points);
		 query.setParameter(2, name);

		 int updatedPilots = query.executeUpdate();
 		 System.out.println("Updated pilots " + updatedPilots);
 		 db.getTransaction().commit();
	  	}
	 }
	 public void updatePilotByNameAddingPoints(String name, int points) {
		 Pilot pilot=getPilotByName(name);
		   if (pilot==null) 
		    System.out.println(name + " it is not in the database");  
		   else {
   			 db.getTransaction().begin();
   			 pilot.addPoints(points);
   	 		 db.getTransaction().commit();
  		     System.out.println(pilot + " has been updated");
			}
	 }
	 


	  public void close(){
		db.close();
		System.out.println("DataBase closed");
		}
	}
