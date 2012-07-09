/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businessService;

import global.IHospital;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fish
 */
public class HospitalProxy implements IHospital{
	
private Connection con;

    public HospitalProxy() throws SQLException {
	DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	String url = "jdbc:mysql://";
	ReadSQLXml r = new ReadSQLXml();
	r.getInfo();
	url += r.getHost();
	url += "?unicode=true&characterEncoding=UTF-8&user=" + r.getUsername() + "&password=" + r.getPassword();
	con = DriverManager.getConnection(url);
    }
    
    @Override
    public boolean loginDoctor(int doctorId) {
	try {
	    PreparedStatement ps =con.prepareStatement("SELECT * FROM doctor WHERE doctorId=?");
	    ps.setInt(1, doctorId);
	    ResultSet rs = ps.executeQuery();
	    if(rs.next()){
		if(rs.getBoolean("isAvailable")){
		    return false;
		}
		PreparedStatement ps1 =con.prepareStatement("UPDATE doctor SET isAvailable=1 WHERE doctorId=?");
		ps1.setInt(1, doctorId);
		ps1.executeUpdate();
		ps1.close();
		ps.close();
		return true;
	    }
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HospitalProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
	return false;
    }

    @Override
    public List<String> getAvailableDepartment() {
	List<String> result = new ArrayList<>();
	try {
	    ResultSet rs = con.createStatement().executeQuery("SELECT departmentName FROM doctor WHERE isAvailable=true");
	    while(rs.next()){
		result.add(rs.getString("departmentName"));
	    }
	    rs.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HospitalProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
	return result;
    }

    @Override
    public List<String> getAllDepartment() {
	List<String> result = new ArrayList<>();
	try {
	    ResultSet rs = con.createStatement().executeQuery("SELECT departmentName FROM doctor");
	    while(rs.next()){
		result.add(rs.getString("departmentName"));
	    }
	    rs.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HospitalProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
	return result;
    }

    @Override
    public void logoutDoctor(int doctorId) {
	try {
	    PreparedStatement ps =con.prepareStatement("UPDATE doctor SET isAvailable=0 WHERE doctorId=?");
	    ps.setInt(1, doctorId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HospitalProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void addDoctor(int doctorId, String departmentName, String doctorName, int room) {
	try {
	    PreparedStatement ps =con.prepareStatement("INSERT INTO doctor (doctorId, departmentName, doctorName, isAvailable, roomNum) VALUES (?,?,?,0,?)");
	    ps.setInt(1, doctorId);
	    ps.setString(2, departmentName);
	    ps.setString(3, doctorName);
	    ps.setInt(4, room);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HospitalProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void deleteDoctor(int doctorId) {
	try {
	    PreparedStatement ps =con.prepareStatement("DELETE FROM doctor WHERE doctorId=?");
	    ps.setInt(1, doctorId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HospitalProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void addDepartment(String department) {
	try {
	    PreparedStatement ps =con.prepareStatement("INSERT INTO department (departmentName) VALUES (?)");
	    ps.setString(1, department);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HospitalProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void deleteDepartment(String department) {
	try {
	    PreparedStatement ps =con.prepareStatement("DELETE FROM department WHERE departmentName=?");
	    ps.setString(1, department);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HospitalProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    
}
