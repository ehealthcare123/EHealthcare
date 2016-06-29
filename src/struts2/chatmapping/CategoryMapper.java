package struts2.chatmapping;

import java.util.HashMap;

public class CategoryMapper {
	private static HashMap<Integer, String> choosencategory = new HashMap<Integer, String>();
	
	public synchronized static void addCategory(Integer sessionID, String category){
		choosencategory.put(sessionID, category);
	}
	
	public static String getCategory(Integer sessionID){
		return choosencategory.get(sessionID);
	}
	
	public synchronized static void removeCategory(Integer sessionID){
		choosencategory.remove(sessionID);
	}
}
