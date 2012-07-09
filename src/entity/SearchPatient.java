/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class SearchPatient extends EntityTable {

    private String patientId;
    private String department;

    public SearchPatient(String patientId, String department) {
	setType("SearchPatient");
	this.patientId = patientId;
	this.department = department;
    }

    public String getPatientId() {
	return patientId;
    }
    
    public String getDepartment(){
	return department;
    }
}
