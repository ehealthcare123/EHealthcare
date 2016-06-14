package struts2.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

import struts2.service.DatabaseConnector;

@Results({ @Result(name = "success", location = "/admin.jsp"), @Result(name = "input", location = "/reset.jsp") })
public class CreateTablesAction extends ActionSupport {


	/**
	 * 
	 */
	private static final long serialVersionUID = 370148480803005023L;

	private DatabaseConnector dc = new DatabaseConnector();

	@Action(value = "createTables")
	public String execute() {
		if(dc.connectTosqlite() && dc.createDocTypeTable() && dc.createUserTable()){
//			ActionContext.getContext().getSession().put("info","Tables were created");
			addActionMessage("Tables were created");
		}
		else{
//			ActionContext.getContext().getSession().put("info","Table creation failed. Check console for further information!");
			addActionMessage("Table creation failed. Check console for further information!");
		}
		return SUCCESS;
	}
}
