/**
 * 
 */
package hanikami.utility;


/**
 * @author hanikami
 *
 */
public class Util {

	public static void printLog(String log) {
		System.out.println(DateUtil.getDateTime("HH:mm:ss")+"-"+String.valueOf(log));
	}

	public static void printLog(String key, Object log) {
		System.out.println(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss")+"-["+key+"]="+String.valueOf(log));
	}
}
