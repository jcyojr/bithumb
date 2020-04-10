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
	 * 오브젝트를 문자열형으로 변환 후, 문자열을 리턴 한다. 문자열이  NULL 또는 값이 없는 경우 defaultString 으로 받은 값을 리턴한다.
	 * @param object 대상 오브젝트
	 * @param defaultString 문자열이  NULL 또는 값이 없는 경우 리턴 할 값
	 * @return String
	 */
	public static final String getString(Object object, String defaultString) {
		String string=String.valueOf(object);
		
		if(object == null || string.trim().equals("")) string = defaultString;
		
		return string;        
	}
       
	/**
	 * 오브젝트를 문자열형으로 변환 후, 문자열을 리턴 한다.
	 * @param object 대상 오브젝트
	 * @return String
	 */
    public static final String getString(Object object) {
    	
        return getString(object, "");
    }
	
	/**
	 * 오브젝트를 문자열형으로 변환 후, 문자열에서 지정한 인덱스 범위의 문자열을 리턴 한다.
	 * @param object 대상 오브젝트
	 * @param indexStart 시작 인덱스
	 * @param indexEnd 종료 인덱스
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
	 * 문자열에서 시작 위치부터 마지막 길이까지의 문자열을 반환한다.
	 * @param object 대상 오브젝트
	 * @param indexStart 시작 인덱스
	 * @return String
	 */
	public static String substring(Object object, int indexStart) {
		int indexEnd = String.valueOf(object).length();

		return substring(object, indexStart, indexEnd);
	}	

	/**
	 * 문자열에서 시작 위치부터 종료 위치까지의 문자열을 반환한다. 한글의 깨짐 문자는 반환하지 않는다.
	 * @param object 대상 오브젝트
	 * @param indexStart 시작 인덱스
	 * @param indexEnd 종료 인덱스
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
	 * 문자열에서 시작 위치부터 마지막 길이까지의 문자열을 반환한다. SQL의 SUBSTR과 동일하다.
	 * @param object 대상 오브젝트
	 * @param locationStart 시작 위치
	 * @param returnStringLength 반환할 문자 길이
	 * @return String
	 */
	public static String substr(Object object, int locationStart, int returnStringLength) {
		int indexStart = locationStart-1;
		int indexEnd = indexStart+returnStringLength;
		
		return substring(object, indexStart, indexEnd);
	}	

	/**
	 * 문자열에서 시작 위치부터 마지막 길이까지의 문자열을 반환한다. SQL의 SUBSTR과 동일하다.
	 * @param object 대상 오브젝트
	 * @param locationStart 시작 위치
	 * @return String
	 */
	public static String substr(Object object, int locationStart) {
		int indexStart = locationStart-1;
		int indexEnd = String.valueOf(object).length();
		
		return substring(object, indexStart, indexEnd);
	}	

	
	/**
	 * 문자열에서 시작 위치부터 마지막 길이까지의 문자열을 반환한다. SQL의 SUBSTRB와 동일하다. 한글의 깨짐 문자는 반환하지 않는다.
	 * @param object 대상 오브젝트
	 * @param locationStart 시작 위치
	 * @param returnStringLength 반환할 문자 길이
	 * @return String
	 */
	public static String substrb(Object object, int locationStart, int returnStringLength) {
		int indexStart = locationStart-1;
		int indexEnd = indexStart+returnStringLength;
		
		return substringb(object, indexStart, indexEnd);
	}	

	/**
	 * 문자열에서 시작 위치부터 마지막 길이까지의 문자열을 반환한다. SQL의 SUBSTRB와 동일하다. 한글의 깨짐 문자는 반환하지 않는다.
	 * @param object 대상 오브젝트
	 * @param locationStart 시작 위치
	 * @return String
	 */
	public static String substrb(Object object, int locationStart) {
		int indexStart = locationStart-1;
		int indexEnd = String.valueOf(object).length();
		
		return substringb(object, indexStart, indexEnd);
	}	

	/**
	 * 문자 채움 방향을 지정하여 문자열 채우기
	 * @param object 대상 오브젝트
	 * @param stringLength 전체 길이
	 * @param fillString 채울 문자
	 * @param isRightPad 오른쪽 재움 여부
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
	 * 문자타입에 따라 문자열 채우기. 문자형은 오른쪽에 공백 채움. 숫자형은 완쪽에 0 채움.
	 * @param object 대상 오브젝트
	 * @param stringLength 전체 길이
	 * @param isStringType 문자형 채움 방식 여부
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
	 * 문자타입에 따라 문자열 채우기. 문자형은 오른쪽에 공백 채움. 숫자형은 완쪽에 0 채움.
	 * @param object 대상 오브젝트
	 * @param stringLength 전체 길이
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
	 * 채울 문자를 지정하여  문자열 채우기. 문자형은 오른쪽에 채움. 숫자형은 완쪽에 채움.
	 * @param object 대상 오브젝트
	 * @param stringLength 전체 길이
	 * @param fillString 채울 문자열
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
	 * 문자열을 지정된 길이만큼 남기고 잘라낸다.
	 * @param object 대상 오브젝트
	 * @param stringLength 남길 길이
	 * @param fillString 잘라내고 채울 문자열
	 * @return String
	 */
	public static String cutString(Object object, int stringLength, String fillString) {
		
		return substringb(object, 0, stringLength)+fillString;
	}
	
	/**
	 * 문자열을 지정된 길이만큼 남기고 잘라낸다.
	 * @param object 대상 오브젝트
	 * @param stringLength 남길 길이
	 * @return String
	 */
	public static String cutString(Object object, int stringLength) {
		
		return cutString(object, stringLength, "..");
	}
	
	/**
	 * 문자열형 배열의 값을 모두 합친다.
	 * @param stringArray 대상문자열형 배열
	 * @param delimiter 구분자 (구분자가 \n 인 경우에는 라인으로 구분한다.)
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
	 * 문자열을 배열로 지정한 문자들로 치환 한다. 
	 * @param string 대상 문자열
	 * @param stringArray 치환할 문자열형 배열
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
	 * 문자열에서 지정된 문자들을 제거한다.
	 * @param string 대상 문자열
	 * @param stringArray 제거할 문자 배열
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
	 * 문자열이 숫자형인지 판단한다.
	 * @param string 대상 문자열
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
	 * 문자열에서 한글을 반환한다.
	 * @param string 대상 문자열
	 * @return String
	 */
	public static String getKorean(String string) {
		String returnValue = getString(string);
		
		return returnValue.replaceAll("^([가-힝]{1,}동[0-9]{1,2}가)$|^([가-힝]{1,}동)$|^([가-힝]{1,}면)$", " ").trim();
	}	
	
	/**
	 * 문자열에서 숫자를 반환한다.
	 * @param string 대상 문자열
	 * @return String
	 */
	public static String getNumeric(String string) {
		String returnValue = getString(string);
		
		return returnValue.replaceAll("[^-0-9]", " ").trim();
	}	

	/**
	 * 문자열의 문자셋을 변경한다.
	 * @param string 대상 문자열
	 * @return inputCharSet 입력 문자셋
	 * @return outputCharSet 출력 문자셋
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