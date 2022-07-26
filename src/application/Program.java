package application;

import java.util.Date;
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
System.out.println("=== test 3 === findByAll");
 list = sellerDao.findAll();
 for (Seller seller2 : list) {
	System.out.println(seller2);
}
 
 System.out.println("=== test 4 === insert");
Seller newSeller = new Seller(null,"Greg green" , "green@gmail.com", new Date(), 5000.0, department);
	sellerDao.insert(newSeller);
	System.out.println("inserted! "+newSeller.getId());
	
	System.out.println("=== test 5 update === ");
   seller = sellerDao.findById(1);
   seller.setName("Martha wine");
   sellerDao.update(seller);
   System.out.println("update copleted!");
	
	}
	

}
