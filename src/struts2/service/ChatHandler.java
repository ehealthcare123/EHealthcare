package struts2.service;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import struts2.model.UserLoginData;
import struts2.model.UserType;

@ServerEndpoint(value="/WSSchat/{sessionID}")
public class ChatHandler {
	
	@javax.websocket.OnOpen
	public void OnOpen(Session session, @PathParam("sessionID") String sessionID){
		Integer sID = Integer.parseInt(sessionID);
		UserLoginData uld = (UserLoginData) SessionMapper.getSession(sID);
//		steht der User in der SessionMap?
		if(uld != null){
			if(uld.getUsertype() == UserType.PATIENT){
				String patientcategory = CategoryMapper.getCategory(sID);
				System.out.println("patient waits in category '"+ patientcategory + "' for doctor");
				WaitingQueueCategory.addPatient(patientcategory, session, sID);
			}
			else if(uld.getUsertype() == UserType.DOCTOR){
				System.out.println("doctor "+ uld.getFullname() + " entered the chat");
				String wantedCategory = CategoryMapper.getCategory(sID);
				Session patientSession = WaitingQueueCategory.getNextPatient(wantedCategory);
				Integer patientID = WaitingQueueCategory.getNextPatientID(wantedCategory);
//				Doktor wird dem Patienten zugeordnet
				ChatSessions.addChatClient(sID, patientSession);
//				und umgekehrt
				ChatSessions.addChatClient(patientID,session);
//				Patient wird aus der Warteschlange für die jeweilige Kategorie genommen
				WaitingQueueCategory.removePatient(wantedCategory, patientSession);
//				Chat für Patient freischalten
				try {
					patientSession.getBasicRemote().sendText("startchat");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				System.out.println("Some other user entered the chat room");
			}
		}
	}
	
	@javax.websocket.OnClose
	public void OnClose(Session session, @PathParam("sessionID") String sessionID){
		Integer sID = Integer.parseInt(sessionID);
		UserLoginData uld = (UserLoginData) SessionMapper.getSession(sID);
//		steht der User in der SessionMap?
		if(uld != null){
			if(uld.getUsertype() == UserType.PATIENT){
				String patientcategory = CategoryMapper.getCategory(sID);
//				Patient wird aus der Warteschlange für die jeweilige Kategorie genommen				
				WaitingQueueCategory.removePatient(patientcategory, session);
				
				System.out.println("patient "+session.toString()+" was removed from category '"+ patientcategory + "'");
				
				CategoryMapper.removeCategory(Integer.parseInt(sessionID));
			}
			else if(uld.getUsertype() == UserType.DOCTOR){
				CategoryMapper.removeCategory(Integer.parseInt(sessionID));
				System.out.println("doctor exited the chat");
			}
			else{
				System.out.println("Some other user exited the chat room");
			}
		}
		
	}
	
	@OnMessage
	public void handleMessage(Session session, String msg){
        if (session.isOpen()) {
            try {
				session.getBasicRemote().sendText(msg);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
    @OnMessage
    public void echoBinaryMessage(Session session, ByteBuffer bb,
            boolean last) {
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendBinary(bb, last);
            }
        } catch (IOException e) {
            try {
                session.close();
            } catch (IOException e1) {
                // Ignore
            }
        }
    }
}
