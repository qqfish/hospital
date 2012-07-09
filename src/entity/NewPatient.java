/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class NewPatient extends EntityTable{
    
    private String patientName;
    private short age;
    private boolean gender;
    
    public NewPatient(String patientName, short age, boolean gender){
	setType("NewPatient");
	this.patientName = patientName;
	this.age = age;
	this.gender = gender;
    }
    
    public String getPatientName(){
	return patientName;
    }
    
    public short getAge(){
	return age;
    }
    
    public boolean getGender(){
	return gender;
    }
    
}
