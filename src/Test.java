public class Test {

	public static void main(String args[]) {
		
		boolean own = false;
		boolean top_point = false, btm_point = false;
		
		float tax = 0.0015F;
		float benefit = 0.01F;
		float top_gap = 0.02F, btm_gap = 0.02F;
		
		long own_krw = 500000L;
		long own_limit = 1000000L;
		
		long owwn_coin = 0L;
		float own_coin_cnt = 0F;
		
		long cur_coin = 0L, top_coin = 0L, btm_coin = 0L;
		
		if(top_coin==0)top_coin=cur_coin;
		if(btm_coin==0)btm_coin=cur_coin;
	
		if(own) {//sell
			
		}
		else {//buy
			if(btm_point && cur_coin>btm_coin*btm_gap) {
				long cost = own_krw;
				if(cost>own_limit)cost=own_limit;
				own_coin_cnt = cost%cur_coin;
			}
		}
		
		if(top_coin<cur_coin) {
			top_coin = cur_coin;
			btm_coin = 0L;
			top_point = true;
			btm_point = false;
		}
		else {
			top_point = false;
		}
		
		if(btm_coin>cur_coin) {
			btm_coin = cur_coin;
			top_coin = 0L;
			btm_point = true;
			top_point = false;
		}
		else {
			btm_point = false;
		}
		
	}
}
