/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businessService;

import entity.HistoryDetailInfo;
import entity.HistoryInfo;
import global.IHistory;
import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fish
 */
public class HistoryProxy implements IHistory {

    private Connection con;

    public HistoryProxy() throws SQLException {
	DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	String url = "jdbc:mysql://";
	ReadSQLXml r = new ReadSQLXml();
	r.getInfo();
	url += r.getHost();
	url += "?unicode=true&characterEncoding=UTF-8&user=" + r.getUsername() + "&password=" + r.getPassword();
	con = DriverManager.getConnection(url);
    }

    @Override
    public void addTakeDrug(int historyId, int drugId, int num, String howToEat) {
	try {
	    PreparedStatement ps = con.prepareStatement("SELECT * FROM drug WHERE drugId=?");
	    ps.setInt(1, drugId);
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
		int price = rs.getInt("price") * num;
		String sql = "INSERT INTO takeDrug (historyId, drugId, quantity, howToEat, totalPrice, takeStatus) VALUES (?,?,?,?,?,'U')";
		PreparedStatement ps1 = con.prepareStatement(sql);
		ps1.setInt(1, historyId);
		ps1.setInt(2, drugId);
		ps1.setInt(3, num);
		ps1.setString(4, howToEat);
		ps1.setInt(5, price);
		ps1.executeUpdate();
		ps1.close();
	    }
	    rs.close();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void deleteTakeDrug(int historyId, int drugId) {
	try {
	    con.createStatement().executeUpdate("DELETE FROM takeDrug WHERE drugId=" + drugId);
	    PreparedStatement ps = con.prepareStatement("DELETE FROM takeDrug WHERE historyId=? and drugId=?");
	    ps.setInt(1, historyId);
	    ps.setInt(2, drugId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

//    @Override
//    public void confirmTakeDrug(int historyId, int drugId) {
//	try {
//	    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM takeDrug WHERE historyId = ? and drugId=?");
//	    ps1.setInt(1, historyId);
//	    ps1.setInt(2, drugId);
//	    ResultSet rs = ps1.executeQuery();
//	    while (rs.next()) {
//		int q = rs.getInt("quantity");
//		int dId = rs.getInt("drugId");
//		PreparedStatement ps2 = con.prepareStatement("UPDATE drug SET remain=remain-? WHERE drugId=?");
//		ps2.setInt(1, q);
//		ps2.setInt(2, dId);
//		ps2.executeUpdate();
//	    }
//	    PreparedStatement ps = con.prepareStatement("UPDATE takeDrug SET takeStatus='P' WHERE historyId=? and drugId=?");
//	    ps.setInt(1, historyId);
//	    ps.setInt(2, drugId);
//	    ps.executeUpdate();
//	    ps.close();
//	    rs.close();
//	    ps1.close();
//	} catch (SQLException ex) {
//	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
//	}
//    }
    @Override
    public void confirmTakeDrug(int historyId) {
	//	try {
	//	    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM takeDrug WHERE historyId = ?");
	//	    ps1.setInt(1, historyId);
	//	    ResultSet rs = ps1.executeQuery();
	//	    while (rs.next()) {
	//		int q = rs.getInt("quantity");
	//		int dId = rs.getInt("drugId");
	//		PreparedStatement ps2 = con.prepareStatement("UPDATE drug SET remain=remain-? WHERE drugId=?");
	//		ps2.setInt(1, q);
	//		ps2.setInt(2, dId);
	//		ps2.executeUpdate();
	//	    }
	//	    PreparedStatement ps = con.prepareStatement("UPDATE takeDrug SET takeStatus='P' WHERE historyId=?");
	//	    ps.setInt(1, historyId);
	//	    ps.executeUpdate();
	//	    ps.close();
	//	    rs.close();
	//	    ps1.close();
	//	} catch (SQLException ex) {
	//	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	//	}
	try {
	    PreparedStatement ps1 = con.prepareCall("call comfirmTakeDrug(?)");
	    ps1.setInt(1, historyId);
	    ps1.executeUpdate();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    @Override
    public void cancelTakeDrug(int historyId, int drugId) {
	try {
	    PreparedStatement ps = con.prepareStatement("UPDATE takeDrug SET takeStatus='D' WHERE historyId=? and drugId=?");
	    ps.setInt(1, historyId);
	    ps.setInt(2, drugId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public HistoryDetailInfo nextPatient(int doctorId) {
	HistoryDetailInfo result = null;
	try {
	    PreparedStatement ps0 = con.prepareStatement("SELECT departmentName FROM doctor WHERE doctorId=?");
	    ps0.setInt(1, doctorId);
	    ResultSet st0 = ps0.executeQuery();
	    if (st0.next()) {
		String departmentName = st0.getString("departmentName");
		System.out.println(departmentName);
		PreparedStatement ps = con.prepareStatement("SELECT * FROM history WHERE HistoryStatus='W' and departmentName=? ORDER BY time");
		ps.setString(1, departmentName);
		ResultSet st = ps.executeQuery();
		if (st.next()) {
		    //System.out.println("ll");
		    int historyId = st.getInt("historyId");
		    PreparedStatement ps1 = con.prepareStatement("UPDATE history SET HistoryStatus='S', doctorId=? WHERE historyId=?");
		    ps1.setInt(1, doctorId);
		    ps1.setInt(2, historyId);
		    ps1.executeUpdate();
		    ps1.close();
		    result = getDetailInfo(historyId);
		}
		st.close();
		ps.close();
	    }
	    st0.close();
	    ps0.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
	return result;
    }

    @Override
    public void editHistory(int historyId, String diseaseInfo) {
	try {
	    Date date = new Date();
	    Timestamp st = new Timestamp(date.getTime());
	    PreparedStatement ps = con.prepareStatement("UPDATE history SET diseaseInfo=?, time=?, HistoryStatus='U' WHERE historyId=?");
	    ps.setString(1, diseaseInfo);
	    ps.setTimestamp(2, st);
	    ps.setInt(3, historyId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void getPaid(int historyId) {
	try {
	    PreparedStatement ps = con.prepareStatement("UPDATE history SET HistoryStatus='P' WHERE historyId=?");
	    ps.setInt(1, historyId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void cancelHistory(int historyId) {
	try {
	    PreparedStatement ps = con.prepareStatement("UPDATE history SET HistoryStatus='D' WHERE historyId=?");
	    ps.setInt(1, historyId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void completeHistory(int historyId) {
	try {
	    PreparedStatement ps = con.prepareStatement("UPDATE history SET HistoryStatus='C' WHERE historyId=?");
	    ps.setInt(1, historyId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public HistoryInfo getInfo(int historyId) {
	HistoryInfo result = null;
	try {
	    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM history WHERE historyId=?");
	    ps1.setInt(1, historyId);
	    ResultSet rs1 = ps1.executeQuery();

	    PreparedStatement ps2 = con.prepareStatement("SELECT * FROM takeDrug natural join drug WHERE historyId=?");
	    ps2.setInt(1, historyId);
	    ResultSet rs2 = ps2.executeQuery();

	    if (rs1.next()) {
		String departmentName = rs1.getString("departmentName");
		int doctorId = rs1.getInt("doctorId");
		String patientId = rs1.getString("patientId");
		String diseaseInfo = rs1.getString("diseaseInfo");
		Date time = rs1.getDate("time");
		char status = (char) rs1.getString("HistoryStatus").toCharArray()[0];

		result = new HistoryInfo(historyId, departmentName, doctorId, patientId, diseaseInfo, time, status);

		while (rs2.next()) {
		    int drugId = rs2.getInt("drugId");
		    String drugName = rs2.getString("drugName");
		    String drugIntro = rs2.getString("drugIntro");
		    String manufacturer = rs2.getString("manufacturer");
		    int num = rs2.getInt("quantity");
		    String howToEat = rs2.getString("howToEat");
		    char takeStatus = rs2.getString("takeStatus").charAt(0);
		    int price = rs2.getInt("totalPrice");
		    result.addDrugs(drugId, drugName, drugIntro, manufacturer, num, howToEat, takeStatus, price);
		}
	    }
	    rs1.close();
	    rs2.close();
	    ps1.close();
	    ps2.close();

	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
	return result;
    }

    @Override
    public void confirmHistroy(int historyId) {
	try {
	    PreparedStatement ps = con.prepareStatement("UPDATE history SET HistoryStatus='P' WHERE historyId=?");
	    ps.setInt(1, historyId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public HistoryDetailInfo getDetailInfo(int historyId) {
	HistoryDetailInfo result = null;
	try {
	    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM history natural join patient WHERE historyId=?");
	    ps1.setInt(1, historyId);
	    ResultSet rs1 = ps1.executeQuery();

	    PreparedStatement ps2 = con.prepareStatement("SELECT * FROM takeDrug natural join drug WHERE historyId=?");
	    ps2.setInt(1, historyId);
	    ResultSet rs2 = ps2.executeQuery();



	    if (rs1.next()) {
		String departmentName = rs1.getString("departmentName");
		int doctorId = rs1.getInt("doctorId");
		String patientId = rs1.getString("patientId");
		String diseaseInfo = rs1.getString("diseaseInfo");
		Date time = rs1.getDate("time");
		char status = (char) rs1.getString("HistoryStatus").toCharArray()[0];

		PreparedStatement ps3 = con.prepareStatement("SELECT historyId FROM history WHERE patientId=?");
		ps3.setString(1, patientId);
		ResultSet rs3 = ps3.executeQuery();

		String patientName = rs1.getString("patientName");
		boolean gender = rs1.getBoolean("gender");
		short age = rs1.getShort("age");

		result = new HistoryDetailInfo(historyId, departmentName, doctorId, patientId, diseaseInfo, time, status, patientName, gender, age);
		while (rs3.next()) {
		    result.addHistory(rs3.getInt("historyId"));
		}
		while (rs2.next()) {
		    int drugId = rs2.getInt("drugId");
		    String drugName = rs2.getString("drugName");
		    String drugIntro = rs2.getString("drugIntro");
		    String manufacturer = rs2.getString("manufacturer");
		    int num = rs2.getInt("quantity");
		    String howToEat = rs2.getString("howToEat");
		    char takeStatus = rs2.getString("takeStatus").charAt(0);
		    int price = rs2.getInt("totalPrice");
		    result.addDrugs(drugId, drugName, drugIntro, manufacturer, num, howToEat, takeStatus, price);
		}
	    }
	    rs1.close();
	    rs2.close();
	    ps1.close();
	    ps2.close();

	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
	return result;
    }

    @Override
    public void notCompleteHistory(int historyId) {
//	try {
//	    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM takeDrug WHERE historyId = ?");
//	    ps1.setInt(1, historyId);
//	    ResultSet rs = ps1.executeQuery();
//	    while (rs.next()) {
//		int q = rs.getInt("quantity");
//		int dId = rs.getInt("drugId");
//		PreparedStatement ps2 = con.prepareStatement("UPDATE drug SET remain=remain+? WHERE drugId=?");
//		ps2.setInt(1, q);
//		ps2.setInt(2, dId);
//		ps2.executeUpdate();
//	    }
//	    PreparedStatement ps = con.prepareStatement("UPDATE takeDrug SET takeStatus='D' WHERE historyId=?");
//	    ps.setInt(1, historyId);
//	    ps.executeUpdate();
//	    PreparedStatement ps3 = con.prepareStatement("Update history SET HistoryStatus='D' WHERE historyId=?");
//	    ps3.setInt(1, historyId);
//	    ps3.executeUpdate();
//	    ps3.close();
//	    ps.close();
//	    rs.close();
//	    ps1.close();
//	} catch (SQLException ex) {
//	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
//	}
	
	try {
	    PreparedStatement ps1 = con.prepareStatement("call notCompleteHistory(?)");
	    ps1.setInt(1, historyId);
	    ps1.executeUpdate();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void directAddHistory(int historyId, String department, int doctorId, String patientId, String info, Date time) {
	try {
	    Timestamp st = new Timestamp(time.getTime());
	    PreparedStatement ps = con.prepareStatement("INSERT INTO history (departmentName, doctorId, patientId, diseaseInfo, time, HistoryStatus, historyId) VALUES (?,?,?,?,?,'C',?)");
	    ps.setString(1, department);
	    ps.setInt(2, doctorId);
	    ps.setString(3, patientId);
	    ps.setString(4, info);
	    ps.setTimestamp(5, st);
	    ps.setInt(6, historyId);
	    ps.executeUpdate();
	    ps.close();
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void directRemoveHistory(int historyId) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void directAddDrug(int historyId, int drugId, int num, String how) {
	try {
	    PreparedStatement ps0 = con.prepareStatement("SELECT price FROM drug WHERE drugId=?");
	    ps0.setInt(1, drugId);
	    ResultSet rs = ps0.executeQuery();
	    if (rs.next()) {
		int price = rs.getInt("price");
		PreparedStatement ps = con.prepareStatement("INSERT INTO takeDrug(historyId, drugId, quantity, howToEat, takeStatus, totalPrice) VALUES (?,?,?,?,'C',?)");
		ps.setInt(1, historyId);
		ps.setInt(2, drugId);
		ps.setInt(3, num);
		ps.setString(4, how);
		ps.setInt(5, num * price);
		ps.executeUpdate();
		ps.close();
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(HistoryProxy.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void directRemoveDrug(int historyId, int drugId) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
}
