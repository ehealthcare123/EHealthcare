package struts2.service;

import java.util.HashMap;

public class SessionMapper {
	private static HashMap<Integer, Object> sessions = new HashMap<Integer, Object>();
	
	public synchronized static void addSession(int sessionID, Object object){
		sessions.put(sessionID, object);
	}
	
	public static Object getSession(Integer sessionID){
		return sessions.get(sessionID);
	}
}
