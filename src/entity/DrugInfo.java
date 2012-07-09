/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class DrugInfo extends EntityTable{

    private int drugId;
    private String drugName;
    private String drugIntro;
    private int remain;
    private String manufacturer;
    private int price;

    public DrugInfo(int drugId, String drugName, String drugIntro, int remain, String manufacturer, int price) {
	setType("DrugInfo");
	this.drugId = drugId;
	this.drugName = drugName;
	this.drugIntro = drugIntro;
	this.remain = remain;
	this.manufacturer = manufacturer;
	this.price = price;
    }
    
    public int getDrugId(){
	return drugId;
    }
    
    public String getDrugName(){
	return drugName;
    }
    
    public String getDrugIntro(){
	return drugIntro;
    }
    
    public int getRemain(){
	return remain;
    }
    
    public String getManufacturer(){
	return manufacturer;
    }
    
    public int getPrice(){
	return price;
    }
}
