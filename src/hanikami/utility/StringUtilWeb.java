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
	 * ���ڿ��� �ִ� Ư�����ڸ� HTML���� ��밡���� ���ڿ��� ��ȯ�Ѵ�. 
	 * @param string ��� ���ڿ�
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
	 * ���ڿ����� Ư�����ڸ� �����Ѵ�.
	 * @param string ��� ���ڿ�
	 * @return String
	 * */
	public static String removeSpecialChar(String string) {
		String[] stringArray = {"#", "$", "%", "&", "-", "--", "=", ";", "<", ">", "/", "\'", "\"", "\\+", "\\(", "\\)"};
	
		return removeString(string, stringArray);
	}	

}