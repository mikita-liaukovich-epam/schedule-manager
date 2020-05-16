package ppvis_lab_2;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ImportController extends DefaultHandler {	
    private ArrayList<String[]> recordsList = new ArrayList<String[]>();
    private String[] record = null;
    private String value = null;
    private Boolean[] foundFields = { false, false, false, false, false };

    public ArrayList<String[]> getRecordsList() {
    	return recordsList;
    }
    
	@Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("Record")) {
            record = new String[6];
        }
        
        if (qName.equalsIgnoreCase("TrainID")) {
        	foundFields[0] = true;
        }
        
        if (qName.equalsIgnoreCase("DepartureStation")) {
        	foundFields[1] = true;
        }
        
        if (qName.equalsIgnoreCase("ArrivalStation")) {
        	foundFields[2] = true;
        }
        
        if (qName.equalsIgnoreCase("DepartureDateTime")) {
        	foundFields[3] = true;
        }
        
        if (qName.equalsIgnoreCase("ArrivalDateTime")) {
        	foundFields[4] = true;
        }
	}
	
	@Override
    public void endElement(String uri, String localName, String qName) {
		if (qName.equalsIgnoreCase("TrainID")) {
            record[0] = value;
        }
        
        if (qName.equalsIgnoreCase("DepartureStation")) {
            record[1] = value;
        }
        
        if (qName.equalsIgnoreCase("ArrivalStation")) {
            record[2] = value;
        }
        
        if (qName.equalsIgnoreCase("DepartureDateTime")) {
        	record[3] = value;
        }
        
        if (qName.equalsIgnoreCase("ArrivalDateTime")) {
        	record[4] = value;
        }
        
        if (qName.equalsIgnoreCase("Record")) {
            recordsList.add(record);
        }
    }
	
	@Override
    public void characters(char[] ch, int start, int length) {
        value = new String(ch, start, length);
    }
}