/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class AddRemain extends EntityTable{
    
    private int drugId;
    private int num;
    
    public AddRemain(int drugId, int num){
	setType("AddRemain");
	this.drugId = drugId;
	this.num = num;
    }
    
    public int getDrugId(){
	return drugId;
    }
    
    public int getNum(){
	return num;
    }
    
}
