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
import java.util.List;

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
                Seller obj = instantiateSeller(dep,rs);
                return obj;
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
        return null;
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
