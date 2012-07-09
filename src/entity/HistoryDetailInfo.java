/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author fish
 */
public class HistoryDetailInfo extends EntityTable implements Serializable{
     private List<TakeDrugInfo> drugs;
    private int historyId;
    private String departmentName;
    private int doctorId;
    private String patientId;
    private String diseaseInfo;
    private Date time;
    private int price;
    private char status;
    private String patientName;
    private boolean gender;
    private short age;
    
    private List<Integer> historyList;

    public HistoryDetailInfo(int historyId, String departmentName, int doctorId, String patientId, String diseaseInfo, Date time, char status, String patientName, boolean gender, short age) {
	setType("HistoryDetailInfo");
	price = 0;
	drugs = new ArrayList<TakeDrugInfo>();
	this.historyId = historyId;
	this.departmentName = departmentName;
	this.doctorId = doctorId;
	this.patientId = patientId;
	this.diseaseInfo = diseaseInfo;
	this.time = time;
	this.historyList = new ArrayList<>();
	
	this.patientName = patientName;
	this.age = age;
	this.gender = gender;
	
	//W for wait; D for delete; C for complete; P for paid; U for unpaid ; S for have seen;
	this.status = status;
    }

    public List<TakeDrugInfo> getDrugs() {
	return drugs;
    }

    public void addDrugs(int drugId, String drugName, String drugIntro, String manufacturer, int num, String howToEat, char status, int price) {
	drugs.add(new TakeDrugInfo(this.historyId, drugId, drugName, drugIntro, manufacturer, num, howToEat, status, price));
	this.price += price;
    }
    public void removeDrug(int drugId){
	for(int i = 0; i < drugs.size(); i++){
	    if(drugs.get(i).getDrugId() == drugId){
		drugs.remove(i);
		break;
	    }
	}
    }
    
    public void addHistory(int input){
	historyList.add(input);
    }

    public int getHistoryId() {
	return historyId;
    }

    public String getDepartmentName() {
	return departmentName;
    }

    public int getDoctorId() {
	return doctorId;
    }


    public String getPatientId() {
	return patientId;
    }

    public String getDiseaseInfo() {
	return diseaseInfo;
    }

    public Date getTime() {
	return time;
    }

    public char getStatus() {
	return status;
    }
    
    public int getPrice(){
	return price;
    }
    
    public List<Integer> getHistoryList(){
	return historyList;
    }
    
    public String getPatientName(){
	return patientName;
    }
    
    public boolean getGender(){
	return gender;
    }
    
    public short getAge(){
	return age;
    }
    
    public void setDiseaseInfo(String input){
	diseaseInfo = input;
    }
    
    
}
