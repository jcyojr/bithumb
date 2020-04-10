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
	
	
    /* bithumb �ŷ��� ������ �ŷ� ����
	{
		"status": "0000", //��� ���� �ڵ� (���� : 0000, �����̿� �ڵ�� ���� �ڵ� ����)
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
    	//currency : BTC(�⺻��), ETH, DASH, LTC, ETC, XRP, BCH, XMR, ZEC, QTUM, ALL(��ü)
    	String uri = properties.getProperty("URI_PUBLIC_TICKER");
		HashMap<String, String> rgParams = new HashMap<String, String>();

    	return callApi(uri+currency, rgParams);
    }

    public String getPublicTicker() {
    	String currency = currency_order;

    	return getPublicTicker(currency);
    }
    
    
    /* bithumb �ŷ��� ��/���� ��� ��� �Ǵ� �ŷ� �� ���� ����
    */
    public String getPublicOrderbook(String currency, String group_orders, String count) {
    	//currency : BTC(�⺻��), ETH, DASH, LTC, ETC, XRP, BCH, XMR, ZEC, QTUM, ALL(��ü)
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

    
    /* bithumb �ŷ��� �ŷ� ü�� �Ϸ� ����
    */
    public String getPublicRecentTransactions(String currency, String offset, String count) {
    	//currency : BTC(�⺻��), ETH, DASH, LTC, ETC, XRP, BCH, XMR, ZEC, QTUM
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

    
    /* bithumb �ŷ��� ȸ�� ����
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

    
	/* bithumb �ŷ��� ȸ�� ���� ����
	{
		"status" : "0000", //��� ���� �ڵ� (���� : 0000, �����̿� �ڵ�� ���� �ڵ� ����)
		"data" : {
			"total_<currency>" : "665.40127447", //��ü currency (btc, eth, dash, ltc, etc, xrp, bch, xmr, zec, qtum ��)
			"total_krw" : "305507280", //��ü krw
			"in_use_<currency>" : "127.43629364", //����� currency (btc, eth, dash, ltc, etc, xrp, bch, xmr, zec, qtum ��)
			"in_use_krw" : "8839047.0000000000", //����� krw
			"available_<currency>" : "537.96498083", //��밡�� currency (btc, eth, dash, ltc, etc, xrp, bch, xmr, zec, qtum ��)
			"available_krw" : "294932685.000000000000", //��밡�� krw
			"misu_<currency>" : "0.0", //�ſ�ŷ� currency (btc, eth, dash, ltc, etc, xrp, bch, xmr, zec, qtum ��)
			"misu_krw" : "-528905", //�ſ�ŷ� krw
			"xcoin_last" : "505000", //������ �ŷ� ü�� �ݾ�
			"misu_depo_krw" : "1735548.000000000000" //�̼� ���ű�
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

    
    /* bithumb �ŷ��� ȸ�� �Ա� �ּ�
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
    
    
    /* ȸ�� ������ �ŷ� ����
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
    
    
    /* ��/���� �ŷ� �ֹ� ��� �Ǵ� ���� ���� �ŷ�
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
    	//String after = String.valueOf(System.currentTimeMillis());//Ư���Ͻ� ���� ��ȸ�� ���
    	String after = "";
    	String currency = currency_order;
    	
    	return getInfoOrders(apiKey, secretKey, order_id, type, count, after, currency);
    }

    
    /* bithumb ȸ�� ��/���� ü�� ����
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

    
    /* ȸ�� �ŷ� ����
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

    
    /* bithumb ȸ�� ��/���� �ŷ� �ֹ� ��� �� ü��
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

    
    /* bithumb ȸ�� ��/���� �ŷ� ���
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

    
    /* bithumb ȸ�� Currency ���
    */
    public String getTradeBtcWithdrawal() {
    	String uri = properties.getProperty("URI_TRADE_BTC_WITHDRAWAL");
		HashMap<String, String> rgParams = new HashMap<String, String>();
    	
    	return callApi(uri, rgParams);
    }
    
    
    /* bithumb ȸ�� KRW ��� ��û
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

    
    /* bithumb ȸ�� KRW �Ա� ������� ���� ��û
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

    
    /* ���尡 ����
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

  
    /* ���尡�Ǹ�
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
			result="���� �ŷ�";
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
			result="CUSTOM NOTICE (��Ȳ�� ���� �޽��� ���)";
			break;
		case "5600":
			result="Unknown Error";
			break;
		case "5900":
			result="���� �ŷ�";
			break;
		default:
			result="�� �� ���� ����";
			break;		
		}
				
		return result;
	}
}

