package struts2.service;

import static j2html.TagCreator.article;
import static j2html.TagCreator.b;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

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
				
//				hole Doktor-Kategorie aus HashMap
				String wantedCategory = CategoryMapper.getCategory(sID);
//				Session des Patienten ermitteln
				Session patientSession = WaitingQueueCategory.getNextPatient(wantedCategory);
//				Session-ID des Patienten holen
				Integer patientID = WaitingQueueCategory.getNextPatientID(wantedCategory);
				
//				Doktor wird dem Patienten zugeordnet
				ChatSessions.addChatClient(sID, patientSession);
//				und umgekehrt
				ChatSessions.addChatClient(patientID,session);
				
//				Patient wird aus der Warteschlange für die jeweilige Kategorie genommen
				WaitingQueueCategory.removePatient(wantedCategory, patientSession);
				
//				Kategorieauswahl des Arztes und des Patienten löschen
				CategoryMapper.removeCategory(sID);
				CategoryMapper.removeCategory(patientID);
				
//				Chat für Patient freischalten
				try {
//					Signal für Clients (Doktor und Patient)
					session.getBasicRemote().sendText(String.valueOf(new JSONObject()
							.put("chatcommand", "startchat")));
					patientSession.getBasicRemote().sendText(String.valueOf(new JSONObject()
	                        .put("chatcommand", "startchat")));
					System.out.println("chat session started!");
				} catch (JSONException | IOException e) {
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
//		steht der User in der SessionMap ?
		if(uld != null){
			if(uld.getUsertype() == UserType.PATIENT){
				String patientcategory = CategoryMapper.getCategory(sID);
				
				System.out.println("patient "+session.toString()+" was removed from category '"+ patientcategory + "'");
			}
			else if(uld.getUsertype() == UserType.DOCTOR){
				System.out.println("doctor exited the chat");
//				Arzt aus ChatSession-Einträgen entfernen
				Session patientSession = ChatSessions.getChatClient(sID);
				ChatSessions.removeChatClient(sID);
				try {
	//				Patienten über Ende des Chats informieren, Eingabe deaktivieren
					patientSession.getBasicRemote().sendText(String.valueOf(new JSONObject()
					        .put("chatcommand", "endchat")));
				} catch (JSONException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else{
				System.out.println("Some other user exited the chat room");
			}
		}
		
	}
	
	@OnMessage
	public void handleMessage(Session session, String msg, @PathParam("sessionID") String sessionID){
        if (session.isOpen()) {
            try {
            	Integer sID = Integer.parseInt(sessionID);
            	Session othersession = ChatSessions.getChatClient(sID);
            	UserLoginData uld = (UserLoginData) SessionMapper.getSession(sID);
            	msg = createHtmlMessageFromSender(uld.getFullname(),msg);
//            	Nachricht an sich selbst
                session.getBasicRemote().sendText(String.valueOf(new JSONObject()
                        .put("usermessage", msg)));
//              Nachricht an anderen Teilnehmer
                othersession.getBasicRemote().sendText(String.valueOf(new JSONObject()
                        .put("usermessage", msg)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
    //Builds a HTML element with a sender-name, a message, and a timestamp,
    private static String createHtmlMessageFromSender(String sender, String message) {
        return article().with(
                b(sender + " says:"),
                p(message),
                span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
        ).render();
    }
}
