package app;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.GenericDao;
import model.entities.Seller;

public class App {
    public static void main(String[] args) {
        GenericDao<Seller> seller = DaoFactory.createSellerDao();
        
        System.out.println("---- TEST 1: Seller findById method ----");
        System.out.println(seller.findById(7));
        System.out.println();

        System.out.println("---- TEST 2: Seller findByDepartment method ----");
        List<Seller> test = seller.findByDepartment(5);
        if(test.isEmpty())
            System.out.println("There is no seller with this department id!");
        else {
            test.forEach(System.out::println);  
            System.out.println(test.get(0).getDepartment() == test.get(1).getDepartment()); //TRUE -> mesmo objeto  
        }
        System.out.println();

        System.out.println("---- TEST 3: Seller findAll method ----");
        List<Seller> test2 = seller.findAll();
        if(test2.isEmpty())
            System.out.println("There is no sellers!");
        else
            test2.forEach(System.out::println);  //Conferido, mesmo objeto para department!
    }
}