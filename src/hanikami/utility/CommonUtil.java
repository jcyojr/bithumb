/**
 * 
 */
package hanikami.utility;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author hanikami
 *
 */
public class CommonUtil {


	/**
	 * 입력받은 숫자형 자료에서 3자리마다 컴마를 표시한다.
	 * @param object 대상 오브젝트
	 * @return String
	 */
	public static String getNumberFormat(Object object) {
		String returnValue = "";
		String string = String.valueOf(object);
		String minus = "";
		if(string.charAt(0) == '-'){
			minus = "-";
			string = string.substring(1, string.length());
		}
		
		String strDotLeft = string.replaceAll(",", "");
		String strDotRight = "";
		if(string.indexOf(".")>0){
			strDotLeft = string.substring(0, string.indexOf("."));
			strDotRight = string.substring(string.indexOf("."), string.length());
		}

		int digitNum = 3;
		int strLen=strDotLeft.length();
		if(strLen>digitNum){
			int commaNum = (int)(strLen/digitNum);
			int idx = (int)(strLen%digitNum);

			if(idx > 0){
				returnValue += strDotLeft.substring(0,idx)+",";
			}

			for(int i = 0; i < commaNum; i++){
				returnValue += strDotLeft.substring(idx, idx+digitNum);
				idx += digitNum;
				if(idx < strLen){
					returnValue += ",";
				}
			}
			returnValue += strDotRight;
		}
		else{
			returnValue = strDotLeft+strDotRight;
		}
		
		return minus+returnValue;
	}

	public static Properties getPropertiesFullPath(String fileName) {
		Properties properties = new Properties();
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(fileName);
			properties.load(new BufferedInputStream(fis));
		}
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			try {
				fis.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return properties;
	}
	
	public static Properties getProperties(String fileName) {
		Properties properties = new Properties();
		InputStream is = CommonUtil.class.getClassLoader().getResourceAsStream(fileName);
		
		try {
			properties.load(is);
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			try {
				is.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}

		
		return properties;
	}
	

}
