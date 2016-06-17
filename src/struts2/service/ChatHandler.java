package struts2.service;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import struts2.model.UserLoginData;

@ServerEndpoint(value="/WSSchat/{sessionID}")
public class ChatHandler {
	
	@javax.websocket.OnOpen
	public void OnOpen(Session session, @PathParam("sessionID") String sessionID){
		UserLoginData uld = (UserLoginData) SessionMapper.getSession(Integer.parseInt(sessionID));
		System.out.println(uld.getFullname());

		//		Map<String,Object> hm = ActionContext.getContext().getSession();
//		System.out.println(hm.toString());
//		UserLoginData uld = (UserLoginData) ActionContext.getContext().getSession().get("userlogindata");
//		if(uld != null){
//			if(uld.getUsertype() == UserType.PATIENT){
//				System.out.println("patient waits for doctor");
//			}
//			else if(uld.getUsertype() == UserType.DOCTOR){
//				System.out.println("doctor entered the chat");
//			}
//			else{
//				System.out.println("Some other user entered the chat room");
//			}
//		}
//		else{
//			System.out.println("somebody connected to websocket");
//		}
//		System.out.println("Session "+session.toString()+ " hat sich angemeldet!");
//		 try {
//			session.getBasicRemote().sendText("onOpen");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 System.out.println();
	}
	
	@javax.websocket.OnClose
	public void OnClose(Session session){
		System.out.println("Session "+session.toString()+ " wurde entfernt!");
	}
	
	@OnMessage
	public void handleMessage(Session session, String msg){
		System.out.println(msg);
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
