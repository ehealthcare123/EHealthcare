package struts2.model;

public enum UserType {
	ADMIN(0),DOCTOR(1),PATIENT(2);
	int userlevel;
	UserType(int p) {
	      userlevel = p;
	   }
}
