/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author fish
 */
public class TakeDrugInfo implements Serializable{

    private int historyId;
    private int drugId;
    private String drugName;
    private String drugIntro;
    private String manufacturer;
    private int quantity;
    private String howToEat;
    private int price;
    
    //U for uncomplete; C for complete; D for delete; P for paid
    private char status;

    public TakeDrugInfo(int historyId, int drugId, String drugName, String drugIntro, String manufacturer, int quantity, String howToEat, char status, int price) {
	this.historyId = historyId;
	this.drugId = drugId;
	this.drugName = drugName;
	this.drugIntro = drugIntro;
	this.manufacturer = manufacturer;
	this.quantity = quantity;
	this.howToEat = howToEat;
	this.status = status;
	this.price = price;
    }

    public int getHistoryID() {
	return historyId;
    }

    public int getDrugId() {
	return drugId;
    }

    public String getDrugName() {
	return drugName;
    }

    public String getDrugIntro() {
	return drugIntro;
    }

    public String getManufacturer() {
	return manufacturer;
    }

    public int getQuantity() {
	return quantity;
    }

    public String getHowToEat() {
	return howToEat;
    }

    public char getStatus() {
	return status;
    }
    
    public int getPrice(){
	return price;
    }
}
