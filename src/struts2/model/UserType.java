package struts2.model;

public enum UserType {
	ADMIN(0),DOCTOR(1),PATIENT(2);
	int userlevel;
	UserType(int userlevel) {
	      this.userlevel = userlevel;
	   }
	
    public int getValue() {
        return this.userlevel;
    }
    
    public static UserType fromInt(Integer i) {
        for (UserType b : UserType .values()) {
            if (b.getValue() == i) { return b; }
        }
        return null;
    }
}
