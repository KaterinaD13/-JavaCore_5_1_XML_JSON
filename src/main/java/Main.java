import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String xmlFileName = "data.xml";
        List<Employee> list = parseXML(xmlFileName);
        String json2 = listToJson(list);
        String jsonFilename2 = "data2.json";
        writeString(json2, jsonFilename2);
    }

    private static List<Employee> parseXML(String xmlFileName) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        List<String> elements = new ArrayList<>();
        List<Employee> list1 = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element staff = document.createElement("staff");
        document.appendChild(staff);
        Element employee = document.createElement("employee");
        staff.appendChild(employee);
        Element id = document.createElement("id");
        id.appendChild(document.createTextNode("1"));
        employee.appendChild(id);
        Element firstName = document.createElement("firstName");
        firstName.appendChild(document.createTextNode("John"));
        employee.appendChild(firstName);
        Element lastName = document.createElement("lastName");
        lastName.appendChild(document.createTextNode("Smith"));
        employee.appendChild(lastName);
        Element country = document.createElement("country");
        country.appendChild(document.createTextNode("USA"));
        employee.appendChild(country);
        Element age = document.createElement("age");
        age.appendChild(document.createTextNode("25"));
        employee.appendChild(age);
        Element employee1 = document.createElement("employee");
        staff.appendChild(employee1);
        Element id1 = document.createElement("id");
        id1.appendChild(document.createTextNode("2"));
        employee1.appendChild(id1);
        Element firstName1 = document.createElement("firstName");
        firstName1.appendChild(document.createTextNode("Ivan"));
        employee1.appendChild(firstName1);
        Element lastName1 = document.createElement("lastName");
        lastName1.appendChild(document.createTextNode("Petrov"));
        employee1.appendChild(lastName1);
        Element country1 = document.createElement("country");
        country1.appendChild(document.createTextNode("RU"));
        employee1.appendChild(country1);
        Element age1 = document.createElement("age");
        age1.appendChild(document.createTextNode("23"));
        employee1.appendChild(age1);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // для красивого вывода в консоль
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        //печатаем в консоль или файл
        StreamResult console = new StreamResult(System.out);
        StreamResult streamResult = new StreamResult(new File(xmlFileName));
        //записываем данные
        transformer.transform(domSource, console);
        transformer.transform(domSource, streamResult);
        System.out.println("Создание XML файла закончено");
        Document doc = builder.parse(new File(xmlFileName));
        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equals("employee")) {
                NodeList nodeList1 = node.getChildNodes();
                for (int j = 0; j < nodeList1.getLength(); j++) {
                    Node node_ = nodeList1.item(j);
                    if (Node.ELEMENT_NODE == node_.getNodeType()) {
                        elements.add(node_.getTextContent());
                    }
                }
                list1.add(new Employee(
                        Long.parseLong(elements.get(0)),
                        elements.get(1),
                        elements.get(2),
                        elements.get(3),
                        Integer.parseInt(elements.get(4))));
                elements.clear();
            }
        }
        return list1;
    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return gson.toJson(list, listType);
    }

    private static void writeString(String json2, String jsonFilename2) {
        try (FileWriter file = new FileWriter(jsonFilename2)) {
            file.write(json2);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}