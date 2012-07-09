/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import entity.HistoryDetailInfo;
import entity.HistoryInfo;
import java.util.Date;

/**
 *
 * @author fish
 */
public interface IHistory {

    void addTakeDrug(int historyId, int drugId, int num, String howToEat);

    void deleteTakeDrug(int historyId, int drugId);

    //void confirmTakeDrug(int historyId, int drugId);
    
    void confirmTakeDrug(int historyId);

    void cancelTakeDrug(int historyId, int drugId);

    HistoryDetailInfo nextPatient(int doctorId);

    void editHistory(int historyId, String diseaseInfo);

    void getPaid(int historyId);

    void cancelHistory(int historyId);
    
    void confirmHistroy(int historyId);

    void completeHistory(int historyId);
    
    void notCompleteHistory(int historyId);

    HistoryInfo getInfo(int historyId);
    
    HistoryDetailInfo getDetailInfo(int historyId);
    
    void directAddHistory(int historyId, String department, int doctorId, String patientId, String info, Date time);
    
    void directRemoveHistory(int historyId);
    
    void directAddDrug(int historyId, int drugId, int num, String how);
    
    void directRemoveDrug(int historyId, int drugId);
}
