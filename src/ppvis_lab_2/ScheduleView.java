package ppvis_lab_2;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class ScheduleView {
	
	private Display display;
	protected Shell shell;
	
	private ToolItem addItem;
	private MenuItem addMenuItem;
	
	private ToolItem deleteItem;
	private MenuItem deleteMenuItem;
	
	private ToolItem searchItem;
	private MenuItem searchMenuItem;
	
	private ToolItem importItem;
	private MenuItem importMenuItem;
	
	private ToolItem exportItem;
	private MenuItem exportMenuItem;
	
	protected Table timeTable;
	
	public ScheduleView() {
		display = new Display();
		    
	    RowLayout layout = new RowLayout();
	    layout.type = SWT.VERTICAL;
	    createShell(layout);
	    
	    Menu menuBar = new Menu(shell, SWT.BAR);	    
	    ToolBar instrumentsPanel = new ToolBar(shell, SWT.BORDER|SWT.HORIZONTAL);
		Composite scheduleContainer = new Composite(shell, SWT.BORDER);
			
		createMenu(menuBar);
		createToolBar(instrumentsPanel);
		createTable(scheduleContainer);
	}
	
	public void open() {	    
	    shell.pack();
	    shell.open();
	 
	    while (!shell.isDisposed()) {
	        if (!display.readAndDispatch())
	            display.sleep();
	    }
	    display.dispose();
	  }
	  
	protected void createShell(RowLayout layout) {
	  shell = new Shell();
	 
	  shell.setLayout(layout);
	  shell.setText("PPVIS_LAB2_var13");
	}
	
	public void createTable(Composite composite) {
		timeTable = new Table(composite, SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		timeTable.setHeaderVisible(true);
		timeTable.setLinesVisible(true);
		timeTable.setSize(820, 500);
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
	}
	
	public void createMenu(Menu appMenu) {
	    shell.setMenuBar(appMenu);
	    
	    Menu fileSubMenu = new Menu(shell, SWT.DROP_DOWN);
	    
	    MenuItem fileItem = new MenuItem(appMenu, SWT.CASCADE);
	    fileItem.setText("File");
	    fileItem.setMenu(fileSubMenu);
	    
	    importMenuItem = new MenuItem(fileSubMenu, SWT.PUSH);
	    importMenuItem.setText("Import\tCtrl+O");
	    importMenuItem.setAccelerator(SWT.MOD1 + 'O');
	    
	    exportMenuItem = new MenuItem(fileSubMenu, SWT.PUSH);
	    exportMenuItem.setText("Export\tCtrl+S");
	    exportMenuItem.setAccelerator(SWT.MOD1 + 'S');
	    
	    Menu editSubMenu = new Menu(shell, SWT.DROP_DOWN);
	    
	    MenuItem editItem = new MenuItem(appMenu, SWT.CASCADE);
	    editItem.setText("Edit");
	    editItem.setMenu(editSubMenu);

	    addMenuItem = new MenuItem(editSubMenu, SWT.PUSH);
	    addMenuItem.setText("New record\tCtrl+N");
	    addMenuItem.setAccelerator(SWT.MOD1 + 'N');
	    
	    deleteMenuItem = new MenuItem(editSubMenu, SWT.PUSH);
	    deleteMenuItem.setText("Delete record\tCtrl+D");
	    deleteMenuItem.setAccelerator(SWT.MOD1 + 'D');
	    
	    searchMenuItem = new MenuItem(appMenu, SWT.CASCADE);
	    searchMenuItem.setText("Search");
	    searchMenuItem.setAccelerator(SWT.MOD1 + 'F');
	}
	
	public void createToolBar(ToolBar instrumentsPanel) {
		addItem = new ToolItem(instrumentsPanel, SWT.PUSH|SWT.BORDER);
		addItem.setText("  ✚  ");
		addItem.setToolTipText("Add record");
		
		deleteItem = new ToolItem(instrumentsPanel, SWT.PUSH|SWT.BORDER);
		deleteItem.setText("  ━  ");
		deleteItem.setToolTipText("Delete record");
		
		new ToolItem(instrumentsPanel, SWT.SEPARATOR);
		
		searchItem = new ToolItem(instrumentsPanel, SWT.PUSH|SWT.BORDER);
		searchItem.setToolTipText("Find records");
		searchItem.setText("  🔍  ");
		
		new ToolItem(instrumentsPanel, SWT.SEPARATOR);
		
		exportItem = new ToolItem(instrumentsPanel, SWT.PUSH|SWT.BORDER);
		exportItem.setToolTipText("Export to XML");
		exportItem.setText("  📤  ");
		
		importItem = new ToolItem(instrumentsPanel, SWT.PUSH|SWT.BORDER);
		importItem.setToolTipText("Import from XML");
		importItem.setText("  📥  ");
	}
	
	public TableItem createTableItem() {
		return new TableItem(timeTable, SWT.NONE);
	}
	
	public void addToolListener(String action, Listener listener) {
		switch (action) {
		case ("add"):
			addItem.addListener(SWT.Selection, listener);
			addMenuItem.addListener(SWT.Selection, listener);
			break;
		case ("delete"):
			deleteItem.addListener(SWT.Selection, listener);
			deleteMenuItem.addListener(SWT.Selection, listener);
			break;
		case ("search"):
			searchItem.addListener(SWT.Selection, listener);
			searchMenuItem.addListener(SWT.Selection, listener);
			break;
		case ("import"):
			importItem.addListener(SWT.Selection, listener);
			importMenuItem.addListener(SWT.Selection, listener);
			break;
		case ("export"):
			exportItem.addListener(SWT.Selection, listener);
			exportMenuItem.addListener(SWT.Selection, listener);
			break;
		}	
	}
	
	public void clearTable() {
		timeTable.removeAll();
	}
	
}
