/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class DeleteDrug extends EntityTable{
    
    private int drugId;
    
    public DeleteDrug(int drugId){
	setType("DeleteDrug");
	this.drugId = drugId;
    }
    
    public int getDrugId(){
	return drugId;
    }
}
