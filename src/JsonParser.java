import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParser {

	private static String itemName = "";
	
	public JsonParser(String itemName) {
		setItemName(itemName);
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
    public static String getJson(String jsonStr, String item) {
    	JSONObject jsonObject = new JSONObject();
    	try {
    		jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
    	}
    	catch(ParseException pe){
    		pe.printStackTrace();
    	}
    	
    	return jsonObject.get(item).toString();
    }

    public static String getJsonData(String jsonStr, String item) {

    	return getJson(getJson(jsonStr, itemName), item);
    }

    public static String getJsonDataArr(String jsonStr, int idx, String item) {
    	JSONObject jsonObject = new JSONObject();
    	try {
    		jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
    	}
    	catch(ParseException pe){
    		pe.printStackTrace();
    	}
    	
    	JSONArray jsonArray = (JSONArray)jsonObject.get(itemName);
    	
    	String result = "";
    	if(jsonArray.size()>0) {
    		JSONObject jsonObjectArray = (JSONObject)jsonArray.get(idx);
    		result = jsonObjectArray.get(item).toString();
    	}
		System.out.println("====================");
		System.out.println(jsonArray.size());
		System.out.println("====================");
    	
    	return result;
    }

    public static int getJsonArrSize(String jsonStr) {
    	JSONObject jsonObject = new JSONObject();
    	try {
    		jsonObject = (JSONObject) new JSONParser().parse(jsonStr);
    	}
    	catch(ParseException pe){
    		pe.printStackTrace();
    	}
    	
    	return jsonObject.size();
    }
    
}

