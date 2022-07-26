package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDAO {
	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
PreparedStatement stmt = null;
try {
	stmt = conn.prepareStatement("insert into seller (Name, Email, BirthDate, BaseSalary, DepartmentId) values (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
	stmt.setString(1, obj.getName());
	stmt.setString(2, obj.getEmail());
	stmt.setDate(3, new java.sql.Date(obj.getBithDate().getTime()));
	stmt.setDouble(4, obj.getBaseSalary());
	stmt.setInt(5, obj.getDepartment().getId());
	int rowsAffected = stmt.executeUpdate();
	if(rowsAffected>0) {
		ResultSet rs = stmt.getGeneratedKeys();
		if(rs.next()) {
			int id = rs.getInt(1);
			obj.setId(id);
		}
		DB.closedResultSet(rs);
	}else {
	throw new DbException("erro inespeado, nenhuma linha afetada!");
	}
} catch (SQLException e) {
	throw new DbException("error "+e.getMessage());
}finally {
	DB.closedStatement(stmt);
	
}
	}

	@Override
	public void update(Seller obj) {
PreparedStatement stmt = null;
try {
	stmt = conn.prepareStatement("update seller set Name=?, Email=?,BirthDate=?,BaseSalary=?, DepartmentId=? where Id=?");
	stmt.setString(1, obj.getName());
	stmt.setString(2, obj.getEmail());
	stmt.setDate(3, new java.sql.Date(obj.getBithDate().getTime()));
	stmt.setDouble(4, obj.getBaseSalary());
	stmt.setInt(5, obj.getDepartment().getId());
	stmt.setInt(6, obj.getId());
	stmt.executeUpdate();
	
	
} catch (SQLException e) {
throw new DbException("error: "+e.getMessage());
}finally {
	DB.closedStatement(stmt);
}
	}

	@Override
	public void delete(Integer id) {
     PreparedStatement stmt = null;
     try {
		stmt = conn.prepareStatement("delete from seller where id=? ");
		stmt.setInt(1, id);
		stmt.executeUpdate();
	} catch (SQLException e) {
		throw new DbException("error: "+e.getMessage());
	}finally {
		DB.closedStatement(stmt);
	}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(
					"select seller.*, department.Name as DepName from seller inner join department on seller.DepartmentId = department.id where seller.id = ? ");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				Department dep = instanciateDepartment(rs);
				Seller obj = intanciateSeller(rs, dep);
				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {

			DB.closedStatement(stmt);
			DB.closedResultSet(rs);
		}
	}

	private Seller intanciateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBithDate(rs.getDate("BirthDate"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setDepartment(dep);
		return obj;

	}

	private Department instanciateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));

		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("select seller.*, " + "department.Name" + " as DepName from" + " seller inner"
					+ " join department" + " on seller.DepartmentId=department.Id");
			rs = stmt.executeQuery();
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				Department dep = instanciateDepartment(rs);
				map.put(rs.getInt("DepartmentId"), dep);
				Seller obj = intanciateSeller(rs, dep);
				list.add(obj);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException("erro" + e.getMessage());
		} finally {
			DB.closedStatement(stmt);
			DB.closedResultSet(rs);
		}

	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(
					"select seller.*, department.Name as DepName from seller inner join department on seller.DepartmentId=department.Id where seller.DepartmentId=? order by Name");
			stmt.setInt(1, department.getId());
			rs = stmt.executeQuery();
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instanciateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);

				}
				Seller obj = intanciateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException("erro: " + e.getMessage());
		} finally {
			DB.closedStatement(stmt);
			DB.closedResultSet(rs);
		}

	}

}
