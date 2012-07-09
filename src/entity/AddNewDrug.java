/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class AddNewDrug extends EntityTable {

    private String drugName;
    private String drugInfo;
    private String manufacturer;
    private int price;
    private int num;

    public AddNewDrug(String drugName, String drugInfo, String manufacturer, int price, int num) {
	setType("AddNewDrug");
	this.drugName = drugName;
	this.drugInfo = drugInfo;
	this.manufacturer = manufacturer;
	this.price = price;
	this.num = num;
    }

    public String getDrugName() {
	return drugName;
    }

    public String getDrugInfo(){
	return drugInfo;
    }
    
    public String getManufacturer(){
	return manufacturer;
    }
    
    public int getPrice(){
	return price;
    }
    
    public int getNum(){
	return num;
    }
}
