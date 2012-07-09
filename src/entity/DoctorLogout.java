/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class DoctorLogout extends EntityTable{
    
    private int doctorId;
    
    public DoctorLogout(int doctorId){
	setType("DoctorLogout");
	this.doctorId = doctorId; 
    }
    
    public int getDoctorId(){
	return doctorId;
    }
    
}
