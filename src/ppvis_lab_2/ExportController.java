package ppvis_lab_2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class ExportController {
    public void export(ArrayList<String[]> records, String filePath) {
        try {
            DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            Element rootElement = document.createElement("Records");
            document.appendChild(rootElement);

            records.forEach(record -> {
                Element recordElement = document.createElement("Record");

                Element trainID = document.createElement("TrainID");
                trainID.setTextContent(record[0]);
                recordElement.appendChild(trainID);

                Element departureStation = document.createElement("DepartureStation");
                departureStation.setTextContent(record[1]);
                recordElement.appendChild(departureStation);
                
                Element arrivalStation = document.createElement("ArrivalStation");
                arrivalStation.setTextContent(record[2]);
                recordElement.appendChild(arrivalStation);
                
                Element departureDateTime = document.createElement("DepartureDateTime");
                departureDateTime.setTextContent(record[3]);
                recordElement.appendChild(departureDateTime);
                
                Element arrivalDateTime = document.createElement("ArrivalDateTime");
                arrivalDateTime.setTextContent(record[4]);
                recordElement.appendChild(arrivalDateTime);
                
                rootElement.appendChild(recordElement);
            });
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
}
