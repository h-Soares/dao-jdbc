package app;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.GenericDao;
import model.entities.Seller;
/* import model.entities.Department;
import java.math.BigDecimal;
import java.time.LocalDate; */


public class App {
    public static void main(String[] args) {
        GenericDao<Seller> sellerDao = DaoFactory.createSellerDao();
        
        System.out.println("---- TEST 1: Seller findById method ----");
        System.out.println(sellerDao.findById(7));
        System.out.println();

        System.out.println("---- TEST 2: Seller findByDepartment method ----");
        List<Seller> test = sellerDao.findByDepartment(2);
        if(test.isEmpty())
            System.out.println("There is no seller with this department id!");
        else {
            test.forEach(System.out::println);  
            //System.out.println(test.get(0).getDepartment() == test.get(1).getDepartment()); //TRUE -> mesmo objeto  
        }
        System.out.println();

        System.out.println("---- TEST 3: Seller findAll method ----");
        List<Seller> test2 = sellerDao.findAll();
        if(test2.isEmpty())
            System.out.println("There is no sellers!");
        else
            test2.forEach(System.out::println);  //Conferido, mesmo objeto para department!
        System.out.println();

        System.out.println("---- TEST 4: Seller insert method ----");
        /* Department dpt = test.get(0).getDepartment(); //com seller.findByDepartment(2); !!
        Seller seller = new Seller("Pompew", "pompew@gmail.com", LocalDate.now(), BigDecimal.valueOf(1400), dpt);
        sellerDao.insert(seller);
        System.out.println("Inserted! Seller id: " + seller.getId()); FUNCIONOU! */ 
    }
}