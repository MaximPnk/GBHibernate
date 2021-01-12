import dao.ProductDAO;
import dao.ProductDAOImpl;
import entity.Product;

import java.math.BigDecimal;

public class Main {

    static ProductDAO productDAO = new ProductDAOImpl();

    public static void main(String[] args) {

//        System.out.println(productDAO.findAll());

//        System.out.println(productDAO.findById(3L));

//        productDAO.saveOrUpdate(new Product("Milk", new BigDecimal(130)));
//        productDAO.saveOrUpdate(new Product("Orange", new BigDecimal(50)));
//        productDAO.saveOrUpdate(new Product("Grapefruit", new BigDecimal(70)));
//        productDAO.saveOrUpdate(new Product("Lemon", new BigDecimal(90)));
//        productDAO.saveOrUpdate(new Product("Cucumber", new BigDecimal(110)));
//        System.out.println(productDAO.findAll());

//        Product product = productDAO.findById(8);
//        product.setPrice(new BigDecimal(1300));
//        productDAO.saveOrUpdate(product);
//        System.out.println(productDAO.findAll());


//        productDAO.deleteById(8);
        System.out.println(productDAO.findAll());
    }
}
