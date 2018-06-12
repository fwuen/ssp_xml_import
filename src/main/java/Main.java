import Provider.ProductProvider;
import Provider.ProductTypeProvider;
import Provider.ProductionOrderProvider;
import Provider.ProductionProvider;
import model.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.importXml();
    }

    public void importXml() throws Exception {
        XmlImporter xmlImporter = new XmlImporter();
        ProductTypeProvider productTypeProvider = new ProductTypeProvider();
        ProductProvider productProvider = new ProductProvider();
        ProductionOrderProvider productionOrderProvider = new ProductionOrderProvider();
        ProductionProvider productionProvider = new ProductionProvider();

        InputStream initialStream = getClass().getResourceAsStream("xml/prod_type.xml");
        File targetFile = new File("xml/prod_type.tmp.xml");
        FileUtils.copyInputStreamToFile(initialStream, targetFile);
        xmlImporter.setFile(targetFile);
        List<ProductType> productTypes = xmlImporter.readProductTypes();
        for (ProductType productType : productTypes) {
            productTypeProvider.writeProductType(productType);
        }

        initialStream = getClass().getResourceAsStream("xml/product.xml");
        targetFile = new File("xml/product.tmp.xml");
        FileUtils.copyInputStreamToFile(initialStream, targetFile);
        xmlImporter.setFile(targetFile);
        List<Product> products = xmlImporter.readProducts();
        for (Product product : products) {
            productProvider.writeProduct(product);
        }

        initialStream = getClass().getResourceAsStream("xml/production_order.xml");
        targetFile = new File("xml/production_order.tmp.xml");
        FileUtils.copyInputStreamToFile(initialStream, targetFile);
        xmlImporter.setFile(targetFile);
        List<ProductionOrder> productionOrders = xmlImporter.readProductionOrders(products);
        for (ProductionOrder productionOrder : productionOrders) {
            productionOrderProvider.writeProductionOrder(productionOrder);
            for (ProductionOrderItems productionOrderItems : productionOrder.getProductionOrderItems()) {
                productionOrderProvider.writeProductionOrderItems(productionOrderItems);
            }
        }

        initialStream = getClass().getResourceAsStream("xml/production.xml");
        targetFile = new File("xml/production.tmp.xml");
        FileUtils.copyInputStreamToFile(initialStream, targetFile);
        xmlImporter.setFile(targetFile);
        List<Production> productions = xmlImporter.readProductions();
        for (Production production : productions) {
            productionProvider.writeProduction(production);
        }
    }

    //src\main\resources\
}
