/**
 * 
 */
package hanikami.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author hanikami
 *
 */
public class DateUtil {

	/**
	 * ������ �ð��� �����Ѵ�. �������: yyyyMMddHHmmss
	 * @return String
	 */
	public static String getTime() { 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
		Date currentDateTime = new Date(); 
		
		return sdf.format(currentDateTime);
	}

	/**
	 * ������ �ð��� ����������� �����Ѵ�.
	 * @param dateFormat ������� (��: yyyyMMddHHmmss)
	 * @return String
	 */
	public static String getTime(String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		
		return formatter.format(calendar.getTime());
	}
	
	/**
	 * �Է¹��� �ð��� ����������� �����Ѵ�.
	 * @param calendar �Է� �ð�
	 * @param dateFormat �������
	 * @return String
	 */
	public static String getTime(Calendar calendar, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		
		return formatter.format(calendar.getTime());
	}

	/**
	 * ������ ��¥�� �����Ѵ�.
	 * @param delimiter ������
	 * @return String
	 */
	public static String getCurrentDate(String delimiter) {
		String returnValue = "";
		String currentTime = getTime();
		
		returnValue=returnValue+currentTime.substring(0,4)+delimiter+currentTime.substring(4,6)+delimiter+currentTime.substring(6,8);
		
		return returnValue;
	}
	
	/**
	 * ������ �ð��� �����Ѵ�.
	 * @param delimiter ������
	 * @return String
	 */
	public static String getCurrentTime(String delimiter) {
		String returnValue = "";
		String currentTime = getTime();
		
		returnValue=returnValue+currentTime.substring(8,10)+delimiter+currentTime.substring(10,12)+delimiter+currentTime.substring(12,14);
		
		return returnValue;
	}

	/**
	 * �Է¹��� ��¥���� ���� ���������ڸ� �����Ѵ�. �������: yyyyMMddHHmmss
	 * @param dateString �Է� ����
	 * @return String
	 */
	public static String getLastDate(String dateString) {
		int year = Integer.parseInt(dateString.substring(0, 4));
		int month = Integer.parseInt(dateString.substring(4, 6))-1;

		Calendar cal = Calendar.getInstance();
		cal.set(year, month+1, 1);
		cal.add(Calendar.DATE, -1);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(cal.getTime());
	}	
	
	/**
	 * GregorianCalendar�� String������ ��ȯ�Ѵ�. �������: yyyyMMddHHmmss
	 * @param gCal GregorianCalendar�� �Է� �ð�
	 * @return String
	 */
	public static String convCalToString(GregorianCalendar gCal) {
		String returnValue = "";
		int year = gCal.get(GregorianCalendar.YEAR);
		int month = gCal.get(GregorianCalendar.MONTH)+1;
		int date = gCal.get(GregorianCalendar.DATE);
		int hour = gCal.get(GregorianCalendar.HOUR);
		int minute = gCal.get(GregorianCalendar.MINUTE);
		int second = gCal.get(GregorianCalendar.SECOND);
		
		
		returnValue = returnValue+Integer.toString(year);
		returnValue = returnValue+((month < 10) ? "0"+month : Integer.toString(month));
		returnValue = returnValue+((date < 10) ? "0"+date : Integer.toString(date));
		returnValue = returnValue+((hour < 10) ? "0"+hour : Integer.toString(hour));
		returnValue = returnValue+((minute < 10) ? "0"+minute : Integer.toString(minute));
		returnValue = returnValue+((second < 10) ? "0"+second : Integer.toString(second));

		return returnValue;
	}	

	/**
	 * �Է¹��� YYYYMMDD �Ǵ� yyyyMMddHHmmss ������ ��¥�� �ɼ��� ���ؼ� yyyyMMddHHmmss �������� �����Ѵ�.
	 * @param gCal GregorianCalendar�� �Է� �ð�
	 * @return String
	 */
	public static String getOffsetDay(String dateString, int offsetDays) {
		int year = Integer.parseInt(dateString.substring(0, 4));
		int month = Integer.parseInt(dateString.substring(4, 6))-1;
		int date = Integer.parseInt(dateString.substring(6, 8))+offsetDays;

		GregorianCalendar gCal = new GregorianCalendar();
		
		if(dateString.length()>8){
			int hour = Integer.parseInt(dateString.substring(8, 10));
			int minute = Integer.parseInt(dateString.substring(10, 12));
			int second = Integer.parseInt(dateString.substring(12, 14));
			
			gCal.set(year, month, date, hour, minute, second);
		}
		else{
			gCal.set(year, month, date);
		}

		return convCalToString(gCal);
	}

    public static void waitTime(long sleep) {
		try {
			Thread.sleep(sleep);
		}
		catch(InterruptedException ie) {
			ie.printStackTrace();
		}
    }
    
	public static String getDateTime(String dateFormat) {
		String returnValue = new SimpleDateFormat(dateFormat).format(new Date(System.currentTimeMillis()));//yyyyMMddHHmmss
		
		return returnValue;
	}
}
