/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businessService;

import global.IPatient;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fish
 */
public class PatientProxy implements IPatient {

    private Connection con;

    public PatientProxy() throws SQLException {
	DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	String url = "jdbc:mysql://";
	ReadSQLXml r = new ReadSQLXml();
	r.getInfo();
	url += r.getHost();
	url += "?unicode=true&characterEncoding=UTF-8&user=" + r.getUsername() + "&password=" + r.getPassword();
	con = DriverManager.getConnection(url);
    }

    @Override
    public void createPatient(String patientId, String patientName, boolean gender, short age) {
	try {
	    String sql = "INSERT INTO patient (patientId, patientName, gender, age) VALUES (?,?,?,?)";
	    PreparedStatement ps =con.prepareStatement(sql);
	    ps.setString(1, patientId);
	    ps.setString(2, patientName);
	    ps.setBoolean(3, gender);
	    ps.setShort(4, age);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(PatientProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void editPatient(String patientId, String patientName, boolean gender, short age) {
	try {
	    String sql = "UPDATE patient SET patientName=?, gender=?, age=? WHERE patientId=?";
	    PreparedStatement ps =con.prepareStatement(sql);
	    ps.setString(1, patientName);
	    ps.setBoolean(2, gender);
	    ps.setShort(3, age);
	    ps.setString(4, patientId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(PatientProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void newHistory(String patientId, String departmentName) {
	try {
	    PreparedStatement ps =con.prepareStatement("INSERT INTO history (patientId, departmentName) VALUES(?,?)");
	    ps.setString(1, patientId);
	    ps.setString(2, departmentName);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(PatientProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public boolean hasPatient(String patientId) {
	try {
	    PreparedStatement ps =con.prepareStatement("SELECT * FROM patient WHERE patientId=?");
	    ps.setString(1, patientId);
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()){
		ps.close();
		rs.close();
		return true;
	    }
	    ps.close();
	    rs.close();
	} catch (SQLException ex) {
	    Logger.getLogger(PatientProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
	return false;
    }
}
