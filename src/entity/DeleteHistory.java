/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class DeleteHistory extends EntityTable {

    private int historyId;

    public DeleteHistory(int historyId) {
	setType("DeleteHistory");
	this.historyId = historyId;
    }

    public int getHistoryId() {
	return historyId;
    }
}
