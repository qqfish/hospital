/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import businessService.DrugProxy;
import businessService.HistoryProxy;
import businessService.HospitalProxy;
import businessService.PatientProxy;
import entity.*;
import global.IDrug;
import global.IHistory;
import global.IHospital;
import global.IPatient;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fish
 */
public class ThreadManager {

    private List<HistoryInfo> paymentBoard;
    //private static boolean paymentUpdate;
    private List<HistoryInfo> deliverBoard;
    private List<ServerThread> registerList;
    private List<ServerThread> doctorList;
    private List<ServerThread> chemistList;
    private List<ServerThread> registerUpdateList;
    private List<ServerThread> chemistUpdateList;
    private List<ServerThread> registerDepartment;

//    private class backgroundThread extends Thread {
//
//	public backgroundThread() {
//	    start();
//	}
//
//	public void run() {
//	    paymentUpdate = true;
//	    //paymentBoard.add(new HistoryInfo(1, "adf", 12, "dafs", "123", 123, "asfd", null, 'U'));
//	    while (true) {
//		if (paymentUpdate) {
//		    for (int i = 0; i < registerUpdateList.size(); i++) {
//			System.out.println("111");
//			registerUpdateList.get(i).sendString("update");
//			for (int j = 0; j < paymentBoard.size(); j++) {
//			    System.out.println(registerUpdateList.get(i));
//			    registerUpdateList.get(i).sendObject(paymentBoard.get(j));
//			}
//			registerUpdateList.get(i).sendString("end");
//		    }
//		    paymentUpdate = false;
//		}
//	    }
//	}
//    }
    private class ServerThread extends Thread {

	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean connected;
	private boolean doctorLogin;
	private String type;

	public ServerThread(Socket s) throws IOException {
	    client = s;
	    connected = true;
	    out = new ObjectOutputStream(client.getOutputStream());
	    out.flush();
	    in = new ObjectInputStream(client.getInputStream());
	    start();
	}

	public void run() {
	    try {
		String t;
		t = (String) in.readObject();
		System.out.println("hello " + t);
		if (t.equals("register")) {
		    registerList.add(this);
		    type = "register";
		    while (connected) {
			try {
			    registerRun();
			} catch (EOFException ex) {
			    connected = false;
			}
			if (client.isClosed()) {
			    connected = false;
			}
		    }
		} else if (t.equals("doctor")) {
		    doctorList.add(this);
		    type = "doctor";
		    doctorLogin = false;
		    while (connected) {
			try {
			    doctorRun();
			} catch (EOFException ex) {
			    connected = false;
			}
			if (client.isClosed()) {
			    connected = false;
			}
		    }
		} else if (t.equals("chemist")) {
		    chemistList.add(this);
		    type = "chemist";
		    while (connected) {
			try {
			    chemistRun();
			} catch (EOFException ex) {
			    connected = false;
			}
			if (client.isClosed()) {
			    connected = false;
			}
		    }
		} else if (t.equals("registerUpdate")) {
		    registerUpdateList.add(this);
		    type = "registerUpdate";
		    sendString("update");
		    for (int j = 0; j < paymentBoard.size(); j++) {
			sendObject(paymentBoard.get(j));
		    }
		    sendString("end");
		    while (connected) {
			try {
			    EntityTable e = (EntityTable) in.readObject();
			    if (client.isClosed()) {
				connected = false;
			    }
			    if (e.getType().equals("bye")) {
				connected = false;
			    }
			} catch (EOFException e) {
			    connected = false;
			}
		    }
		} else if (t.equals("chemistUpdate")) {
		    chemistUpdateList.add(this);
		    type = "chemistUpdate";
		    sendString("update");
		    for (int j = 0; j < deliverBoard.size(); j++) {
			sendObject(deliverBoard.get(j));
		    }
		    sendString("end");
		    while (connected) {
			try {
			    EntityTable e = (EntityTable) in.readObject();
			    if (client.isClosed()) {
				connected = false;
			    }
			    if (e.getType().equals("bye")) {
				connected = false;
			    }
			} catch (EOFException ex) {
			    connected = false;
			}
		    }
		} else if (t.equals("registerDepartment")) {
		    registerDepartment.add(this);
		    type = "registerDepartment";
		    IHospital itf = new HospitalProxy();
		    EntityList el = new EntityList(itf.getAvailableDepartment());
		    out.writeObject(el);
		    out.flush();
		    while (connected) {
			try {
			    EntityTable e = (EntityTable) in.readObject();
			    if (client.isClosed()) {
				connected = false;
			    }
			    if (e.getType().equals("bye")) {
				connected = false;
			    }
			} catch (EOFException e) {
			    connected = false;
			}
		    }
		} else {
		    System.out.println("unknown client");
		}
	    } catch (IOException ex) {
		Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
	    } catch (ClassNotFoundException ex) {
		Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
	    } catch (SQLException ex) {
		Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
		try {
		    client.close();
		} catch (IOException ex) {
		    Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	    if (type.equals("register")) {
		registerList.remove(this);
	    } else if (type.equals("doctor")) {
		doctorList.remove(this);
	    } else if (type.equals("chemist")) {
		chemistList.remove(this);
	    } else if (type.equals("registerUpdate")) {
		registerUpdateList.remove(this);
	    } else if (type.equals("chemistUpdate")) {
		chemistUpdateList.remove(this);
	    } else if (type.equals("registerDepartment")) {
		registerUpdateList.remove(this);
	    }
	}

	private void registerRun() throws IOException, ClassNotFoundException, SQLException {
	    EntityTable e = (EntityTable) in.readObject();
	    if (e.getType().equals("ConfirmCash")) {
		ConfirmCash cc = (ConfirmCash) e;
		IHistory itf = new HistoryProxy();
		itf.confirmHistroy(cc.getHistoryId());
		itf.confirmTakeDrug(cc.getHistoryId());
		HistoryInfo newDeliver = itf.getInfo(cc.getHistoryId());
		for (int i = 0; i < paymentBoard.size(); i++) {
		    if (newDeliver.getHistoryId() == paymentBoard.get(i).getHistoryId()) {
			paymentBoard.remove(i);
			break;
		    }
		}
		//paymentUpdate = true;
		updateRegister();
		//System.out.println(paymentUpdate);
		deliverBoard.add(newDeliver);
		updateDeliver();
	    } else if (e.getType().equals("SearchPatient")) {
		SearchPatient sp = (SearchPatient) e;
		IPatient itf = new PatientProxy();
		boolean hasId = itf.hasPatient(sp.getPatientId());
		out.writeBoolean(hasId);
		out.flush();
		if (hasId) {
		    itf.newHistory(sp.getPatientId(), sp.getDepartment());
		} else {
		    EntityTable e1 = (EntityTable) in.readObject();
		    if (e1.getType().equals("NewPatient")) {
			NewPatient np = (NewPatient) e1;
			itf.createPatient(sp.getPatientId(), np.getPatientName(), np.getGender(), np.getAge());
			itf.newHistory(sp.getPatientId(), sp.getDepartment());
			out.writeBoolean(true);
			out.flush();
		    }
		}
	    } else if (e.getType().equals("DeleteHistory")) {
		DeleteHistory dh = (DeleteHistory) e;
		IHistory itf = new HistoryProxy();
		itf.cancelHistory(dh.getHistoryId());
		for (int i = 0; i < paymentBoard.size(); i++) {
		    if (dh.getHistoryId() == paymentBoard.get(i).getHistoryId()) {
			paymentBoard.remove(i);
			break;
		    }
		}
		updateRegister();
		//paymentUpdate = true;
	    } else if (e.getType().equals("bye")) {
		connected = false;
	    }
	}

	private void doctorRun() throws IOException, ClassNotFoundException, SQLException {
	    if (doctorLogin) {
		EntityTable e = (EntityTable) in.readObject();
		if (e.getType().equals("HistoryDetailInfo")) {
		    HistoryDetailInfo hi = (HistoryDetailInfo) e;
		    IHistory itf = new HistoryProxy();
		    for (int i = 0; i < hi.getDrugs().size(); i++) {
			itf.addTakeDrug(hi.getHistoryId(), hi.getDrugs().get(i).getDrugId(), hi.getDrugs().get(i).getQuantity(), hi.getDrugs().get(i).getHowToEat());
		    }
		    itf.editHistory(hi.getHistoryId(), hi.getDiseaseInfo());
		    paymentBoard.add(itf.getInfo(hi.getHistoryId()));
		    updateRegister();
		    //paymentUpdate = true;
		} else if (e.getType().equals("NextPatient")) {
		    NextPatient np = (NextPatient) e;
		    IHistory itf = new HistoryProxy();
		    HistoryDetailInfo result = itf.nextPatient(np.getDoctorId());
		    sendObject(result);
		} else if (e.getType().equals("DoctorLogout")) {
		    DoctorLogout dl = (DoctorLogout) e;
		    IHospital itf = new HospitalProxy();
		    itf.logoutDoctor(dl.getDoctorId());
		    doctorLogin = false;
		    List<String> adl = itf.getAvailableDepartment();
		    EntityList el = new EntityList(adl);
		    for (int i = 0; i < registerDepartment.size(); i++) {
			registerDepartment.get(i).sendObject(el);
		    }
		} else if (e.getType().equals("SearchDrug")) {
		    SearchDrug sd = (SearchDrug) e;
		    IDrug itf = new DrugProxy();
		    List<DrugInfo> result;
		    if (sd.getSearchType() == 'N') {
			result = itf.searchDrug(sd.getKeyword());
		    } else {
			result = itf.searchDrugName(sd.getKeyword());
		    }
		    sendObject(result);
		} else if (e.getType().equals("GetHistory")) {
		    GetHistory gh = (GetHistory) e;
		    IHistory itf = new HistoryProxy();
		    HistoryInfo hi = itf.getInfo(gh.getHistoryId());
		    sendObject(hi);
		}
	    } else {
		EntityTable e = (EntityTable) in.readObject();
		if (e.getType().equals("DoctorLogin")) {
		    DoctorLogin dl = (DoctorLogin) e;
		    IHospital itf = new HospitalProxy();
		    doctorLogin = itf.loginDoctor(dl.getDoctorId());
		    out.writeBoolean(doctorLogin);
		    out.flush();
		    if (doctorLogin) {
			List<String> adl = itf.getAvailableDepartment();
			EntityList el = new EntityList(adl);
			for (int i = 0; i < registerDepartment.size(); i++) {
			    registerDepartment.get(i).sendObject(el);
			}
		    }
		}
	    }
	}

	private void chemistRun() throws IOException, ClassNotFoundException, SQLException {
	    EntityTable e = (EntityTable) in.readObject();
	    if (e.getType().equals("CompleteHistory")) {
		CompleteHistory ch = (CompleteHistory) e;
		IHistory itf = new HistoryProxy();
		itf.completeHistory(ch.getHistoryId());
		for (int i = 0; i < deliverBoard.size(); i++) {
		    if (ch.getHistoryId() == deliverBoard.get(i).getHistoryId()) {
			deliverBoard.remove(i);
			break;
		    }
		}
		updateDeliver();
	    } else if (e.getType().equals("NotCompleteHistory")) {
		NotCompleteHistory nch = (NotCompleteHistory) e;
		IHistory itf = new HistoryProxy();
		itf.notCompleteHistory(nch.getHistoryId());
		for (int i = 0; i < deliverBoard.size(); i++) {
		    if (nch.getHistoryId() == deliverBoard.get(i).getHistoryId()) {
			deliverBoard.remove(i);
			break;
		    }
		}
		updateDeliver();
	    } else if (e.getType().equals("SearchDrug")) {
		SearchDrug sd = (SearchDrug) e;
		IDrug itf = new DrugProxy();
		List<DrugInfo> result;
		if (sd.getSearchType() == 'N') {
		    result = itf.searchDrug(sd.getKeyword());
		} else {
		    result = itf.searchDrugName(sd.getKeyword());
		}
		sendObject(result);
	    } else if (e.getType().equals("AddNewDrug")) {
		AddNewDrug ad = (AddNewDrug) e;
		IDrug itf = new DrugProxy();
		itf.newDrug(ad.getDrugName(), ad.getDrugInfo(), ad.getNum(), ad.getManufacturer(), ad.getPrice());
	    } else if (e.getType().equals("DeleteDrug")) {
		DeleteDrug dd = (DeleteDrug) e;
		IDrug itf = new DrugProxy();
		itf.deleteDrug(dd.getDrugId());
	    } else if (e.getType().equals("AddRemain")) {
		AddRemain ar = (AddRemain) e;
		IDrug itf = new DrugProxy();
		itf.addDrug(ar.getDrugId(), ar.getNum());
	    }
	}

	public void sendString(String input) {
	    try {
		EntityTable e = new EntityTable();
		e.setType(input);
		out.writeObject(e);
		out.flush();
	    } catch (IOException ex) {
		Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

	public void sendObject(Object input) {
	    try {
		out.writeObject(input);
		out.flush();
	    } catch (IOException ex) {
		Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
	    }

	}

	public void updateRegister() {
	    for (int i = 0; i < registerUpdateList.size(); i++) {
		//System.out.println("111");
		registerUpdateList.get(i).sendString("update");
		for (int j = 0; j < paymentBoard.size(); j++) {
		    // System.out.println(registerUpdateList.get(i));
		    registerUpdateList.get(i).sendObject(paymentBoard.get(j));
		}
		registerUpdateList.get(i).sendString("end");
	    }
	}

	public void updateDeliver() {
	    for (int i = 0; i < chemistUpdateList.size(); i++) {
		chemistUpdateList.get(i).sendString("update");
		for (int j = 0; j < deliverBoard.size(); j++) {
		    chemistUpdateList.get(i).sendObject(deliverBoard.get(j));
		}
		chemistUpdateList.get(i).sendString("end");
	    }
	}
    }

    public ThreadManager() {
	paymentBoard = new ArrayList<>();
	deliverBoard = new ArrayList<>();
	registerList = new ArrayList<>();
	doctorList = new ArrayList<>();
	chemistList = new ArrayList<>();
	registerUpdateList = new ArrayList<>();
	chemistUpdateList = new ArrayList<>();
	registerDepartment = new ArrayList<>();
	//paymentUpdate = false;
	//deliverUpdate = false;
	//new backgroundThread();
    }

    public void addThread(Socket s) throws IOException {
	new ServerThread(s);
    }
}
