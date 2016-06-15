package struts2.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import struts2.model.UserLoginData;
import struts2.model.UserType;

import struts2.service.DatabaseConnector;

@Results({ @Result(name = "success", location = "/profile.jsp"), @Result(name = "input", location = "/profile.jsp") })
public class ChangeProfileAction extends ActionSupport {
	/**
	* 
	*/
	private static final long serialVersionUID = 224532928379636400L;

	private String registername;
	private String password;
	private String password2;
	private String firstname;
	private String surname;
	private String mail;
	private UserLoginData userlogindata;
	private DatabaseConnector dc;

	public ChangeProfileAction(){
		userlogindata = (UserLoginData) ActionContext.getContext().getSession().get("userlogindata");
		registername = userlogindata.getUsername();
		password = userlogindata.getUserpassword();
		password2 = userlogindata.getUserpassword();
		firstname = userlogindata.getFullname().split(" ")[0];
		surname = userlogindata.getFullname().split(" ")[1];
		mail = userlogindata.getUsermail();
		
	}
	
	@Action(value = "changeprof")
	public String execute() {
//		User in Datenbank ändern
//		dc.updateUser(registername, password, surname, firstname, mail);
		userlogindata = new UserLoginData(dc.getID(registername), registername, firstname + " " + surname, password, UserType.PATIENT, mail);
//		Logindaten in Session ablegen
		ActionContext.getContext().getSession().put("userlogindata", userlogindata);
		
		return SUCCESS;
	}
	
	@Action(value = "changeprofin")
	public String display(){
		return "input";
	}

	@RequiredStringValidator(message = "Please enter a user name!")
	@RegexFieldValidator(regex = "^[a-z0-9_-]{3,15}$", message = "This isn't a valid username! Use a-z, 0-9, underscore or hyphen and at least 3 characters and maximum length of 15")
	public String getRegistername() {
		return registername;
	}

	public void setRegistername(String registername) {
		this.registername = registername;
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
		
//		gibt es den User schon?
		dc = new DatabaseConnector();
		if(dc.getID(this.getRegistername()) != null){
			addFieldError("registername", "User already exists. Choose a different user name!");
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
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
