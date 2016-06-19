package struts2.service;

import java.util.HashMap;

import javax.websocket.Session;

public class WaitingQueueCategory {
	private static HashMap<String, WaitingQueue> waitingqueues = new HashMap<String, WaitingQueue>();
	
	public synchronized static void addPatient(String category, Session patient, Integer sessionID){
		WaitingQueue wq = waitingqueues.get(category);
		if(wq != null){
//			existiert die Warteschlange schon so füge neuen Patienten hinzu
			wq.addPatient(patient,sessionID);
		}
		else{
//			neue Warteschlange mit Patienten erstellen
			waitingqueues.put(category, new WaitingQueue(patient,sessionID));			
		}
	}
	
	public synchronized static Session getNextPatient(String category){
		Session returnthis = waitingqueues.get(category).getNextPatientSession();
		return returnthis;
	}
	
	public synchronized static Integer getNextPatientID(String category){
		Integer returnthis = waitingqueues.get(category).getNextPatientSessionID();
		return returnthis;
	}
	
	public synchronized static void removePatient(String category, Session patient){
		waitingqueues.get(category).removePatient(patient);
		if(waitingqueues.get(category).getWaitingQueueSize() == 0){
			waitingqueues.remove(category);
		}
	}
	
	public static Integer getWaitingQueuesSize(){
		return waitingqueues.size();
	}
	
	public static Integer getWaitingQueueSize(String category){
		return waitingqueues.get(category) != null ? waitingqueues.get(category).getWaitingQueueSize() : 0;
	}
}
