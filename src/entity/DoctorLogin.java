/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class DoctorLogin extends EntityTable{
    private int doctorId;
    
    public DoctorLogin(int doctorId){
	setType("DoctorLogin");
	this.doctorId = doctorId;
    }
    
    public int getDoctorId(){
	return doctorId;
    }
}
