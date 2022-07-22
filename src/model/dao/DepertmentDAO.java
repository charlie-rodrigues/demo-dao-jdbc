package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepertmentDAO {
void insert(Department obj);
void update(Department obj);
void delete(Department obj);
Department findById(Integer id);
List<Department>findAll();

}
