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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
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
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
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
}
