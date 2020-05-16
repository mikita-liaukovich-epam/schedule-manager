package ppvis_lab_2;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class ScheduleController {
	private ScheduleView scheduleView;
	private ScheduleModel scheduleModel;
	private TableModel tableModel;
	private Shell shell;
	private DialogController dialogController;
	 
	
	public ScheduleController() {		
		this.scheduleView = new ScheduleView();
		this.scheduleModel = new ScheduleModel();
		this.tableModel = new TableModel();
		
		this.shell = scheduleView.shell;
		this.dialogController = new DialogController(shell, this);
		
		createToolListeners();
		scheduleView.open();
	}
	
	public void createToolListeners() {
		scheduleView.addToolListener("add", new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				dialogController.showAddDialog();
			}
		});
		
		scheduleView.addToolListener("delete", new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				dialogController.showDeleteDialog();
			}
		});
		
		scheduleView.addToolListener("search", new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				dialogController.showSearchDialog();
			}
		});
		
		scheduleView.addToolListener("import", new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				dialogController.showImportDialog();
			}
		});
		
		scheduleView.addToolListener("export", new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String filePath = dialogController.showExportDialog();
				if (filePath != null) {
					ExportController exportController = new ExportController();
					exportController.export(tableModel.tableRecords, filePath);					
				}
			}
		});
	}
	
	public void createScheduleItem(String[] record) {
		record = calculateTravelTime(record);
		tableModel.addTableRecord(record);
		scheduleView.createTableItem().setText(record);
	}
	
	public void updateScheduleTable(ArrayList<String[]> records) {
		records.forEach(item -> {
			scheduleView.createTableItem().setText(item);			
		});
	}
	
	public void loadNewScheduleData(ArrayList<String[]> records) {
		scheduleView.timeTable.removeAll();
		records.forEach(item -> {
			item = calculateTravelTime(item);
			scheduleView.createTableItem().setText(item);
		});
		tableModel.tableRecords = records;
	}
	
	public String[] calculateTravelTime(String[] record) {
		return scheduleModel.getFormattedRecord(record);
	}
	
	public int deleteScheduleRecord(String textValue, int field) {
		ArrayList<String[]> itemsToDelete = findScheduleRecords(textValue, field);
		tableModel.deleteTableRecordsCollection(itemsToDelete);
		scheduleView.timeTable.removeAll();
		updateScheduleTable(tableModel.tableRecords);
		
		return itemsToDelete.size();
	}
	
	public int deleteScheduleRecord(String textValue, String type) {
		ArrayList<String[]> itemsToDelete = findScheduleRecords(textValue, type);
		tableModel.deleteTableRecordsCollection(itemsToDelete);
		scheduleView.timeTable.removeAll();
		updateScheduleTable(tableModel.tableRecords);
		
		return itemsToDelete.size();
	}
	
	public ArrayList<String[]> findScheduleRecords(String textValue, int field) {
		ArrayList<String[]> allMatchingItems = new ArrayList<String[]>();
		
		for (int i = 0; i < tableModel.tableRecords.size(); i++) {
			String[] item = tableModel.tableRecords.get(i);
			String some = item[field];
			
			if (some.contentEquals(textValue)) {
				allMatchingItems.add(item);
			}
		}
		
		return allMatchingItems;
	}
	
	public int deleteScheduleRecord(String timeFrom, String timeTo, String type) {
		ArrayList<String[]> itemsToDelete = findScheduleRecords(timeFrom, timeTo, type);
		tableModel.deleteTableRecordsCollection(itemsToDelete);
		scheduleView.timeTable.removeAll();
		updateScheduleTable(tableModel.tableRecords);
		
		return itemsToDelete.size();
	}

	public ArrayList<String[]> findScheduleRecords(String textValue, String type) {
		return scheduleModel.findScheduleRecords(tableModel.tableRecords, textValue, type);
	}
	
	public ArrayList<String[]> findScheduleRecords(String timeFrom, String timeTo, String type) {		
		return scheduleModel.findScheduleRecords(tableModel.tableRecords, timeFrom, timeTo, type);
	}
}
