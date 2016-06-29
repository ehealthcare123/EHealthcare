package struts2.service;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnector2
{
	private Connection c;

	public boolean connectTosqlite(){
		 try {
			 Class.forName("org.sqlite.JDBC");
			 String dbpath = "jdbc:sqlite:" + System.getProperty("user.home") + File.separator + "access.db";
			 c = DriverManager.getConnection(dbpath);
		 } catch ( Exception e ) {
			 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			 return false;
		 }
		 return true;
	 }

	public boolean closeConnection()
	{
		try
		{
			c.close();
		}
		catch(Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      return false;
		}
		return true;
	}
	
	public boolean createDocTypeTable()
	{
		String sql = "CREATE TABLE TYPES " +
				"(ID	INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
				" TYP	TEXT	NOT NULL UNIQUE)";
		return this.executeStatement(sql);
	}
	
	public boolean addDocType(String type)
	{
		String sql = "INSERT INTO TYPES(TYP) VALUES (" + type +")";
		
		return this.executeStatement(sql);
	}


	public boolean createUserTable()
	{
		String sql = "CREATE TABLE BENUTZER " +
			"(ID	INTEGER	 PRIMARY KEY AUTOINCREMENT NOT NULL," +
			" LOGIN CHAR(15) NOT NULL UNIQUE," +
			" PASSWORT		 TEXT     NOT NULL, " + 
			" NAME			 TEXT     NOT NULL, " + 
			" VORNAME		 TEXT     NOT NULL, " +
			" MAIL   		 TEXT     NOT NULL, " + 
			" TYP			 INT      NOT NULL, " +
			" SPEZIALGEBIET	 INTEGER)";
		return this.executeStatement(sql);
	 }
	
	public boolean dropUserTable()
	{
		String sql = "DROP TABLE BENUTZER";
		
		return this.executeStatement(sql);
	}
	
	public boolean dropDocTypeTable()
	{
		String sql = "DROP TABLE TYPES";
		
		return this.executeStatement(sql);
	}
	
	public boolean createAllTables()
	{
		if(!createUserTable())
			return false;
		if(!createDocTypeTable())
		{
			dropUserTable();
			return false;
		}
		return true;
	}

	
	//Hardcode, weil wir keine weiteren Typen brauchen werden!
	public String getTypeString(int i)
	{
		switch(i)
		{
		case 0: {return "Admin";}
		case 1: {return "Doctor";}
		case 2: {return "Patient";}
		default: {return null;}
		}
	}
	
	public boolean insertUser(String login, String pw, String name, String vorname, String mail)
	{
		return insertUser(login,pw,name,vorname,2,mail,0);
	}

	public boolean insertAdmin(String login, String pw, String name, String vorname, String mail)
	{
		return insertUser(login,pw,name,vorname,0, mail,0);
	}

	public boolean insertDoc(String login, String pw, String name, String vorname, String spez, String mail)
	{
		int s = getSpezNumber(spez);
		if(s>0)
			return insertUser(login,pw,name,vorname,1, mail,s);
		return false;
	}
	
	public Integer getSpezNumber(String s)
	{
		if(!connectTosqlite())	return 0;
		Statement stmt;
		int id = 0;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ID FROM TYPES WHERE TYP='"+s+"'");
			rs.next();
			id = rs.getInt("id");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		closeConnection();
		return id;
	}


	public boolean insertUser(String login, String pw, String name, String vorname, int typ, String mail, int spez)
	{
		String sql = "";
		if(spez>0)
		{
		    sql = "INSERT INTO BENUTZER(LOGIN, PASSWORT, NAME, VORNAME, TYP, MAIL, SPEZIALGEBIET)" +
			"VALUES (" +
				"'" + login + "'," + 
				"'" + pw + "'," + 
				"'" + name + "'," + 
				"'" + vorname + "'," + 
				"'" + typ + "'," +
				"'" + mail + "'," +
				"'" + spez + "')";
		}
		else
		{
			sql = "INSERT INTO BENUTZER(LOGIN, PASSWORT, NAME, VORNAME, TYP, MAIL)" +
					"VALUES (" +
						"'" + login + "'," + 
						"'" + pw + "'," + 
						"'" + name + "'," + 
						"'" + vorname + "'," + 
						"'" + typ + "'," +
						"'" + mail + "')";
		}
		return this.executeStatement(sql);
	}

	public boolean executeStatement(String sql){
			if(!this.connectTosqlite())	return false;
		    Statement stmt = null;
		    try {
		      stmt = c.createStatement();
		      stmt.executeUpdate(sql);
		      stmt.close();
		      this.closeConnection();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      return false;
		    }
		    return true;
	 }

	public boolean deleteUser(int id)
	{
		String sql = "DELETE FROM BENUTZER WHERE ID="+id;

		return this.executeStatement(sql);
	}

	public String getName(int id)
	{
		if(!connectTosqlite())	return null;
		Statement stmt;
		String vn="",n = "";
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT NAME,VORNAME FROM BENUTZER WHERE ID="+id);
			rs.next();
			n = rs.getString("name");
			vn = rs.getString("vorname");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		closeConnection();
		return (vn + " " + n);
	}

	public String getPW(int id)
	{
		if(!connectTosqlite())	return null;
		Statement stmt;
		String pw = "";
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT PASSWORT FROM BENUTZER WHERE ID="+id);
			rs.next();
			pw = rs.getString("passwort");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		closeConnection();
		return pw;
	}
	
	public String getMail(int id)
	{
		if(!connectTosqlite())	return null;
		Statement stmt;
		String pw = "";
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAIL FROM BENUTZER WHERE ID="+id);
			rs.next();
			pw = rs.getString("mail");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		closeConnection();
		return pw;
	}


	public Integer getID(String login)
	{
		if(!connectTosqlite())	return null;
		Statement stmt;
		Integer id = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ID FROM BENUTZER WHERE LOGIN='"+login+"'");
			rs.next();
			id = rs.getInt("id");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		closeConnection();
		return id;
	}

	public Integer getTyp(int id)
	{
		if(!connectTosqlite())	return null;
		Statement stmt;
		Integer typ = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT TYP FROM BENUTZER WHERE ID="+id);
			rs.next();
			typ = rs.getInt("typ");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		closeConnection();
		return typ;
	}

	public String getLogin(int id)
	{
		if(!connectTosqlite())	return null;
		Statement stmt;
		String login = "";
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT LOGIN FROM BENUTZER WHERE ID="+id);
			rs.next();
			login = rs.getString("login");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		closeConnection();
		return login;
	}

	public String getDocSpez(int id)
	{
		if(!connectTosqlite())	return null;
		Statement stmt;
		String spez = "";
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT T.TYP FROM BENUTZER AS B INNER JOIN TYPES AS T ON B.SPEZIALGEBIET = T.ID WHERE B.ID="+id);
			rs.next();
			spez = rs.getString("typ");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		closeConnection();
		return spez;
	}
	
	public ArrayList<String> getDocCategories()
	{
		if(!connectTosqlite())	return null;
		Statement stmt;
		ArrayList<String> categories = new ArrayList<String>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT TYP FROM TYPES");
			while(rs.next()){
				categories.add(rs.getString("typ"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		closeConnection();
		return categories;
	}

	public boolean updateUser(Integer id, String password, String surname, String firstname, String mail) {
	    String sql = "UPDATE BENUTZER SET PASSWORT='"+ password +"', NAME='"+ surname +"', VORNAME='"+ firstname +"', MAIL='"+ mail +"' "+
		             "WHERE ID="+id.toString();
		return this.executeStatement(sql);
	}
}
