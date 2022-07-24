package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
Department obj = new Department(2, "books");
//Seller seller = new Seller(1, "rulio", "rulio@gmail.com", new Date(), 3000.0, obj);


SellerDAO dao = DaoFactory.createSellerDao();
Seller seller  = dao.findById(3);
System.out.println(seller);
	}

}
