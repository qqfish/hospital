/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

/**
 *
 * @author fish
 */
public interface IPatient {

    void createPatient(String patientId, String patientName, boolean gender, short age);

    void editPatient(String patientId, String patientName, boolean gender, short age);

    void newHistory(String patientId, String departmentName);

    boolean hasPatient(String patientId);
}
