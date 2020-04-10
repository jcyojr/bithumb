import java.util.Properties;

import hanikami.utility.CommonUtil;
import hanikami.utility.DateUtil;
import hanikami.utility.Util;


public class BithumbTest {
	
	private static String propertyFileName = "D:/project/workspace/java/bithumb/bin/Bithumb.properties";
	private static Properties properties = null;

	private static String last_order_id = "";

	private static int prop_reload_time = 0;
	private static int flag_market_trade = 0;
	private static int flag_stop_loss = 0;
	private static int flag_safe_trade = 0;
	private static int flag_start_sell = 0;	

	private static float ratio_tax = 0F;
	private static float ratio_fee = 0F;
	private static float ratio_buffer = 0F;
	private static float ratio_benefit = 0F;
	private static float ratio_cancel = 0F;
	private static float ratio_gap_top = 0F;
	private static float ratio_gap_bottom = 0F;
	private static float ratio_gap_trade = 0F;
	private static float ratio_gap_limit = 0F;
	private static float ratio_stop_loss = 0F;
	private static long limit_buy_total = 0L;
	private static long limit_buy_trade = 0L;
	private static int avr_force_line = 0;
	
	public BithumbTest() {
		
		properties = CommonUtil.getProperties(propertyFileName);
		last_order_id = properties.getProperty("LAST_ORDER_ID");
		prop_reload_time = Integer.parseInt(properties.getProperty("PROP_RELOAD_TIME"));
		avr_force_line = Integer.parseInt(properties.getProperty("CONST_AVR_FORCE_LINE"));
	}
	
    public static void main(String args[]) {

    	BithumbTest bm = new BithumbTest();
    	BithumbApi bapi = new BithumbApi();
    	JsonParser jp = new JsonParser("data");

		String result = "";
		String order_id = "";
/*   	
		result = bapi.getTradePlaceBuy("0.0060", "18000000");
		if(jp.getJson(result, "status").equals("0000")) {
			order_id = jp.getJson(result, "order_id");
		}
*/		
		System.out.println("---------------------");
		result = bapi.getInfoOrders("1513784974505954", "bid");
		System.out.println("---------------------");
		Util.printLog("result     ", jp.getJson(result, "message"));
		
		Util.printLog("result     ", jp.getJsonDataArr(result, 0, "status"));

	}	
			
			
}
