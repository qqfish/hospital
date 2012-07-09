/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

import java.util.List;

/**
 *
 * @author fish
 */
public interface IHospital {

    boolean loginDoctor(int doctorId);
    
    void logoutDoctor(int doctorId);

    List<String> getAvailableDepartment();

    List<String> getAllDepartment();
    
    void addDoctor(int doctorId, String departmentName, String doctorName, int room);
    
    void deleteDoctor(int doctorId);
    
    void addDepartment(String department);
    
    void deleteDepartment(String department);
}
