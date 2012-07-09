/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import businessService.HistoryProxy;
import global.IHistory;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fish
 */
public class ConfirmCash extends EntityTable{
    
    private int historyId;
    
    public ConfirmCash(int historyId){
	setType("ConfirmCash");
	this.historyId = historyId;
    }
    
    public int getHistoryId(){
	return historyId;
    }
}
