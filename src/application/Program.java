package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
Department obj = new Department(2, "books");
//Seller seller = new Seller(1, "rulio", "rulio@gmail.com", new Date(), 3000.0, obj);


SellerDAO sellerDao = DaoFactory.createSellerDao();
System.out.println("=== test 1 === findById");
Seller seller  = sellerDao.findById(3);
System.out.println(seller);
System.out.println("=== test 2 === findByDepartmentId");
Department department = new Department(2, null);
List<Seller>list =  sellerDao.findByDepartment(department);
for (Seller objeto : list) {
	System.out.println(objeto);
}


	}

}
