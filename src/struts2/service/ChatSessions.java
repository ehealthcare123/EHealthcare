package struts2.service;

import java.util.HashMap;

import javax.websocket.Session;

public class ChatSessions {
	private static HashMap<Integer, Session> chatsWith = new HashMap<Integer, Session>();

	public static synchronized void addChatClient(Integer sessionID, Session chatUserSession) {
		chatsWith.put(sessionID, chatUserSession);
	}

	public static Session getChatClient(Integer sessionID) {
		return chatsWith.get(sessionID);
	}

	public static synchronized void removeChatClient(Integer sessionID) {
		chatsWith.remove(sessionID);
	}
	
}
