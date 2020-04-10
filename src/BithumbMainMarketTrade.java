import java.util.Properties;

import hanikami.utility.CommonUtil;
import hanikami.utility.DateUtil;
import hanikami.utility.Util;


public class BithumbMainMarketTrade {
	
	private static String propertyFileName = "D:/project/workspace/java/bithumb/bin/Bithumb.properties";
	private static Properties properties = null;

	private static int const_reload_time = 0;
	private static float ratio_tax = 0F;
	private static float ratio_fee = 0F;
	private static float ratio_buffer = 0F;
	private static float ratio_benefit = 0F;
	private static float ratio_cancel = 0F;
	private static float ratio_gap_top = 0F;
	private static float ratio_gap_bottom = 0F;
	private static float ratio_gap_trade = 0F;
	private static long limit_buy_total = 0L;
	private static long limit_buy_trade = 0L;
	
	public BithumbMainMarketTrade() {
		
		properties = CommonUtil.getProperties(propertyFileName);
		const_reload_time = Integer.parseInt(properties.getProperty("CONST_RELOAD_TIME"));
	}
	
    public static void main(String args[]) {
		
    	BithumbMainMarketTrade bm = new BithumbMainMarketTrade();
    	BithumbApi bapi = new BithumbApi();
    	JsonParser jp = new JsonParser("data");
   	
    	String result = "";
    	
		boolean stat_buy = false;
		boolean stat_order = false;;
		boolean point_top = false;
		boolean point_bottom = false;
		
		result = bapi.getInfoBalance();
		long available_krw = Long.parseLong(jp.getJsonData(result, "available_krw"));
		long buy_coin_price = 0L;
		float buy_coin_units = 0F;
		
		long coin_price_current = 0L;
		long coin_price_top = 0L;
		long coin_price_bottom = 0L;
		
		String order_id = "";
		String order_type = "";

		int reloadtime = 0;
		while(true) {
    		DateUtil.waitTime(1000L);
    		reloadtime++;
    		if(reloadtime>const_reload_time) {
    			ratio_tax = Float.parseFloat(properties.getProperty("CONST_RATIO_TAX"));
    			ratio_fee = Float.parseFloat(properties.getProperty("CONST_RATIO_FEE"));
    			ratio_buffer = Float.parseFloat(properties.getProperty("CONST_RATIO_BUFFER"));
    			ratio_benefit = Float.parseFloat(properties.getProperty("CONST_RATIO_BENEFIT"));
    			ratio_cancel = Float.parseFloat(properties.getProperty("CONST_RATIO_CANCEL"));
    			ratio_gap_top = Float.parseFloat(properties.getProperty("CONST_RATIO_GAP_TOP"));
    			ratio_gap_bottom = Float.parseFloat(properties.getProperty("CONST_RATIO_GAP_BOTTOM"));
    			limit_buy_total = Long.parseLong(properties.getProperty("CONST_LIMIT_BUY_TOTAL"));
    			limit_buy_trade = Long.parseLong(properties.getProperty("CONST_LIMIT_BUY_TRADE"));
    			reloadtime=0;
    		}
			Util.printLog("available_krw     ", available_krw);
			Util.printLog("coin_price_top    ", coin_price_top);
			Util.printLog("coin_price_current", coin_price_current);
			Util.printLog("coin_price_bottom ", coin_price_bottom);
			
			if(stat_buy) {//sell
	    		result = bapi.getPublicTicker();
	    		coin_price_current = (long)Float.parseFloat(jp.getJsonData(result, "buy_price"));
				if(coin_price_top==0)coin_price_top=coin_price_current;
				if(coin_price_bottom==0)coin_price_bottom=coin_price_current;
				
				//if(top_point && cur_coin<top_coin-top_coin*top_gap) {
				if(coin_price_current<coin_price_top-coin_price_top*ratio_gap_top) {
					if(coin_price_top-coin_price_top*ratio_gap_top >= buy_coin_price*ratio_benefit) {
						
						result = bapi.getTradeMarketSell(Float.toString(buy_coin_units));
						if(jp.getJson(result, "status").equals("0000")) {
							order_id = jp.getJson(result, "order_id");
							buy_coin_price+=(long)(buy_coin_units*coin_price_current);
							buy_coin_units=0F;
							stat_order=true;
							order_type="ask";
							coin_price_top=0L;
							point_top=false;
						}
					}
				}
			}
			else {//buy
	    		result = bapi.getPublicTicker();
	    		coin_price_current = (long)Float.parseFloat(jp.getJsonData(result, "sell_price"));
				if(coin_price_top==0)coin_price_top=coin_price_current;
				if(coin_price_bottom==0)coin_price_bottom=coin_price_current;

				//if(btm_point && cur_coin<buy_limit && cur_coin>btm_coin+btm_coin*btm_gap) {
				if(coin_price_current<limit_buy_trade && coin_price_current>coin_price_bottom+coin_price_bottom*ratio_gap_bottom) {
					long cost = available_krw;
					if(cost>limit_buy_total)cost=limit_buy_total;
					
					buy_coin_price=(long)(cost-cost*(ratio_fee+ratio_buffer));				
					buy_coin_units = (float)buy_coin_price/(float)coin_price_current;
					buy_coin_units = (float)((long)(buy_coin_units*100000000))/100000000;					

					result = bapi.getTradeMarketBuy(Float.toString(buy_coin_units));
					if(jp.getJson(result, "status").equals("0000")) {
						order_id = jp.getJson(result, "order_id");
						available_krw-=((long)(cost*buy_coin_units)+cost*ratio_fee);
						stat_order=true;
						order_type="bid";
						coin_price_bottom=0L;
						point_bottom=false;
					}
				}
			}
			
			if(coin_price_top<coin_price_current) {
				coin_price_top = coin_price_current;
				point_top = true;
				point_bottom = false;
			}
			else {
				point_top = false;
			}
			
			if(coin_price_bottom>coin_price_current) {
				coin_price_bottom = coin_price_current;
				point_bottom = true;
				point_top = false;
			}
			else {
				point_bottom = false;
			}
		}
    }
}
