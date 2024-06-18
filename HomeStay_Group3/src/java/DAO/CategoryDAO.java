/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.Category;
import Model.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CategoryDAO {

    private Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public CategoryDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     public List<Category> GetAllCategory() {
        try {
            List<Category> listCate = new ArrayList<>();
            String sql = "SELECT * FROM Category";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category cate = new Category();
                cate.setId(rs.getInt("Id"));               
                cate.setName(rs.getString("Name"));
                listCate.add(cate);
            }
            return listCate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
