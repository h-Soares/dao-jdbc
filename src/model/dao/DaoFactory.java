package model.dao;

import model.dao.implementation.SellerDaoImplJDBC;
import model.entities.Seller;

public class DaoFactory {
    public static GenericDao<Seller> createSellerDao() { //Para não expor a implementação.
        return new SellerDaoImplJDBC(); //uma implementação para Seller da interface genérica GenericDao. É UM...
    } //Se, no futuro, quiser trocar o banco de dados, basta implementar para outro banco de dados e trocar o return.
}