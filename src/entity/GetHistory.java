/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class GetHistory extends EntityTable{
    private int historyId;
    
    public GetHistory(int historyId){
	setType("GetHistory");
	this.historyId = historyId;
    }
    
    public int getHistoryId(){
	return historyId;
    }
    
}
