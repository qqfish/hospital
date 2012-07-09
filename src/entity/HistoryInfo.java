/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author fish
 */
public class HistoryInfo extends EntityTable {

    private List<TakeDrugInfo> drugs;
    private int historyId;
    private String departmentName;
    private int doctorId;
    private String patientId;
    private String diseaseInfo;
    private Date time;
    private int price;
    private char status;

    public HistoryInfo(int historyId, String departmentName, int doctorId, String patientId, String diseaseInfo, Date time, char status) {
	setType("HistoryInfo");
	price = 0;
	drugs = new ArrayList<TakeDrugInfo>();
	this.historyId = historyId;
	this.departmentName = departmentName;
	this.doctorId = doctorId;
	this.patientId = patientId;
	this.diseaseInfo = diseaseInfo;
	this.time = time;

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

    public int getPrice() {
	return price;
    }
}
