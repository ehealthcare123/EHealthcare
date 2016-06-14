package struts2.service;

import java.io.File;
import java.sql.*;

public class DatabaseConnector
{
	private Connection c;

	public DatabaseConnector()
	{
		c = null;
	}

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


	public boolean createUserTable()
	{
		String sql = "CREATE TABLE BENUTZER " +
			"(ID	INTEGER	 PRIMARY KEY AUTOINCREMENT NOT NULL," +
			" LOGIN CHAR(15) NOT NULL UNIQUE," +
			" PASSWORT		 TEXT     NOT NULL, " + 
			" NAME			 TEXT     NOT NULL, " + 
			" VORNAME		 TEXT     NOT NULL, " +
			" MAIL   		 TEXT     NOT NULL, " + 
			" TYP			 INT      NOT NULL)";
		return this.executeStatement(sql);
	 }

	public boolean createDocTypeTable()
	{
		String sql = "CREATE TABLE DOCTORS " +
			"(ID	INTEGER	PRIMARY KEY	NOT NULL," + 
			"SPEZIALGEBIET	TEXT	NOT NULL UNIQUE)";
		return this.executeStatement(sql);
	}

	public boolean insertUser(String login, String pw, String name, String vorname, String mail)
	{
		return insertUser(login,pw,name,vorname,3,mail);
	}

	public boolean insertAdmin(String login, String pw, String name, String vorname, String mail)
	{
		return insertUser(login,pw,name,vorname,1, mail);
	}

	public boolean insertDoc(String login, String pw, String name, String vorname, String spez, String mail)
	{
		boolean b = insertUser(login,pw,name,vorname,2, mail);
		if(!b)	return false;
		int userid = getID(login);
		b = insertDoc(userid,spez);
		if(!b)	deleteUser(userid);
		return b;
	}

	public boolean insertDoc(int id, String spez)
	{
		String sql = "INSERT INTO DOCTORS(ID, SPEZIALGEBIET)" +
			"VALUES (" +
				"'" + id + "'," +
				"'" + spez + "')";

		return this.executeStatement(sql);
	}

	public boolean insertUser(String login, String pw, String name, String vorname, int typ, String mail)
	{
		
		String sql = "INSERT INTO BENUTZER(LOGIN, PASSWORT, NAME, VORNAME, TYP, MAIL)" +
			"VALUES (" +
				"'" + login + "'," + 
				"'" + pw + "'," + 
				"'" + name + "'," + 
				"'" + vorname + "'," + 
				"'" + typ + "'," +
				"'" + mail + "')";

		return this.executeStatement(sql);
	}

	public boolean executeStatement(String sql){
		    Statement stmt = null;
		    try {
		      stmt = c.createStatement();
		      stmt.executeUpdate(sql);
		      stmt.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      return false;
		    }
		    return true;
	 }

	public boolean deleteUser(int id)
	{
		String sql = "DELETE FROM BENUTZER WHERE ID="+id;
		String sql2 = "DELETE FROM DOCTORS WHERE ID="+id;

		return (this.executeStatement(sql2)||this.executeStatement(sql));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			ResultSet rs = stmt.executeQuery("SELECT ID FROM BENUTZER WHERE LOGIN="+login);
			rs.next();
			id = rs.getInt("id");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			ResultSet rs = stmt.executeQuery("SELECT SPEZIALGEBIET FROM DOCTORS WHERE ID="+id);
			rs.next();
			spez = rs.getString("spezialgebiet");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
		return spez;
	}
}
