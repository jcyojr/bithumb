/**
 * 
 */
package hanikami.utility;

import java.lang.StringIndexOutOfBoundsException;
import java.io.UnsupportedEncodingException;

/**
 * @author hanikami
 *
 */
public class StringUtil {

	/**
	 * ������Ʈ�� ���ڿ������� ��ȯ ��, ���ڿ��� ���� �Ѵ�. ���ڿ���  NULL �Ǵ� ���� ���� ��� defaultString ���� ���� ���� �����Ѵ�.
	 * @param object ��� ������Ʈ
	 * @param defaultString ���ڿ���  NULL �Ǵ� ���� ���� ��� ���� �� ��
	 * @return String
	 */
	public static final String getString(Object object, String defaultString) {
		String string=String.valueOf(object);
		
		if(object == null || string.trim().equals("")) string = defaultString;
		
		return string;        
	}
       
	/**
	 * ������Ʈ�� ���ڿ������� ��ȯ ��, ���ڿ��� ���� �Ѵ�.
	 * @param object ��� ������Ʈ
	 * @return String
	 */
    public static final String getString(Object object) {
    	
        return getString(object, "");
    }
	
	/**
	 * ������Ʈ�� ���ڿ������� ��ȯ ��, ���ڿ����� ������ �ε��� ������ ���ڿ��� ���� �Ѵ�.
	 * @param object ��� ������Ʈ
	 * @param indexStart ���� �ε���
	 * @param indexEnd ���� �ε���
	 * @return String
	 */
	public static String substring(Object object, int indexStart, int indexEnd) {
		String returnValue = getString(object);

		int len = returnValue.length();
		
		if(indexStart > len) {
			indexStart = len;
		}
		
		if(indexEnd > len) {
			indexEnd = len;
		}

		try {
			returnValue = returnValue.substring(indexStart, indexEnd);
		}
		catch (StringIndexOutOfBoundsException sie) {
			returnValue = "";
		}
		
		return returnValue;
	}	

	/**
	 * ���ڿ����� ���� ��ġ���� ������ ���̱����� ���ڿ��� ��ȯ�Ѵ�.
	 * @param object ��� ������Ʈ
	 * @param indexStart ���� �ε���
	 * @return String
	 */
	public static String substring(Object object, int indexStart) {
		int indexEnd = String.valueOf(object).length();

		return substring(object, indexStart, indexEnd);
	}	

	/**
	 * ���ڿ����� ���� ��ġ���� ���� ��ġ������ ���ڿ��� ��ȯ�Ѵ�. �ѱ��� ���� ���ڴ� ��ȯ���� �ʴ´�.
	 * @param object ��� ������Ʈ
	 * @param indexStart ���� �ε���
	 * @param indexEnd ���� �ε���
	 * @return String
	 */
	public static String substringb(Object object, int indexStart, int indexEnd) {
		String string = getString(object);

		if (string.getBytes().length < indexStart){
			indexStart = string.getBytes().length;
		}
		if (string.getBytes().length < indexEnd){
			indexEnd = string.getBytes().length;
		}

		for(int i = 0; i < indexEnd; i++){
			if (string.charAt(i) > 0x7F){
				indexEnd--;
			}
		}

		if(indexStart > indexEnd){
			indexStart = indexEnd;
		}
		
		return string.substring(indexStart, indexEnd);
	}
	
	/**
	 * ���ڿ����� ���� ��ġ���� ������ ���̱����� ���ڿ��� ��ȯ�Ѵ�. SQL�� SUBSTR�� �����ϴ�.
	 * @param object ��� ������Ʈ
	 * @param locationStart ���� ��ġ
	 * @param returnStringLength ��ȯ�� ���� ����
	 * @return String
	 */
	public static String substr(Object object, int locationStart, int returnStringLength) {
		int indexStart = locationStart-1;
		int indexEnd = indexStart+returnStringLength;
		
		return substring(object, indexStart, indexEnd);
	}	

	/**
	 * ���ڿ����� ���� ��ġ���� ������ ���̱����� ���ڿ��� ��ȯ�Ѵ�. SQL�� SUBSTR�� �����ϴ�.
	 * @param object ��� ������Ʈ
	 * @param locationStart ���� ��ġ
	 * @return String
	 */
	public static String substr(Object object, int locationStart) {
		int indexStart = locationStart-1;
		int indexEnd = String.valueOf(object).length();
		
		return substring(object, indexStart, indexEnd);
	}	

	
	/**
	 * ���ڿ����� ���� ��ġ���� ������ ���̱����� ���ڿ��� ��ȯ�Ѵ�. SQL�� SUBSTRB�� �����ϴ�. �ѱ��� ���� ���ڴ� ��ȯ���� �ʴ´�.
	 * @param object ��� ������Ʈ
	 * @param locationStart ���� ��ġ
	 * @param returnStringLength ��ȯ�� ���� ����
	 * @return String
	 */
	public static String substrb(Object object, int locationStart, int returnStringLength) {
		int indexStart = locationStart-1;
		int indexEnd = indexStart+returnStringLength;
		
		return substringb(object, indexStart, indexEnd);
	}	

	/**
	 * ���ڿ����� ���� ��ġ���� ������ ���̱����� ���ڿ��� ��ȯ�Ѵ�. SQL�� SUBSTRB�� �����ϴ�. �ѱ��� ���� ���ڴ� ��ȯ���� �ʴ´�.
	 * @param object ��� ������Ʈ
	 * @param locationStart ���� ��ġ
	 * @return String
	 */
	public static String substrb(Object object, int locationStart) {
		int indexStart = locationStart-1;
		int indexEnd = String.valueOf(object).length();
		
		return substringb(object, indexStart, indexEnd);
	}	

	/**
	 * ���� ä�� ������ �����Ͽ� ���ڿ� ä���
	 * @param object ��� ������Ʈ
	 * @param stringLength ��ü ����
	 * @param fillString ä�� ����
	 * @param isRightPad ������ ��� ����
	 * @return String
	 */
	public static String fillString(Object object, int stringLength, String fillString, boolean isRightPad) {
		String returnValue = getString(object);
		
		for(int i = stringLength; i > returnValue.length(); i--) {
			if(isRightPad) {
				returnValue = returnValue + fillString;
			}
			else {
				returnValue = fillString + returnValue;
			}
		}
		
		return returnValue;
	}

	/**
	 * ����Ÿ�Կ� ���� ���ڿ� ä���. �������� �����ʿ� ���� ä��. �������� ���ʿ� 0 ä��.
	 * @param object ��� ������Ʈ
	 * @param stringLength ��ü ����
	 * @param isStringType ������ ä�� ��� ����
	 * @return String
	 */
	public static String fillString(Object object, int stringLength, boolean isStringType) {
		String fillString = "";
		boolean isRightPad = false;
		
		if (isStringType){
			fillString = " ";
			isRightPad = true;
		}
		else {
			fillString = "0";
			isRightPad = false;
		}
		
		return fillString(object, stringLength, fillString, isRightPad);
	}

	/**
	 * ����Ÿ�Կ� ���� ���ڿ� ä���. �������� �����ʿ� ���� ä��. �������� ���ʿ� 0 ä��.
	 * @param object ��� ������Ʈ
	 * @param stringLength ��ü ����
	 * @return String
	 */
	public static String fillString(Object object, int stringLength) {
		boolean isStringType = false;
		
		if (object.getClass() == String.class){
			isStringType = true;
		}
		else {
			isStringType = false;
		}
		
		return fillString(object, stringLength, isStringType);
	}

	/**
	 * ä�� ���ڸ� �����Ͽ�  ���ڿ� ä���. �������� �����ʿ� ä��. �������� ���ʿ� ä��.
	 * @param object ��� ������Ʈ
	 * @param stringLength ��ü ����
	 * @param fillString ä�� ���ڿ�
	 * @return String
	 */
	public static String fillString(Object object, int stringLength, String fillString) {
		boolean isRightPad = false;

		if (object.getClass() == String.class){
			isRightPad = true;
		}
		else {
			isRightPad = false;
		}
		
		return fillString(object, stringLength, fillString, isRightPad);
	}

	public static String padding(String str, int strReturnSize, String fillText, boolean leftFill) {
		String returnValue = str;
		
		if(fillText.equals("")) {
			if(leftFill) fillText = "0";
			else fillText = " ";
		}
		
		int strLength = returnValue.length();
		if(strReturnSize > strLength) {
			for(int i=0;i<(strReturnSize-strLength);i++) {
				if(leftFill) returnValue = fillText+returnValue;
				else returnValue += fillText;
			}
		}
		
		return returnValue;
	}
	
	/**
	 * ���ڿ��� ������ ���̸�ŭ ����� �߶󳽴�.
	 * @param object ��� ������Ʈ
	 * @param stringLength ���� ����
	 * @param fillString �߶󳻰� ä�� ���ڿ�
	 * @return String
	 */
	public static String cutString(Object object, int stringLength, String fillString) {
		
		return substringb(object, 0, stringLength)+fillString;
	}
	
	/**
	 * ���ڿ��� ������ ���̸�ŭ ����� �߶󳽴�.
	 * @param object ��� ������Ʈ
	 * @param stringLength ���� ����
	 * @return String
	 */
	public static String cutString(Object object, int stringLength) {
		
		return cutString(object, stringLength, "..");
	}
	
	/**
	 * ���ڿ��� �迭�� ���� ��� ��ģ��.
	 * @param stringArray ����ڿ��� �迭
	 * @param delimiter ������ (�����ڰ� \n �� ��쿡�� �������� �����Ѵ�.)
	 * @return String
	 */
	public static String mergeString(String[] stringArray, String delimiter) {
		String returnValue = "";
		
		for(int i = 0; i < stringArray.length; i++) {
			if(i > 0) {
				returnValue = returnValue + delimiter;
			}
			returnValue = returnValue + stringArray[i];
		}
		
		return returnValue;
	}

	/**
	 * ���ڿ��� �迭�� ������ ���ڵ�� ġȯ �Ѵ�. 
	 * @param string ��� ���ڿ�
	 * @param stringArray ġȯ�� ���ڿ��� �迭
	 * @return String
	 */
	public static String convertString(String string, String[][] stringArray) {
		String returnValue = getString(string);
		
		for(int i = 0; i < stringArray.length; i++) {
			returnValue = returnValue.replaceAll(stringArray[i][0], stringArray[i][1]);
		}
		
		return returnValue;
	}

	/**
	 * ���ڿ����� ������ ���ڵ��� �����Ѵ�.
	 * @param string ��� ���ڿ�
	 * @param stringArray ������ ���� �迭
	 * @return String
	 */
	public static String removeString(String string, String[] stringArray) {
		String returnValue = getString(string);
		
		for(int i = 0; i < stringArray.length; i++) {
			returnValue = returnValue.replaceAll(stringArray[i], "");
		}
		
		return returnValue;
	}	

	/**
	 * ���ڿ��� ���������� �Ǵ��Ѵ�.
	 * @param string ��� ���ڿ�
	 * @return boolean
	 */
	public static boolean isNumeric(String string) {
		boolean returnValue = true;
		
		for(int i = 0; i < string.length(); i++) {
			if(!Character.isDigit(string.charAt(i))) {
				returnValue = false;
				break;
			}
		}

		return returnValue;
	}

	/**
	 * ���ڿ����� �ѱ��� ��ȯ�Ѵ�.
	 * @param string ��� ���ڿ�
	 * @return String
	 */
	public static String getKorean(String string) {
		String returnValue = getString(string);
		
		return returnValue.replaceAll("^([��-��]{1,}��[0-9]{1,2}��)$|^([��-��]{1,}��)$|^([��-��]{1,}��)$", " ").trim();
	}	
	
	/**
	 * ���ڿ����� ���ڸ� ��ȯ�Ѵ�.
	 * @param string ��� ���ڿ�
	 * @return String
	 */
	public static String getNumeric(String string) {
		String returnValue = getString(string);
		
		return returnValue.replaceAll("[^-0-9]", " ").trim();
	}	

	/**
	 * ���ڿ��� ���ڼ��� �����Ѵ�.
	 * @param string ��� ���ڿ�
	 * @return inputCharSet �Է� ���ڼ�
	 * @return outputCharSet ��� ���ڼ�
	 */
	public static String convertCharSet(String string, String inputCharSet, String outputCharSet) {
		String returnValue = getString(string);
		
		try {
			returnValue = new String(string.getBytes(inputCharSet), outputCharSet);
			
			return returnValue;
		} catch (UnsupportedEncodingException uee) {
			
			return returnValue;
		}
	}

}