/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class CompleteHistory extends EntityTable{
    
    private int historyId;
    
    public CompleteHistory(int historyId){
	setType("CompleteHistory");
	this.historyId = historyId;
    }
    
    public int getHistoryId(){
	return historyId;
    }
    
}
