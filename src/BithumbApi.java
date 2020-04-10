import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Properties;

import hanikami.api.bithumb.ApiClient;
import hanikami.utility.CommonUtil;

public class BithumbApi {
	
	private static String propertyFileName = "./Bithumb.properties";
	private static Properties properties = null;

	private static String api_connect_key = "";
	private static String api_secret_key = "";

	private static String currency_order = "";
	private static String currency_payment = "";

	private static String withdrawal_bank_name = "";
	private static String withdrawal_bank_account = "";

	public BithumbApi() {
		
		properties = CommonUtil.getProperties(propertyFileName);
		api_connect_key = properties.getProperty("API_CONNECT_KEY");
		api_secret_key = properties.getProperty("API_SECRET_KEY");

		currency_order = properties.getProperty("CURRENCY_ORDER");
		currency_payment = properties.getProperty("CURRENCY_PAYMENT");

		withdrawal_bank_name = properties.getProperty("WITHDRAWAL_BANK_NAME");
		withdrawal_bank_account = properties.getProperty("WITHDRAWAL_BANK_ACCOUNT");
}

	public String callApi(String uri, HashMap<String, String> rgParams) {
    	String result = "";

		ApiClient api = new ApiClient(api_connect_key, api_secret_key);
		
		try {
			result = api.callApi(uri, rgParams);
		}
		catch (SocketTimeoutException ste) {
		    ste.printStackTrace();
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		
		return result;
	}
	
	
    /* bithumb 거래소 마지막 거래 정보
	{
		"status": "0000", //결과 상태 코드 (정상 : 0000, 정상이외 코드는 에러 코드 참조)
		"data": {
			"opening_price" : "504000",
			"closing_price" : "505000",
			"min_price" : "504000",
			"max_price" : "516000",
			"average_price" : "509533.3333",
			"units_traded" : "14.71960286",
			"volume_1day" : "14.71960286",
			"volume_7day" : "15.81960286",
			"buy_price" : "505000",
			"sell_price" : "504000",
			"date" : 1417141032622
		}
	}	
	*/
    public String getPublicTicker(String currency) {
    	//currency : BTC(기본값), ETH, DASH, LTC, ETC, XRP, BCH, XMR, ZEC, QTUM, ALL(전체)
    	String uri = properties.getProperty("URI_PUBLIC_TICKER");
		HashMap<String, String> rgParams = new HashMap<String, String>();

    	return callApi(uri+currency, rgParams);
    }

    public String getPublicTicker() {
    	String currency = currency_order;

    	return getPublicTicker(currency);
    }
    
    
    /* bithumb 거래소 판/구매 등록 대기 또는 거래 중 내역 정보
    */
    public String getPublicOrderbook(String currency, String group_orders, String count) {
    	//currency : BTC(기본값), ETH, DASH, LTC, ETC, XRP, BCH, XMR, ZEC, QTUM, ALL(전체)
    	String uri = properties.getProperty("URI_PUBLIC_ORDERBOOK");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("group_orders", group_orders);
		rgParams.put("count", count);
    	
    	return callApi(uri+currency, rgParams);
    }
    
    public String getPublicOrderbook() {
    	String currency = currency_order;
    	String group_orders = "1";
    	String count = "20";
    	
    	return getPublicOrderbook(currency, group_orders, count);
    }

    
    /* bithumb 거래소 거래 체결 완료 내역
    */
    public String getPublicRecentTransactions(String currency, String offset, String count) {
    	//currency : BTC(기본값), ETH, DASH, LTC, ETC, XRP, BCH, XMR, ZEC, QTUM
    	String uri = properties.getProperty("URI_PUBLIC_RECENT_TRANSACTIONS");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("offset", offset);
		rgParams.put("count", count);
    	
    	return callApi(uri+currency, rgParams);
    }
	
    public String getPublicRecentTransactions() {
    	String currency = currency_order;
    	String offset = "0";
    	String count = "20";
    	
    	return getPublicRecentTransactions(currency, offset, count);
    }

    
    /* bithumb 거래소 회원 정보
    */
    public String getInfoAccount(String apiKey, String secretKey, String currency) {
    	String uri = properties.getProperty("URI_INFO_ACCOUNT");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("currency", currency);
    	
    	return callApi(uri, rgParams);
    }

    public String getInfoAccount() {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String currency = currency_order;
    	
    	return getInfoAccount(apiKey, secretKey, currency);
    }

    
	/* bithumb 거래소 회원 지갑 정보
	{
		"status" : "0000", //결과 상태 코드 (정상 : 0000, 정상이외 코드는 에러 코드 참조)
		"data" : {
			"total_<currency>" : "665.40127447", //전체 currency (btc, eth, dash, ltc, etc, xrp, bch, xmr, zec, qtum 등)
			"total_krw" : "305507280", //전체 krw
			"in_use_<currency>" : "127.43629364", //사용중 currency (btc, eth, dash, ltc, etc, xrp, bch, xmr, zec, qtum 등)
			"in_use_krw" : "8839047.0000000000", //사용중 krw
			"available_<currency>" : "537.96498083", //사용가능 currency (btc, eth, dash, ltc, etc, xrp, bch, xmr, zec, qtum 등)
			"available_krw" : "294932685.000000000000", //사용가능 krw
			"misu_<currency>" : "0.0", //신용거래 currency (btc, eth, dash, ltc, etc, xrp, bch, xmr, zec, qtum 등)
			"misu_krw" : "-528905", //신용거래 krw
			"xcoin_last" : "505000", //마지막 거래 체결 금액
			"misu_depo_krw" : "1735548.000000000000" //미수 증거금
		}
	}
	*/
	public String getInfoBalance(String apiKey, String secretKey, String currency) {
    	String uri = properties.getProperty("URI_INFO_BALANCE");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("currency", currency);
    	
    	return callApi(uri, rgParams);
    }

    public String getInfoBalance() {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String currency = currency_order;
    	
    	return getInfoBalance(apiKey, secretKey, currency);
    }

    
    /* bithumb 거래소 회원 입금 주소
    */
    public String getInfoWalletAddress(String apiKey, String secretKey, String currency) {
    	String uri = properties.getProperty("URI_INFO_WALLET_ADDRESS");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("currency", currency);
    	
    	return callApi(uri, rgParams);
    }

    public String getInfoWalletAddress() {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String currency = currency_order;
    	
    	return getInfoWalletAddress(apiKey, secretKey, currency);
    }
    
    
    /* 회원 마지막 거래 정보
    */
    public String getInfoTicker(String apiKey, String secretKey, String order_currency, String payment_currency) {
    	String uri = properties.getProperty("URI_INFO_TICKER");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("order_currency", order_currency);
		rgParams.put("payment_currency", payment_currency);
    	
    	return callApi(uri, rgParams);
    }

    public String getInfoTicker() {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String order_currency = currency_order;
    	String payment_currency = currency_payment;
    	
    	return getInfoTicker(apiKey, secretKey, order_currency, payment_currency);
    }
    
    
    /* 판/구매 거래 주문 등록 또는 진행 중인 거래
    */
    public String getInfoOrders(String apiKey, String secretKey, String order_id, String type, String count, String after, String currency) {
    	String uri = properties.getProperty("URI_INFO_ORDERS");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("order_id", order_id);
		rgParams.put("type", type);
		rgParams.put("count", count);
		rgParams.put("after", after);
		rgParams.put("currency", currency);
    	
    	return callApi(uri, rgParams);
    }

    public String getInfoOrders(String order_id, String type) {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String count = "100";
    	//String after = String.valueOf(System.currentTimeMillis());//특정일시 이후 조회시 사용
    	String after = "";
    	String currency = currency_order;
    	
    	return getInfoOrders(apiKey, secretKey, order_id, type, count, after, currency);
    }

    
    /* bithumb 회원 판/구매 체결 내역
    */
    public String getInfoOrderDetail(String apiKey, String secretKey, String order_id, String type, String currency) {
    	String uri = properties.getProperty("URI_INFO_ORDER_DETAIL");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("order_id", order_id);
		rgParams.put("type", type);
		rgParams.put("currency", currency);
    	
    	return callApi(uri, rgParams);
    }

    public String getInfoOrderDetail(String order_id, String type) {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String currency = currency_order;
    	
    	return getInfoOrderDetail(apiKey, secretKey, order_id, type, currency);
    }

    
    /* 회원 거래 내역
    */
    public String getInfoUserTransactions(String apiKey, String secretKey, String offset, String count, String seatchGb, String currency) {
    	String uri = properties.getProperty("URI_INFO_USER_TRANSACTIONS");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("offset", offset);
		rgParams.put("count", count);
		rgParams.put("seatchGb", seatchGb);
		rgParams.put("currency", currency);
    	
    	return callApi(uri, rgParams);
    }

    public String getInfoUserTransactions(String seatchGb) {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String offset = "0";
    	String count = "20";
    	String currency = currency_order;
    	
    	return getInfoUserTransactions(apiKey, secretKey, offset, count, seatchGb, currency);
    }

    
    /* bithumb 회원 판/구매 거래 주문 등록 및 체결
    */
    public String getTradePlace(String apiKey, String secretKey, String order_currency, String payment_currency, String units, String price, String type, String misu) {
    	String uri = properties.getProperty("URI_TRADE_PLACE");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("order_currency", order_currency);
		rgParams.put("payment_currency", payment_currency);
		rgParams.put("units", units);
		rgParams.put("price", price);
		rgParams.put("type", type);
		rgParams.put("misu", misu);
    	
    	return callApi(uri, rgParams);
    }

    public String getTradePlace(String units, String price, String type) {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String order_currency = currency_order;
    	String payment_currency = currency_payment;
    	String misu = "N";
    	
    	return getTradePlace(apiKey, secretKey, order_currency, payment_currency, units, price, type, misu);
    }

    public String getTradePlaceBuy(String units, String price) {
    	String type = "bid";
    	
    	return getTradePlace(units, price, type);
    }

    public String getTradePlaceSell(String units, String price) {
    	String type = "ask";
    	
    	return getTradePlace(units, price, type);
    }

    
    /* bithumb 회원 판/구매 거래 취소
    */
    public String getTradeCancel(String apiKey, String secretKey, String type, String order_id, String currency) {
    	String uri = properties.getProperty("URI_TRADE_CANCEL");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("type", type);
		rgParams.put("order_id", order_id);
		rgParams.put("currency", currency);
    	
    	return callApi(uri, rgParams);
    }

    public String getTradeCancel(String type, String order_id) {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String currency = currency_order;
    	
    	return getTradeCancel(apiKey, secretKey, type, order_id, currency);
    }

    
    /* bithumb 회원 Currency 출금
    */
    public String getTradeBtcWithdrawal() {
    	String uri = properties.getProperty("URI_TRADE_BTC_WITHDRAWAL");
		HashMap<String, String> rgParams = new HashMap<String, String>();
    	
    	return callApi(uri, rgParams);
    }
    
    
    /* bithumb 회원 KRW 출금 신청
    */
    public String getTradeKrwWithdrawal(String apiKey, String secretKey, String bank, String account, String price) {
    	String uri = properties.getProperty("URI_TRADE_KRW_WITHDRAWAL");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("bank", bank);
		rgParams.put("account", account);
		rgParams.put("price", price);
    	
    	return callApi(uri, rgParams);
    }


    public String getTradeKrwWithdrawal(String price) {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String bank = withdrawal_bank_name;
    	String account = withdrawal_bank_account;
    	
    	return getTradeKrwWithdrawal(apiKey, secretKey, bank, account, price);
    }

    
    /* bithumb 회원 KRW 입금 가상계좌 정보 요청
    */
    public String getTradeKrwDeposit(String apiKey, String secretKey) {
    	String uri = properties.getProperty("URI_TRADE_KRW_DEPOSIT");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
    	
    	return callApi(uri, rgParams);
    }

    public String getTradeKrwDeposit() {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	
    	return getTradeKrwDeposit(apiKey, secretKey);
    }

    
    /* 시장가 구매
    */
    public String getTradeMarketBuy(String apiKey, String secretKey, String units, String currency) {
    	String uri = properties.getProperty("URI_TRADE_MARKET_BUY");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("units", units);
		rgParams.put("currency", currency);
    	
    	return callApi(uri, rgParams);
    }

    public String getTradeMarketBuy(String units) {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String currency = currency_order;
    	
    	return getTradeMarketBuy(apiKey, secretKey, units, currency);
    }

  
    /* 시장가판매
    */
    public String getTradeMarketSell(String apiKey, String secretKey, String units, String currency) {
    	String uri = properties.getProperty("URI_TRADE_MARKET_SELL");
		HashMap<String, String> rgParams = new HashMap<String, String>();
		rgParams.put("apiKey", apiKey);
		rgParams.put("secretKey", secretKey);
		rgParams.put("units", units);
		rgParams.put("currency", currency);
    	
    	return callApi(uri, rgParams);
    }

    public String getTradeMarketSell(String units) {
    	String apiKey = api_connect_key;
    	String secretKey = api_secret_key;
    	String currency = currency_order;
    	
    	return getTradeMarketSell(apiKey, secretKey, units, currency);
    }

    
	public String getErrCode(String errCode) {
		String result = "";
		
		switch(errCode) {
		case "0000":
			result="정상 거래";
			break;
		case "5100":
			result="Bad Request";
			break;
		case "5200":
			result="Not Member";
			break;
		case "5300":
			result="Invalid Apikey";
			break;
		case "5302":
			result="Database Fail";
			break;
		case "5400":
			result="Invalid Parameter";
			break;
		case "5500":
			result="CUSTOM NOTICE (상황별 에러 메시지 출력)";
			break;
		case "5600":
			result="Unknown Error";
			break;
		case "5900":
			result="정상 거래";
			break;
		default:
			result="알 수 없는 오류";
			break;		
		}
				
		return result;
	}
}

