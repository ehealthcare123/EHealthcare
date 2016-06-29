package struts2.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import struts2.model.UserLoginData;

import struts2.service.DatabaseConnector2;

@Results({ @Result(name = "success", location="/profile.jsp"), @Result(name = "input", location = "/profile.jsp") })
public class ChangeProfileAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7482725988585007603L;
	private String password;
	private String password2;
	private String firstname;
	private String surname;
	private String usermail;
	private UserLoginData userlogindata;
	private DatabaseConnector2 dc;

	
	@Action(value = "profile")
	public String execute() {
//		User in Datenbank ändern
		if(!dc.updateUser(dc.getID(userlogindata.getUsername()), password, surname, firstname, usermail)){
			addActionError("SQL-Update failed!");
		}
		else{
			userlogindata = new UserLoginData(dc.getID(userlogindata.getUsername()), userlogindata.getUsername(), firstname , surname, password, userlogindata.getUsertype(), usermail);
//			Logindaten in Session ablegen
			ActionContext.getContext().getSession().put("userlogindata", userlogindata);
			addActionMessage("Your changes has been saved!");
		}
		
		return SUCCESS;
	}
	
	public ChangeProfileAction(){
		userlogindata = (UserLoginData) ActionContext.getContext().getSession().get("userlogindata");
		dc = new DatabaseConnector2();
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
//		Passwortvergleich
		if (!password.equals(password2)) {
			addFieldError("password2", "Passwords are different!");
		}
	}

	@RequiredStringValidator(message = "Please reenter your password!")
	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	@RequiredStringValidator(message = "Please enter your first name!")
	@RegexFieldValidator(regex = "^[\\p{L} .'-]+$", message = "Use only letters and spaces!")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@RequiredStringValidator(message = "Please enter your surname!")
	@RegexFieldValidator(regex = "^[\\p{L} .'-]+$", message = "Use only letters and spaces!")
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@RequiredStringValidator(message = "E-Mail address is needed to send you receipts")
	@RegexFieldValidator(regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "This is no valid E-Mail-Address!")
	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}
}
