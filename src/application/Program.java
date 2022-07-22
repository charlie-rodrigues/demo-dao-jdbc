package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
Department obj = new Department(2, "books");
Seller seller = new Seller(1, "rulio", "rulio@gmail.com", new Date(), 3000.0, obj);
System.out.println(seller);
SellerDAO dao = DaoFactory.createSellerDao();
	}

}
