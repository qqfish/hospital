/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class NotCompleteHistory extends EntityTable {

    private int historyId;

    public NotCompleteHistory(int historyId) {
	setType("NotCompleteHistory");
	this.historyId = historyId;
    }

    public int getHistoryId() {
	return historyId;
    }
}
