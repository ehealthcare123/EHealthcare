package struts2.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Results({ @Result(name = "success", location = "/index.jsp")
	})
public class LogoutAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1507160479129689109L;

	@Action(value = "logout")
	public String execute() {
		ActionContext.getContext().getSession().remove("userlogindata");
		return SUCCESS;			
	}
}