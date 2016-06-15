package struts2.model;

/**
 * POJO for Login User
 * @author Sebbo
 *
 */
public class UserLoginData {

	private int userid;
	private String username;
	private String fullname;
	private String userpassword;
	private UserType usertype;
	private String usermail;

	public UserLoginData(int userid, String username, String fullname, String userpassword, UserType usertype, String usermail) {
		this.setUserid(userid);
		this.username = username;
		this.fullname = fullname;
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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

}
