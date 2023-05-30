package model.dao.impl;

import Db.DB;
import Db.DbExeception;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }
    @Override
    public void update(Seller seller) {

    }
    @Override
    public void deleteById(Integer id) {

    }
    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName FROM seller " +
                    "INNER JOIN department ON seller.DepartmentId = department.Id WHERE seller.Id = ?");

            st.setInt(1,id);
            rs = st.executeQuery();

            if (rs.next()){
                Department dep = instantiateDepartment(rs);
                return instantiateSeller(dep,rs);
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
    public List<Seller> FindAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id" +
                    " ORDER BY Name");

            List<Seller> sellers = new ArrayList<>();
            rs = st.executeQuery();

            Map<Integer,Department> map = new HashMap<>();
            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }

                Seller obj = instantiateSeller(dep,rs);

                sellers.add(obj);
            }
            return sellers;
        }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
        finally {
            DB.CloseResultSet(rs);
            DB.CloseStatement(st);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id WHERE" +
                    " DepartmentId = ? ORDER BY Name");

            List<Seller> sellers = new ArrayList<>();
            st.setInt(1,department.getId());
            rs = st.executeQuery();

            Map<Integer,Department> map = new HashMap<>();
            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }

                Seller obj = instantiateSeller(dep,rs);

                sellers.add(obj);
            }
            return sellers;
        }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
        finally {
            DB.CloseResultSet(rs);
            DB.CloseStatement(st);
        }
    }

    public Department instantiateDepartment(ResultSet rs){
        try {
            var dep = new Department();
            dep.setId(rs.getInt("DepartmentId"));
            dep.setName(rs.getString("DepName"));

            return dep;
        }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
    }

    public Seller instantiateSeller (Department dep,ResultSet rs){
        try {
            var obj = new Seller();
            obj.setId(rs.getInt("Id"));
            obj.setName(rs.getString("Name"));
            obj.setEmail(rs.getString("Email"));
            obj.setBaseSalary(rs.getDouble("BaseSalary"));
            obj.setBirthDate(rs.getDate("BirthDate"));
            obj.setDepartment(dep);
            return obj;
        }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }
    }
}
