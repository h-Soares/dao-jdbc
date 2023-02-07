package app;

import model.dao.DaoFactory;
import model.dao.GenericDao;
import model.entities.Seller;

public class App {
    public static void main(String[] args) {
        GenericDao<Seller> seller = DaoFactory.createSellerDao();
        System.out.println(seller.findById(7));
    }
}