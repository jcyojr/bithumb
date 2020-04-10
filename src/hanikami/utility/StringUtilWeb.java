/**
 * 
 */
package hanikami.utility;


/**
 * @author hanikami
 *
 */
public class StringUtilWeb extends StringUtil{

	/**
	 * 문자열에 있는 특수문자를 HTML에서 사용가능한 문자열로 변환한다. 
	 * @param string 대상 문자열
	 * @return String
	 */
	public static String convertTextToHtml(String string) {
		String[][] stringArray = {
			{"&", "&amp;"}
			,{"<", "&lt;"}
			,{"/</", "&lt;"}
			,{">", "&gt;"}
			,{"/>/", "&gt;"}
			,{" ", "&nbsp;"}
			,{"\"", "&quot;"}
			,{"\'", "&#39;"}
			,{"\n", "<br>"}
		};

		return convertString(string, stringArray);		
	}

	/**
	 * 문자열에서 특수문자를 제거한다.
	 * @param string 대상 문자열
	 * @return String
	 * */
	public static String removeSpecialChar(String string) {
		String[] stringArray = {"#", "$", "%", "&", "-", "--", "=", ";", "<", ">", "/", "\'", "\"", "\\+", "\\(", "\\)"};
	
		return removeString(string, stringArray);
	}	

}