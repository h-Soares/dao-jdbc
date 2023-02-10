package app;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.GenericDao;
import model.entities.Department;
import model.entities.Seller;
/* import model.entities.Department;
import java.time.LocalDate; */
import java.math.BigDecimal;

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
            test2.forEach(System.out::println);  //Conferido 2x, mesmo objeto para department!
        System.out.println();

        System.out.println("---- TEST 4: Seller insert method ----");
        /* Department dpt = test.get(0).getDepartment(); //com seller.findByDepartment(2); !!
        Seller seller = new Seller("Pompew", "pompew@gmail.com", LocalDate.now(), BigDecimal.valueOf(1400), dpt);
        sellerDao.insert(seller);
        System.out.println("Inserted! Seller id: " + seller.getId()); FUNCIONOU! */ 
        System.out.println();

        System.out.println("---- TEST 5: Seller update method ----");
        Seller seller = sellerDao.findById(13);
        seller.setBaseSalary(BigDecimal.valueOf(4200));
        seller.setDepartment(new Department(4));
        sellerDao.update(seller);
        System.out.println();

        System.out.println("---- TEST 6: Seller deleteById method ----");
        //Seller seller = new Seller("Test", "test@gmail.com", LocalDate.now(), BigDecimal.valueOf(1400), new Department(4));
        //sellerDao.insert(seller);
        //sellerDao.deleteById(14);
    }
}