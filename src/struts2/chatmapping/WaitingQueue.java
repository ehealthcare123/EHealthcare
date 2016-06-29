package struts2.chatmapping;

import java.util.ArrayList;

import javax.websocket.Session;

public class WaitingQueue {
	private ArrayList<Session> waitingqueue = new ArrayList<Session>();
	private ArrayList<Integer> sessionIDs   = new ArrayList<Integer>();	
	
	public WaitingQueue(Session firstPatient, Integer sessionID){
		addPatient(firstPatient, sessionID);
	}
	
	public synchronized void addPatient(Session patientSession, Integer sessionID){
		waitingqueue.add(patientSession);
		sessionIDs.add(sessionID);
	}
	
	public synchronized void removePatient(Session patientSession){
		sessionIDs.remove(waitingqueue.indexOf(patientSession));
		waitingqueue.remove(patientSession);
	}
	
	public synchronized Session getNextPatientSession(){
		if(getWaitingQueueSize() > 0){
			Session returnthis = waitingqueue.get(0);
			return returnthis;
		}
		else{
			return null;
		}
	}
	
	public synchronized Integer getNextPatientSessionID(){
		if(getWaitingQueueSize() > 0){
			Integer returnthis = sessionIDs.get(0);
			return returnthis;
		}
		else{
			return null;
		}
	}
	
	public Integer getWaitingQueueSize(){
		return waitingqueue.size();
	}
}
