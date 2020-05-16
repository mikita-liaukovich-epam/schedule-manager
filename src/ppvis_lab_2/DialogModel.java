package ppvis_lab_2;

import java.util.ArrayList;

public class DialogModel {
	private ArrayList<String[]> searchResult;
	private int timeTableStartIndex = 0;
	
	public void setSearchResult(ArrayList<String[]> searchResult) {
		this.searchResult = searchResult;
	}
	
	public ArrayList<String[]> getSearchPage(int direction) {
		if ((timeTableStartIndex + 10 > searchResult.size() && direction > 0) ||
			(timeTableStartIndex == 0 && direction < 0)) {
			return null;
		}
		
		timeTableStartIndex += direction * 10;
		int finalIndex = Math.min(timeTableStartIndex + 10, searchResult.size());
		
		ArrayList<String[]> requestedPageList = new ArrayList<String[]>();
		
		for (int i = timeTableStartIndex; i < finalIndex; i++) {
			requestedPageList.add(searchResult.get(i));
		}
		
		return requestedPageList;
	}
}
