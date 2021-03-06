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

import struts2.chatmapping.CategoryMapper;
import struts2.chatmapping.ChatSessions;
import struts2.chatmapping.SessionMapper;
import struts2.chatmapping.WaitingQueueCategory;
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
//				LoginData des Patienten holen
				UserLoginData uldPatient = (UserLoginData) SessionMapper.getSession(patientID);
				
//				Doktor wird dem Patienten zugeordnet
				ChatSessions.addChatClient(sID, patientSession);
//				und umgekehrt
				ChatSessions.addChatClient(patientID,session);
				
//				PatientID mit DoktorID verkn�pfen
				PatientSession.addPatient(sID, patientID);
				
//				Patient wird aus der Warteschlange f�r die jeweilige Kategorie genommen
				WaitingQueueCategory.removePatient(wantedCategory, patientSession);
				
//				Kategorieauswahl des Arztes und des Patienten l�schen
				CategoryMapper.removeCategory(sID);
				CategoryMapper.removeCategory(patientID);
				
//				Chat f�r Patient freischalten
				try {
//					Signal f�r Clients (Doktor und Patient)
					session.getBasicRemote().sendText(String.valueOf(new JSONObject()
							.put("chatcommand", "startchat")));
					patientSession.getBasicRemote().sendText(String.valueOf(new JSONObject()
	                        .put("chatcommand", "startchat")));
//					StartChat Message f�r Arzt
	                session.getBasicRemote().sendText(String.valueOf(new JSONObject()
	                        .put("usermessage", "You are now connected with patient <b>"+ uldPatient.getFullname() +"</b>")));
//	                StartChat Message f�r Patient
	                patientSession.getBasicRemote().sendText(String.valueOf(new JSONObject()
	                		.put("usermessage", "You are now connected with doctor <b>"+ uld.getFullname() +"</b>")));
	                
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
//				Arzt aus ChatSession-Eintr�gen entfernen
				Session doctorSession = ChatSessions.getChatClient(sID);
				if(doctorSession != null && doctorSession.isOpen()){
//					Doktor als Chatpartner entfernen
					ChatSessions.removeChatClient(sID);
//					Patienten �ber Ende des Chats informieren, Eingabe deaktivieren
					try {
						doctorSession.getBasicRemote().sendText(String.valueOf(new JSONObject()
								.put("usermessage", "<br>The patient exited the chat.")));
						doctorSession.getBasicRemote().sendText(String.valueOf(new JSONObject()
								.put("chatcommand", "endchat")));
					} catch (JSONException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (CategoryMapper.getCategory(sID) != null){
	//				Patient wird aus der Warteschlange f�r die jeweilige Kategorie genommen, falls noch kein Chat gestartet wurde
					WaitingQueueCategory.removePatient(patientcategory, session);										
				}				
				System.out.println("patient "+session.toString()+" was removed from category '"+ patientcategory + "'");
			}
			else if(uld.getUsertype() == UserType.DOCTOR){
				System.out.println("doctor exited the chat");
//				Arzt aus ChatSession-Eintr�gen entfernen
				Session patientSession = ChatSessions.getChatClient(sID);
				ChatSessions.removeChatClient(sID);
				if(patientSession.isOpen()){
					try {
						//				Patienten �ber Ende des Chats informieren, Eingabe deaktivieren
		                patientSession.getBasicRemote().sendText(String.valueOf(new JSONObject()
		                		.put("usermessage", "<br>The doctor exited the chat.")));
						patientSession.getBasicRemote().sendText(String.valueOf(new JSONObject()
								.put("chatcommand", "endchat")));
					} catch (JSONException | IOException e) {
					}					
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
