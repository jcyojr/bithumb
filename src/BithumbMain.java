import java.util.Properties;

import hanikami.utility.CommonUtil;
import hanikami.utility.DateUtil;
import hanikami.utility.Util;


public class BithumbMain {
	
	private static String propertyFileName = "./Bithumb.properties";
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
	
	public BithumbMain() {
		
		properties = CommonUtil.getProperties(propertyFileName);
		last_order_id = properties.getProperty("LAST_ORDER_ID");
		prop_reload_time = Integer.parseInt(properties.getProperty("PROP_RELOAD_TIME"));
		avr_force_line = Integer.parseInt(properties.getProperty("CONST_AVR_FORCE_LINE"));
	}
	
    public static void main(String args[]) {

    	BithumbMain bm = new BithumbMain();
    	BithumbApi bapi = new BithumbApi();
    	JsonParser jp = new JsonParser("data");

		boolean available_buy = false;
		boolean available_sell = false;
		boolean stat_order = false;
		boolean point_top = false;
		boolean point_bottom = false;
		boolean set_coin_price_current_avr = false;
		
		long available_krw = 0L;
		long buy_coin_price = 0L;
		float buy_coin_units = 0F;
		
		long[] coin_price_current_arr = new long[avr_force_line];
		long coin_price_current_avr = 0L;
		int arrCnt = 0;
		
		long coin_price_current = 0L;
		long coin_price_top = 0L;
		long coin_price_bottom = 0L;
		long coin_price_limit = 0L;
		int coin_price_limit_cnt = 0;

		String result = "";
		String order_id = "";
		String order_type = "";
    	
		if(!(last_order_id==null || last_order_id.equals(""))) {
			
		}

		available_buy = true;
		int reloadtime = 0;
		while(true) {
			Util.printLog("--------------------------------------------------");
    		DateUtil.waitTime(1000L);
    		reloadtime++;
    		if(reloadtime>prop_reload_time) {
    			flag_market_trade = Integer.parseInt(properties.getProperty("FLAG_MARKET_TRADE"));
    			flag_stop_loss = Integer.parseInt(properties.getProperty("FLAG_STOP_LOSS"));
    			flag_safe_trade = Integer.parseInt(properties.getProperty("FLAG_SAFE_TRADE"));
    			flag_start_sell = Integer.parseInt(properties.getProperty("FLAG_START_SELL"));

    			ratio_tax = Float.parseFloat(properties.getProperty("CONST_RATIO_TAX"));
    			ratio_fee = Float.parseFloat(properties.getProperty("CONST_RATIO_FEE"));
    			ratio_buffer = Float.parseFloat(properties.getProperty("CONST_RATIO_BUFFER"));
    			ratio_benefit = Float.parseFloat(properties.getProperty("CONST_RATIO_BENEFIT"));
    			ratio_cancel = Float.parseFloat(properties.getProperty("CONST_RATIO_CANCEL"));
    			ratio_gap_top = Float.parseFloat(properties.getProperty("CONST_RATIO_GAP_TOP"));
    			ratio_gap_bottom = Float.parseFloat(properties.getProperty("CONST_RATIO_GAP_BOTTOM"));
    			ratio_gap_trade = Float.parseFloat(properties.getProperty("CONST_RATIO_GAP_TRADE"));
    			ratio_gap_limit = Float.parseFloat(properties.getProperty("CONST_RATIO_GAP_LIMIT"));
    			limit_buy_total = Long.parseLong(properties.getProperty("CONST_LIMIT_BUY_TOTAL"));
    			limit_buy_trade = Long.parseLong(properties.getProperty("CONST_LIMIT_BUY_TRADE"));

    			reloadtime=0;
    		}
    		    		
    		result = bapi.getInfoBalance();
    		if(!jp.getJson(result, "status").equals("0000")) {
    			Util.printLog("message", jp.getJson(result, "message"));
    			continue;
    		}
			available_krw = Long.parseLong(jp.getJsonData(result, "available_krw"));
    		
			Util.printLog("available_krw     ", available_krw);
			Util.printLog("coin_price_top    ", coin_price_top);
			Util.printLog("coin_price_current", coin_price_current);
			Util.printLog("coin_price_bottom ", coin_price_bottom);
			Util.printLog("buy_coin_price    ", buy_coin_price);
			Util.printLog("sell_coin_price   ", (long)(buy_coin_price+buy_coin_price*ratio_benefit));
			Util.printLog("coin_price_limit  ", coin_price_limit);
			
			if(stat_order){
				result = bapi.getInfoOrders(order_id, order_type);
				if(jp.getJson(result, "status").equals("0000")) {
					
					if(Math.abs(buy_coin_price-coin_price_current)>coin_price_current*ratio_cancel){
						result = bapi.getTradeCancel(order_id, order_type);
						if(jp.getJson(result, "status").equals("0000")) {
							stat_order = false;
							if(order_type.equals("bid")) {
								available_buy = true;
								available_sell = false;
							}
							else {
								available_buy = false;
								available_sell = true;
							}
						}
					}										
					
					continue;
				}

				result = bapi.getInfoOrderDetail(order_id, order_type);
				if(jp.getJson(result, "status").equals("0000")){
					stat_order = false;
					if(order_type.equals("bid")) {
						available_buy = false;
						available_sell = true;
					}
					else{
						available_buy = true;
						available_sell = false;
					}
				}
			}
			
			if(available_buy) {
	    		result = bapi.getPublicTicker();
	    		coin_price_current = (long)Float.parseFloat(jp.getJsonData(result, "sell_price"));
				if(coin_price_bottom==0)coin_price_bottom=coin_price_current;

				if(
						(coin_price_limit==0 || coin_price_current<coin_price_limit)
						&&coin_price_current<limit_buy_trade
						&& coin_price_current>coin_price_bottom+coin_price_bottom*ratio_gap_bottom) {
					long cost = available_krw;
					if(cost>limit_buy_total)cost=limit_buy_total;
					
					buy_coin_price = coin_price_current+(long)(coin_price_current*ratio_gap_trade);				
					buy_coin_units = (float)((long)(cost-cost*(ratio_fee+ratio_buffer)))/(float)coin_price_current;
					buy_coin_units = (float)((long)(buy_coin_units*10000))/10000;					

					result = bapi.getTradePlaceBuy(Float.toString(buy_coin_units), Long.toString(buy_coin_price));
					if(jp.getJson(result, "status").equals("0000")) {
						order_id = jp.getJson(result, "order_id");
						//available_krw-=((long)(cost*buy_coin_units)+cost*ratio_fee);
						stat_order=true;
						order_type="bid";
						coin_price_top=0L;
						coin_price_bottom=0L;
						point_bottom=false;
					}
				}
			}
			
			if(available_sell) {
	    		result = bapi.getPublicTicker();
	    		coin_price_current = (long)Float.parseFloat(jp.getJsonData(result, "buy_price"));
				if(coin_price_top==0)coin_price_top=coin_price_current;
				
				//if(top_point && cur_coin<top_coin-top_coin*top_gap) {
				if(coin_price_current<coin_price_top-coin_price_top*ratio_gap_top
						&& coin_price_current>= buy_coin_price+buy_coin_price*ratio_benefit) {						
					result = bapi.getTradePlaceSell(Float.toString(buy_coin_units), Long.toString(coin_price_current-(long)(coin_price_current*ratio_gap_trade)));
					if(jp.getJson(result, "status").equals("0000")) {
						order_id = jp.getJson(result, "order_id");
						buy_coin_price+=(long)(buy_coin_units*coin_price_current);
						buy_coin_units=0F;
						stat_order=true;
						order_type="ask";
						coin_price_limit = coin_price_current-(long)((coin_price_current-coin_price_bottom)*ratio_gap_limit);
						coin_price_top=0L;
						coin_price_bottom=0L;
						point_top=false;
					}
				}
			}
			
			if(avr_force_line>0) {
				coin_price_current_arr[arrCnt] = coin_price_current;
				if((arrCnt+1)==coin_price_current_arr.length) {
					long sum = 0L;
					for(int i=0;i<coin_price_current_arr.length;i++)sum+=coin_price_current_arr[i];
					coin_price_current_avr = sum/coin_price_current_arr.length;

					set_coin_price_current_avr = true;
					arrCnt=0;

					if(coin_price_top<coin_price_current_avr) {
						coin_price_top = coin_price_current_avr;
						point_top = true;
						point_bottom = false;
					}
					else {
						point_top = false;
					}
					
					if(coin_price_bottom>coin_price_current_avr) {
						coin_price_bottom = coin_price_current_avr;
						point_bottom = true;
						point_top = false;
					}
					else {
						point_bottom = false;
					}
					
					if(coin_price_limit_cnt>30) {
						coin_price_limit_cnt=0;
						coin_price_limit = 0L;
					}
					else {
						coin_price_limit_cnt++;
					}
					
				}
				else {
					set_coin_price_current_avr = false;
					arrCnt++;
				}
			}
			else {
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
}
