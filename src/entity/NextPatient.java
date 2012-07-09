/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class NextPatient extends EntityTable{
    private int doctorId;
    
    public NextPatient(int doctorId){
	setType("NextPatient");
	this.doctorId = doctorId;
    }
    
    public int getDoctorId(){
	return doctorId;
    }
    
}
