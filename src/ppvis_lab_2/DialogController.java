package ppvis_lab_2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.xml.sax.SAXException;

public class DialogController {
	private DialogView dialogView;
	private DialogModel dialogModel;
	private ScheduleController scheduleController;

	public DialogController(Shell mainShell, ScheduleController scheduleController) {
		this.dialogView = new DialogView(mainShell); 
		this.dialogModel = new DialogModel();
		this.scheduleController = scheduleController;
	}
	
	public String checkO (int number) {
		if (number < 10) {
			return "0" + number;
		}
		return "" + number;
	}
	
	public void showAddDialog() {
		dialogView.createAddDialog();
		dialogView.addDialogListener("action", new Listener() {
			@Override
			public void handleEvent(Event arg0) {				
				String[] record = {
						dialogView.addDialogTextFields[0].getText(),
						dialogView.addDialogTextFields[1].getText(),
						dialogView.addDialogTextFields[2].getText(),
						
						"" + checkO(dialogView.addDialogDatesTimes[0].getDay()) + "/" +
						checkO(dialogView.addDialogDatesTimes[0].getMonth() + 1) + "/" +
						dialogView.addDialogDatesTimes[0].getYear() + " " +
						checkO(dialogView.addDialogDatesTimes[1].getHours()) + ":" +
						checkO(dialogView.addDialogDatesTimes[1].getMinutes()),
						
						"" +
						checkO(dialogView.addDialogDatesTimes[2].getDay()) + "/" +
						checkO(dialogView.addDialogDatesTimes[2].getMonth() + 1) + "/" +
						dialogView.addDialogDatesTimes[2].getYear() + " " +
						checkO(dialogView.addDialogDatesTimes[3].getHours()) + ":" +
						checkO(dialogView.addDialogDatesTimes[3].getMinutes()),
						
						""
					};
				scheduleController.createScheduleItem(record);
				dialogView.dialogShell.close();
			}
		});
	}
	
	public void showDeleteDialog() {
		dialogView.createFilterDialog("Delete");
		dialogView.addDialogListener("action", new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				int selectedFilter = 0;
				for (int i = 0; i < dialogView.delDialogRadioButtons.length; i++) {
					if (dialogView.delDialogRadioButtons[i].getSelection() == true) {
						selectedFilter = i;
						break;
					}
				}
				
				int count = 0;
				if (selectedFilter < 3) {
					count = scheduleController.deleteScheduleRecord(dialogView.delDialogTextFields[selectedFilter].getText(), selectedFilter);
				} else if (selectedFilter < 5) {
					count = scheduleController.deleteScheduleRecord(
							"" +
							checkO(dialogView.delDialogDates[selectedFilter - 3].getDay()) + "/" +
							checkO(dialogView.delDialogDates[selectedFilter - 3].getMonth() + 1) + "/" +
							dialogView.delDialogDates[selectedFilter - 3].getYear(),
							selectedFilter == 3 ? "departure" : "arrival"
						);
				} else if (selectedFilter < 7) {
					String timeFrom = "" + checkO(dialogView.delDialogTimes[selectedFilter - 5][0].getHours()) + 
									   ":" + checkO(dialogView.delDialogTimes[selectedFilter - 5][0].getMinutes());
					String timeTo = "" + checkO(dialogView.delDialogTimes[selectedFilter - 5][1].getHours()) + 
									  ":" + checkO(dialogView.delDialogTimes[selectedFilter - 5][1].getMinutes());
					
					count = scheduleController.deleteScheduleRecord(
							timeFrom,
							timeTo,
							selectedFilter == 5 ? "departure" : "arrival"
						);
				} else if (selectedFilter < 8) {
					count = scheduleController.deleteScheduleRecord(dialogView.delDialogTextFields[3].getText(), 5);
				}
				
				dialogView.showInfoMessage("Deleted " + count + " items");	
				
				dialogView.dialogShell.close();
			}
		});
	}
	
	public void showSearchDialog() {
		dialogView.createFilterDialog("Search");
		dialogView.addDialogListener("action", new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				int selectedFilter = 0;
				for (int i = 0; i < dialogView.delDialogRadioButtons.length; i++) {
					if (dialogView.delDialogRadioButtons[i].getSelection() == true) {
						selectedFilter = i;
						break;
					}
				}
				
				ArrayList<String[]> searchResult = null;
				if (selectedFilter < 3) {
					searchResult = scheduleController.findScheduleRecords(dialogView.delDialogTextFields[selectedFilter].getText(), selectedFilter);
				} else if (selectedFilter < 5) {
					searchResult = scheduleController.findScheduleRecords(
							"" +
							checkO(dialogView.delDialogDates[selectedFilter - 3].getDay()) + "/" +
							checkO(dialogView.delDialogDates[selectedFilter - 3].getMonth() + 1) + "/" +
							dialogView.delDialogDates[selectedFilter - 3].getYear(),
							selectedFilter == 3 ? "departure" : "arrival"
						);
				} else if (selectedFilter < 7) {
					String timeFrom = "" + checkO(dialogView.delDialogTimes[selectedFilter - 5][0].getHours()) + 
									   ":" + checkO(dialogView.delDialogTimes[selectedFilter - 5][0].getMinutes());
					String timeTo = "" + checkO(dialogView.delDialogTimes[selectedFilter - 5][1].getHours()) + 
									  ":" + checkO(dialogView.delDialogTimes[selectedFilter - 5][1].getMinutes());
					
					searchResult = scheduleController.findScheduleRecords(
							timeFrom,
							timeTo,
							selectedFilter == 5 ? "departure" : "arrival"
						);
				} else if (selectedFilter < 8) {
					searchResult = scheduleController.findScheduleRecords(dialogView.delDialogTextFields[3].getText(), 5);
				}
				
				dialogView.showInfoMessage("Found " + searchResult.size() + " items");	
				dialogView.dialogShell.close();
				
				dialogView.createSearchDialog();
				
				dialogModel.setSearchResult(searchResult);
				dialogView.updateSearchByPage(dialogModel.getSearchPage(0));
				
				dialogView.addDialogListener("next", new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						dialogView.updateSearchByPage(dialogModel.getSearchPage(1));
					}
				});
				
				dialogView.addDialogListener("prev", new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						dialogView.updateSearchByPage(dialogModel.getSearchPage(-1));
					}
				});
			}
		});
	}
	
	public void showImportDialog() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        
        try {
        	String filePath = dialogView.createImportDialog();
        	if (filePath != null) {
        		SAXParser saxParser = saxParserFactory.newSAXParser();
        		ImportController loaderController = new ImportController();
        		saxParser.parse(new File(filePath), loaderController);
        		scheduleController.loadNewScheduleData(loaderController.getRecordsList());        		
        	}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String showExportDialog() {
		return dialogView.createExportDialog();
	}
}
