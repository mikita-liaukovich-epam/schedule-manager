package ppvis_lab_2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleModel {
	public String[] getFormattedRecord(String[] record) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Date departureDate = null;
		Date arrivalDate = null;
		
		try {
			departureDate = format.parse(record[3]);
			arrivalDate = format.parse(record[4]);
		
			long diff = arrivalDate.getTime() - departureDate.getTime();
	
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
			int travelTimeIndex = record.length - 1;
			
			record[travelTimeIndex] = (diffDays != 0) ? (diffDays + "d") : "";
			record[travelTimeIndex] += (diffHours != 0) ? (diffHours + "h") : "";
			record[travelTimeIndex] += (diffMinutes != 0) ? (diffMinutes + "m") : "";			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return record;
	}
	
	public ArrayList<String[]> findScheduleRecords(ArrayList<String[]> records, String timeFrom, String timeTo, String type) {
		ArrayList<String[]> allMatchingItems = new ArrayList<String[]>();
		String epoch = "01/01/1970 ";
		int textField = (type == "departure") ? 3 : 4;
		timeFrom = epoch + timeFrom;
		timeTo = epoch + timeTo;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		long timeRangeFrom = 0;
		long timeRangeTo = 0;
		
		try {
			timeRangeFrom = format.parse(timeFrom).getTime();
			timeRangeTo = format.parse(timeTo).getTime();
			
			for (int i = 0; i < records.size(); i++) {
				String[] item = records.get(i);
				String stringToCompare = item[textField];
				
				int indexOfSpace = stringToCompare.indexOf(" ");
				String compareTimeString = stringToCompare.substring(indexOfSpace);
				
				long timeToCompare = format.parse(epoch + compareTimeString).getTime();
				if (timeToCompare >= timeRangeFrom && timeToCompare <= timeRangeTo) {
					allMatchingItems.add(item);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return allMatchingItems;
	}
	
	public ArrayList<String[]> findScheduleRecords(ArrayList<String[]> records, String textValue, String type) {
		ArrayList<String[]> allMatchingItems = new ArrayList<String[]>();
		int textField = (type == "departure") ? 3 : 4;
		
		for (int i = 0; i < records.size(); i++) {
			String[] item = records.get(i);
			String stringToCompare = item[textField];
			int indexOfSpace = stringToCompare.indexOf(" ");
			
			if (stringToCompare.substring(0, indexOfSpace).contentEquals(textValue)) {
				allMatchingItems.add(item);
			}
		}
		
		return allMatchingItems;
	}
}
