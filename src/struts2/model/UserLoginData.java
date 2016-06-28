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

	public UserLoginData(int userid, String username, String firstname ,String surname, String userpassword, UserType usertype, String usermail) {
		this.setUserid(userid);
		this.setUsername(username);
		this.setFirstname(firstname);
		this.setSurname(surname);
		this.setUserpassword(userpassword);
		this.setUsertype(usertype);
		this.setUsermail(usermail);
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

	public String getFullname() {
		return this.getFirstname() + " " + this.getSurname();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + userid;
		result = prime * result + ((usermail == null) ? 0 : usermail.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((userpassword == null) ? 0 : userpassword.hashCode());
		result = prime * result + ((usertype == null) ? 0 : usertype.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserLoginData other = (UserLoginData) obj;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (userid != other.userid)
			return false;
		if (usermail == null) {
			if (other.usermail != null)
				return false;
		} else if (!usermail.equals(other.usermail))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (userpassword == null) {
			if (other.userpassword != null)
				return false;
		} else if (!userpassword.equals(other.userpassword))
			return false;
		if (usertype != other.usertype)
			return false;
		return true;
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

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
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
	
}
