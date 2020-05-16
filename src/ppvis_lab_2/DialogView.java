package ppvis_lab_2;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class DialogView {
	private Shell mainShell;
	protected Shell dialogShell;
	private FillLayout severalItemsLayout = new FillLayout(SWT.VERTICAL);
	
	private Button actionButton;
	
	protected Text addDialogTextFields[] = new Text[3];
	protected DateTime addDialogDatesTimes[] = new DateTime[4];
	
	protected Button delDialogRadioButtons[] = new Button[8];
	protected Text delDialogTextFields[] = new Text[4];
	protected DateTime delDialogDates[] = new DateTime[2];
	protected DateTime[] delDialogTimes[] = new DateTime[2][2];
	
	private Table timeTable;
	private ToolItem prevPage;
	private ToolItem nextPage;
	
	public DialogView(Shell mainShell) {
		this.mainShell = mainShell;
	}
	
	public void createAddDialog() {
		dialogShell = new Shell(mainShell);
		dialogShell.setText("Add record");
		dialogShell.setLayout(new GridLayout(2, false));

		new Label(dialogShell, SWT.NONE).setText("Train ID");
		addDialogTextFields[0] = new Text(dialogShell, SWT.BORDER);
        
        new Label(dialogShell, SWT.NONE).setText("Departure Station");
        addDialogTextFields[1] = new Text(dialogShell, SWT.BORDER);
        
        new Label(dialogShell, SWT.NONE).setText("Arrival Station");
        addDialogTextFields[2] = new Text(dialogShell, SWT.BORDER);
        
        new Label(dialogShell, SWT.NONE).setText("Departure");
        Group departureGroup = new Group(dialogShell, SWT.NONE);
        
        departureGroup.setText("Departure Date and Time");
        departureGroup.setLayout(severalItemsLayout);
        addDialogDatesTimes[0] = new DateTime(departureGroup, SWT.DROP_DOWN|SWT.BORDER|SWT.DATE);
        addDialogDatesTimes[1] = new DateTime(departureGroup, SWT.BORDER|SWT.TIME);
        
        new Label(dialogShell, SWT.NONE).setText("Arrival");
        Group arrivalGroup = new Group(dialogShell, SWT.NONE);
        
        arrivalGroup.setText("Arrival Date and Time     ");
        arrivalGroup.setLayout(severalItemsLayout);
        addDialogDatesTimes[2] = new DateTime(arrivalGroup, SWT.DROP_DOWN|SWT.BORDER|SWT.DATE);
        addDialogDatesTimes[3] = new DateTime(arrivalGroup, SWT.BORDER|SWT.TIME);
        
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
        gridData.horizontalSpan = 2;
        
        actionButton = new Button(dialogShell, SWT.PUSH);
        actionButton.setText("Add");
        actionButton.setLayoutData(gridData);
        
		dialogShell.pack();
		dialogShell.open();
	}
	
	public void createFilterDialog(String modificator) {
		dialogShell = new Shell(mainShell);
		dialogShell.setLayout(new GridLayout(2, true));
		dialogShell.setText(modificator + " by");

		delDialogRadioButtons[0] = new Button(dialogShell, SWT.RADIO);
		delDialogRadioButtons[0].setText("Train ID");
		delDialogRadioButtons[0].setSelection(true);
		delDialogTextFields[0] = new Text(dialogShell, SWT.BORDER);
        
		delDialogRadioButtons[1] = new Button(dialogShell, SWT.RADIO);
		delDialogRadioButtons[1].setText("Departure Station");
        delDialogTextFields[1] = new Text(dialogShell, SWT.BORDER);
        
        delDialogRadioButtons[2] = new Button(dialogShell, SWT.RADIO);
        delDialogRadioButtons[2].setText("Arrival Station");
        delDialogTextFields[2] = new Text(dialogShell, SWT.BORDER);
        
        delDialogRadioButtons[3] = new Button(dialogShell, SWT.RADIO);
        delDialogRadioButtons[3].setText("Departure Date");
        delDialogDates[0] = new DateTime(dialogShell, SWT.DROP_DOWN|SWT.BORDER|SWT.DATE);
        
        delDialogRadioButtons[5] = new Button(dialogShell, SWT.RADIO);
        delDialogRadioButtons[5].setText("Departure Time");
        
        Group departureTimeGroup = new Group(dialogShell, SWT.NONE);
        departureTimeGroup.setText("From - To");
        departureTimeGroup.setLayout(severalItemsLayout);
        
        delDialogTimes[0][0] = new DateTime(departureTimeGroup, SWT.BORDER|SWT.TIME);
        delDialogTimes[0][1] = new DateTime(departureTimeGroup, SWT.BORDER|SWT.TIME);
        
        delDialogRadioButtons[4] = new Button(dialogShell, SWT.RADIO);
        delDialogRadioButtons[4].setText("Arrival Date");
        delDialogDates[1] = new DateTime(dialogShell, SWT.DROP_DOWN|SWT.BORDER|SWT.DATE);
        
        delDialogRadioButtons[6] = new Button(dialogShell, SWT.RADIO);
        delDialogRadioButtons[6].setText("Arrival Time");
        
        Group arrivalTimeGroup = new Group(dialogShell, SWT.NONE);
        arrivalTimeGroup.setText("From - To");
        arrivalTimeGroup.setLayout(severalItemsLayout);
        
        delDialogTimes[1][0] = new DateTime(arrivalTimeGroup, SWT.BORDER|SWT.TIME);
        delDialogTimes[1][1] = new DateTime(arrivalTimeGroup, SWT.BORDER|SWT.TIME);
        
        delDialogRadioButtons[7] = new Button(dialogShell, SWT.RADIO);
        delDialogRadioButtons[7].setText("Travel Time");
        
        Group travelTimeGroup = new Group(dialogShell, SWT.NONE);
        travelTimeGroup.setText("e.g. 1d2h3m");
        travelTimeGroup.setLayout(severalItemsLayout);
        
        delDialogTextFields[3] = new Text(travelTimeGroup, SWT.BORDER);
        
        actionButton = new Button(dialogShell, SWT.PUSH);
        actionButton.setText(modificator);
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
        gridData.horizontalSpan = 3;
        actionButton.setLayoutData(gridData);
        
		dialogShell.pack();
		dialogShell.open();
	}
	
	public void createSearchDialog() {
		dialogShell = new Shell(mainShell);
		dialogShell.setText("Search results");
	    dialogShell.setLayout(new GridLayout());
	    
		Composite scheduleContainer = new Composite(dialogShell, SWT.BORDER|SWT.NO_SCROLL);
		ToolBar pagesBar = new ToolBar(dialogShell, SWT.BORDER|SWT.HORIZONTAL);
        
		timeTable = new Table(scheduleContainer, SWT.BORDER|SWT.SCROLL_PAGE);
		timeTable.setHeaderVisible(true);
		timeTable.setLinesVisible(true);
		timeTable.setSize(820, 260);
		timeTable.setLayout(new FillLayout());
		
		TableColumn trainID = new TableColumn(timeTable, SWT.BORDER);
		TableColumn departureStation = new TableColumn(timeTable, SWT.BORDER);
		TableColumn arrivalStation = new TableColumn(timeTable, SWT.BORDER);
		TableColumn departureDateTime = new TableColumn(timeTable, SWT.BORDER);
		TableColumn arrivalDateTime = new TableColumn(timeTable, SWT.BORDER);
		TableColumn travelTime = new TableColumn(timeTable, SWT.BORDER);
		
		trainID.setWidth(70);
		departureStation.setWidth(150);
		arrivalStation.setWidth(125);
		departureDateTime.setWidth(195);
		arrivalDateTime.setWidth(175);
		travelTime.setWidth(100);
		
        trainID.setText("Train ID");
        departureStation.setText("Departure Station");
        arrivalStation.setText("Arrival Station");
        departureDateTime.setText("Departure Date and Time");
        arrivalDateTime.setText("Arrival Date and Time");
        travelTime.setText("Travel Time");
        
        prevPage= new ToolItem(pagesBar, SWT.BORDER);
        prevPage.setText("  ◀  ");
        nextPage = new ToolItem(pagesBar, SWT.BORDER);
        nextPage.setText("  ▶  ");
        
		dialogShell.pack();
		dialogShell.open();
	}
	
	public String createImportDialog() {
		dialogShell = new Shell(mainShell);
		FileDialog importDialog = new FileDialog(dialogShell, SWT.OPEN);
		importDialog.setText("Open");
        String[] filterExt = { "*.xml", "*.*" };
        importDialog.setFilterExtensions(filterExt);
        return importDialog.open();
	}
	
	public String createExportDialog() {
		dialogShell = new Shell(mainShell);
		FileDialog exportDialog = new FileDialog(dialogShell, SWT.SAVE);
		exportDialog.setText("Save");
        String[] filterExt = { "*.xml", "*.*" };
        exportDialog.setFilterExtensions(filterExt);
        return exportDialog.open();
	}
	
	public TableItem createTableItem() {
		return new TableItem(timeTable, SWT.NONE);
	}
	
	public void updateSearchByPage(ArrayList<String[]> page) {
		if (page == null) return;
		timeTable.removeAll();
		page.forEach(item -> createTableItem().setText(item));
	}
	
	public void showInfoMessage(String info) {
		MessageBox infoBox = new MessageBox(dialogShell, SWT.APPLICATION_MODAL);
		infoBox.setMessage(info);
		infoBox.open();
	}
	
	public void addDialogListener(String element, Listener listener) {
		switch (element) {
		case ("action"):
			actionButton.addListener(SWT.Selection, listener);
			break;
		case ("next"):
			nextPage.addListener(SWT.Selection, listener);
			break;
		case ("prev"):
			prevPage.addListener(SWT.Selection, listener);
			break;
		}
	}
	
	
}
