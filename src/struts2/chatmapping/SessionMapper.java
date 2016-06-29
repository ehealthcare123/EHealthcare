package struts2.chatmapping;

import java.util.HashMap;

public class SessionMapper {
	private static HashMap<Integer, Object> sessions = new HashMap<Integer, Object>();
	
	public synchronized static void addSession(Integer sessionID, Object object){
		sessions.put(sessionID, object);
	}
	
	public static Object getSession(Integer sessionID){
		return sessions.get(sessionID);
	}
	
	public synchronized static void removeSession(Integer sessionID){
		sessions.remove(sessionID);
	}
}
