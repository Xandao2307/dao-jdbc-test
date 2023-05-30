package model.dao.impl;

import Db.DB;
import Db.DbExeception;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static model.dao.impl.SellerDaoJDBC.instantiateDepartment;
import static model.dao.impl.SellerDaoJDBC.instantiateSeller;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection conn;

    @Override
    public void insert(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("insert into department (Id, Name) values (?,?)");

            st.setInt(1,department.getId());
            st.setString(1,department.getName());
            rs = st.executeQuery();

        }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
        finally {
            DB.CloseResultSet(rs);
            DB.CloseStatement(st);
        }
    }
    @Override
    public void update(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("update department set Name = ? where Id = ?", Statement.RETURN_GENERATED_KEYS);

            st.setString(1,department.getName());
            st.setInt(1,department.getId());
            rs = st.executeQuery();

        }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
        finally {
            DB.CloseResultSet(rs);
            DB.CloseStatement(st);
        }
    }
    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            st.setInt(1,id);

        }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
        finally {
            DB.CloseStatement(st);
        }
    }
    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("select * from department where Id = ?");

            st.setInt(1,id);
            rs = st.executeQuery();

            if (rs.next()){
                return instantiateDepartment(rs);
            }
            return null;
        }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
        finally {
            DB.CloseResultSet(rs);
            DB.CloseStatement(st);
        }
    }
    @Override
    public List<Department> FindAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Department> departments = new ArrayList<>();
        try{
            st = conn.prepareStatement("select * from department");

            rs = st.executeQuery();

            while (rs.next()){
                Department dep = instantiateDepartment(rs);
                departments.add(dep) ;
            }
            return departments;
        }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
        finally {
            DB.CloseResultSet(rs);
            DB.CloseStatement(st);
        }
    }
}
