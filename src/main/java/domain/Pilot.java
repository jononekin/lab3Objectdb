package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pilot {
	@Id
	public String name;
	public String nationality;
	public int points;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	

	public Pilot(String name, String naz, int x){
		this.name=name;
		this.nationality =naz;
		this.points=x;
	 }
	public String  toString(){
		return name+" "+nationality+" "+Integer.toString(points);
	}
	public void addPoints(int x){ 
		this.points= this.points+x;
	}
}

