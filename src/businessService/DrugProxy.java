/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businessService;

import entity.DrugInfo;
import global.IDrug;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fish
 */
public class DrugProxy implements IDrug {

    private Connection con;

    public DrugProxy() throws SQLException {
	DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	String url = "jdbc:mysql://";
	ReadSQLXml r = new ReadSQLXml();
	r.getInfo();
	url += r.getHost();
	url += "?unicode=true&characterEncoding=UTF-8&user=" + r.getUsername() + "&password=" + r.getPassword();
	con = DriverManager.getConnection(url);
    }

    @Override
    public void newDrug(String drugName, String drugIntro, int num, String manufacturer, int price) {
	try {
	    String sql = "INSERT INTO drug (drugName, drugIntro, remain, manufacturer,price) VALUES (?,?,?,?,?)";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1, drugName);
	    ps.setString(2, drugIntro);
	    ps.setInt(3, num);
	    ps.setString(4, manufacturer);
	    ps.setInt(5, price);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(DrugProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void editDrug(int drugId, String drugName, String drugIntro, int num, String manufacturer, int price) {
	try {
	    PreparedStatement ps = con.prepareStatement("UPDATE drug SET drugName=?, drugIntro=?, remain=?, manufacturer=?, price=? WHERE drugId=?");
	    ps.setString(1, drugName);
	    ps.setString(2, drugIntro);
	    ps.setInt(3, num);
	    ps.setString(4, manufacturer);
	    ps.setInt(5, price);
	    ps.setInt(6, drugId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(DrugProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void deleteDrug(int drugId) {
	try {
	    PreparedStatement ps = con.prepareStatement("DELETE FROM drug WHERE drugId=?");
	    ps.setInt(1, drugId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(DrugProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public List<DrugInfo> searchDrug(String keyword) {
	List<DrugInfo> result = new ArrayList<>();
	try {
	    PreparedStatement ps = con.prepareStatement("SELECT * FROM drug WHERE drugName like ? or drugIntro like ? or manufacturer like ?");
	    ps.setString(1, "%" + keyword + "%");
	    ps.setString(2, "%" + keyword + "%");
	    ps.setString(3, "%" + keyword + "%");
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
		int drugId = rs.getInt("drugId");
		String drugName = rs.getString("drugName");
		String drugIntro = rs.getString("drugIntro");
		int remain = rs.getInt("remain");
		String manufacturer = rs.getString("manufacturer");
		int price = rs.getInt("price");
		result.add(new DrugInfo(drugId, drugName, drugIntro, remain, manufacturer, price));
	    }
	    rs.close();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(DrugProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
	return result;
    }

    @Override
    public List<DrugInfo> searchDrugName(String keyword) {
	List<DrugInfo> result = new ArrayList<>();
	try {
	    PreparedStatement ps = con.prepareStatement("SELECT * FROM drug WHERE drugName like ?");
	    ps.setString(1, keyword + "%");
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
		int drugId = rs.getInt("drugId");
		String drugName = rs.getString("drugName");
		String drugIntro = rs.getString("drugIntro");
		int remain = rs.getInt("remain");
		String manufacturer = rs.getString("manufacturer");
		int price = rs.getInt("price");
		result.add(new DrugInfo(drugId, drugName, drugIntro, remain, manufacturer, price));
	    }
	    rs.close();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(DrugProxy.class.getName()).log(Level.SEVERE, null, ex);
	}

	return result;
    }

    @Override
    public void addDrug(int drugId, int num) {
	try {
	    PreparedStatement ps = con.prepareStatement("UPDATE drug SET remain=remain+? WHERE drugId=?");
	    ps.setInt(1, num);
	    ps.setInt(2, drugId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(DrugProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void takeDrug(int drugId, int num) {
	try {
	    PreparedStatement ps = con.prepareStatement("UPDATE drug SET remain=remain-? WHERE drugId=?");
	    ps.setInt(1, num);
	    ps.setInt(2, drugId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(DrugProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public static void main(String arg[]) throws SQLException {
//	PatientProxy pp = new PatientProxy();
//	HistoryProxy hp = new HistoryProxy();
//	for (int i = 9445; i < 15000; i++) {
//	    boolean a = false;
//	    if (i % 2 == 0) {
//		a = true;
//	    }
//	    short age = (short) (i % 100);
//	    pp.createPatient("" + i, "patient" + i, a, age);
//	    for (int j = 0; j < 10; j++) {
//		hp.directAddHistory(i * 10 + j, "department" + j, j, "" + i, "you will died very soon", new java.util.Date());
//		hp.directAddDrug(j + i * 10, j + 160, j, "just eat it");
//	    }
//	}
	
	DrugProxy dp = new DrugProxy();
	for(int i = 100000; i < 200000; i++){
	    dp.newDrug(i + "dr " + i + "ug" + i, i + "drug is " + i, i % 1000, "No." + i +" maker", i % 99);
	}
    }
}
