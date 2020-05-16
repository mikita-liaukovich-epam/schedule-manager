package ppvis_lab_2;

import java.util.ArrayList;

public class TableModel {
	protected ArrayList<String[]> tableRecords;
	
	public TableModel() {
		tableRecords = new ArrayList<String[]>();
	}
	
	public void addTableRecord(String[] record) {
		tableRecords.add(record);
	}
	
	public void deleteTableRecord(int index) {
		tableRecords.remove(index);
	}
	
	public void deleteTableRecordsCollection(ArrayList<String[]> items) {
		tableRecords.removeAll(items);
	}
}
