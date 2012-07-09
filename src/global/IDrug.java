/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import entity.DrugInfo;
import java.util.List;

/**
 *
 * @author fish
 */
public interface IDrug {

    void newDrug(String drugName, String drugIntro, int num, String manufacturer, int price);

    void editDrug(int drugId, String drugName, String drugIntro, int num, String manufacturer, int price);

    void deleteDrug(int drugId);

    List<DrugInfo> searchDrug(String keyword);

    List<DrugInfo> searchDrugName(String keyword);

    void addDrug(int drugId, int num);

    void takeDrug(int drugId, int num);
}
