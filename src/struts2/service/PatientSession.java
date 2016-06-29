package struts2.service;

import java.util.HashMap;

public class PatientSession {
	private static HashMap<Integer, Integer> chatsWith = new HashMap<Integer, Integer>();

	public static synchronized void addPatient(Integer docSID, Integer patientSID) {
		chatsWith.put(docSID, patientSID);
	}

	public static Integer getPatient(Integer docSID) {
		return chatsWith.get(docSID);
	}

	public static synchronized void removePatient(Integer docSID) {
		chatsWith.remove(docSID);
	}
}
