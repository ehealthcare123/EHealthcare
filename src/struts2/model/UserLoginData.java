package struts2.model;

/**
 * POJO for Login User
 * @author Sebbo
 *
 */
public class UserLoginData {

	private int userid;
	private String username;
	private String firstname;
	private String surname;
	private String userpassword;
	private UserType usertype;
	private String usermail;

	public UserLoginData(int userid, String username, String firstname, String surname, String userpassword, UserType usertype, String usermail) {
		this.setUserid(userid);
		this.username = username;
		this.firstname = firstname;
		this.surname = surname;
		this.userpassword = userpassword;
		this.usertype = usertype;
		this.usermail = usermail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public UserType getUsertype() {
		return usertype;
	}

	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

}
