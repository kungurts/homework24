import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ClientLog log = new ClientLog();
        Basket basket1 = new Basket(null, null, null);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");

        Boolean loadFile = false, saveFile = false, logFile = false;
        File loadFileName = null, saveFileName = null, logFileName= null;
        String loadFileFormat = null, saveFileFormat= null;

        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                NodeList list1 = node.getChildNodes();

                for (int a = 0; a < list1.getLength(); a++) {
                    String command = list1.item(a).getTextContent();
                    switch (node.getNodeName()) {
                        case "load":
                            if (list1.item(a).getNodeName().equals("enabled")) {
                                loadFile = (command.equals("true") ? true : false);
                            } else if (list1.item(a).getNodeName().equals("fileName")) {
                                loadFileName = new File(command);
                            } else if (list1.item(a).getNodeName().equals("format")) {
                                loadFileFormat = command;
                            }
                            break;
                        case "save":
                            if (list1.item(a).getNodeName().equals("enabled")) {
                                saveFile = (command.equals("true") ? true : false);
                            } else if (list1.item(a).getNodeName().equals("fileName")) {
                                saveFileName = new File(command);;
                            } else if (list1.item(a).getNodeName().equals("format")) {
                                saveFileFormat = command;
                            }
                            break;
                        case "log":
                            if (list1.item(a).getNodeName().equals("enabled")) {
                                logFile = (command.equals("true") ? true : false);
                            } else if (list1.item(a).getNodeName().equals("fileName")) {
                                logFileName = new File(command);
                            }
                            break;
                    }
                }
            }
        }
       
        if (loadFile) {
            if (loadFileName.exists()) {
                switch (loadFileFormat) {
                    case "txt":
                        basket1 = Basket.loadFromTextFile(loadFileName);
                        break;
                    case "json":
                        basket1 = Basket.loadFromJson(loadFileName);
                        break;
                }
                basket1.printCart();
            } else  basket1 = new Basket(new String[]{"Торт", "Молоко", "Кофе", "Творог", "Пирог"},
                    new int[]{1000, 70, 500, 130, 150});
        } else {
            basket1 = new Basket(new String[]{"Торт", "Молоко", "Кофе", "Творог", "Пирог"},
                new int[]{1000, 70, 500, 130, 150}); }

        basket1.PrintItems();

        while (true) {
            System.out.println();
            System.out.println("Выберите товар и его количество или введите end.");
            String input = sc.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]);
            int productQuantity = Integer.parseInt(parts[1]);
            basket1.addToCart(productNumber, productQuantity);
            log.log(productNumber, productQuantity);
        }
        if (logFile) {
            log.exportAcCSV(logFileName);
        }
        if (saveFile) {
            switch (saveFileFormat) {
                case "txt":
                    basket1.saveTxt(saveFileName);
                    break;
                case "json":
                    basket1.saveJson(saveFileName);
            }
        }
        basket1.printCart();

    }
}
