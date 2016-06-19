package struts2.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.*;

import struts2.model.UserLoginData;
import struts2.model.UserType;
import struts2.service.DatabaseConnector;
import struts2.service.SessionMapper;

@Results({ 
	@Result(name = "patient",type="redirectAction" ,location = "prechoosedoc"), 
	@Result(name = "admin"  , location = "/admin.jsp"), 
	@Result(name = "doctor" , location = "/index.jsp"),
	@Result(name = "input"  , location = "/index.jsp")
	})
public class LoginAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8067150970565987762L;
	private String loginname;
	private String password;
	
	/**
	 * 
	 */
	@Action(value = "login")
	public String execute() {
//		ActionContext.getContext().getSession().put("userlogindata", new UserLoginData(1, loginname, "blub bla", password, UserType.ADMIN, "blub@bla.com"));
		UserLoginData uld = (UserLoginData) ActionContext.getContext().getSession().get("userlogindata");
		
		if(uld != null){
//			gebe Usertyp zurück und leite an nächste Maske weiter
			return uld.getUsertype().toString().toLowerCase();						
		}
		else{
			return "input";
		}
		
	}

	@RequiredStringValidator(message = "Please enter a user name!")
	@RegexFieldValidator(regex = "^[a-z0-9_-]{3,15}$", message = "This isn't a valid username!")
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@RequiredStringValidator(message = "Please enter a password!")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void validate() {
//		ask database for userid
		DatabaseConnector dc = new DatabaseConnector();
		Integer userid = dc.getID(this.getLoginname());

//		Integer userid = 1;
//		
		if (userid == null || !dc.getPW(userid).equals(this.getPassword())){
			addActionError("Incorrect login data");
		}
		else{
			UserLoginData uld = new UserLoginData(userid, loginname, dc.getName(userid), dc.getPW(userid), UserType.fromInt(dc.getTyp(userid)), dc.getMail(userid));
			ActionContext.getContext().getSession().put(
					"userlogindata", uld);
			ActionContext.getContext().getSession().put(
					"strutssessionid", uld.hashCode());
			SessionMapper.addSession(uld.hashCode(), uld);
			
//			Beim Doktor die Kategorie in Session speichern
			if(uld.getUsertype() == UserType.DOCTOR){
				ActionContext.getContext().getSession().put(
						"doctortype", dc.getDocSpez(userid));
			}
		}
	}
}