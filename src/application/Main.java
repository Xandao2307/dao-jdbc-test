package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Seller;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao= DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        DepartmentDao departmentDao =  DaoFactory.createDepartmentDao();
    }
}