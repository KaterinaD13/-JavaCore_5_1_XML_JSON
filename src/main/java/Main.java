import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.*;


public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String xmlFileName = "data.xml";
        List<Employee> list = parseXML(xmlFileName);
        String json2 = listToJson(list);
        String jsonFilename2 = "data2.json";
        writeString(json2, jsonFilename2);
    }

    private static List<Employee> parseXML(String xmlFileName) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        List<Employee> list1 = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(xmlFileName));
        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                list1.add(new Employee(
                        Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()),
                        element.getElementsByTagName("firstName").item(0).getTextContent(),
                        element.getElementsByTagName("lastName").item(0).getTextContent(),
                        element.getElementsByTagName("country").item(0).getTextContent(),
                        Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent())));
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