package app;

import java.math.BigDecimal;
import java.time.LocalDate;
import entities.Department;
import entities.Seller;

public class App {
    public static void main(String[] args) throws Exception {
        Department dpt = new Department(1, "Computers");

        Seller seller = new Seller(21, "Lionel", "lionel@gmail.com", LocalDate.now(), BigDecimal.valueOf(2200), dpt);

        System.out.println(dpt);
        System.out.println();
        System.out.println(seller);       
    }
}