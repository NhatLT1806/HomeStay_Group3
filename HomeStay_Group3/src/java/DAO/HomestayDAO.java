/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.Homestay;
import Model.User;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Datnt
 */
public class HomestayDAO {

    private Connection con;
    private List<User> user;
    PreparedStatement ps;
    ResultSet rs;

    public HomestayDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean PostHomestay(Homestay homestay, Part homeStayImage) {
        try {
            String sql = "INSERT INTO [Homestay] (Name, Image, Address, Status, UserId, Description, CreateAt)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, homestay.getName());

            InputStream fileContent = homeStayImage.getInputStream();
            ps.setBinaryStream(2, fileContent, (int) homeStayImage.getSize());

            ps.setString(3, homestay.getAddress());
            ps.setInt(4, homestay.getStatus());
            ps.setInt(5, homestay.getUserId());
            ps.setString(6, homestay.getDescription());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setString(7, currentDate);
            int affectedRow = ps.executeUpdate();
            return affectedRow > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot Post HomeStay");
        }
        return false;
    }

    public int getTotalHomePage() {
        try {
            String sql = "Select COUNT(*) FROM Homestay";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Homestay> getAll_HomePage(int index) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            // status = 0;
            // 
            String sql = "SELECT * FROM Homestay WHERE Status = 1 ORDER BY CreateAt DESC OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, (index - 1) * 12);
            rs = ps.executeQuery();
            while (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                listHomeStay.add(homestay);
            }
            return listHomeStay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getAll_HomePageSearchTotal(String search) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            // status = 0;
            // 
            String sql = "SELECT COUNT(*) FROM Homestay WHERE Status = 1 AND Name LIKE ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Homestay> getAll_HomePageSearch(int index, String search) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            // status = 0;
            // 
            String sql = "SELECT * FROM Homestay WHERE Status = 1 AND Name LIKE ? ORDER BY CreateAt DESC OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, (index - 1) * 12);

            rs = ps.executeQuery();
            while (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                listHomeStay.add(homestay);
            }
            return listHomeStay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Homestay> GetListHomeStayByUserId(int userId, int index) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "SELECT * FROM Homestay WHERE UserId = ? ORDER BY HomestayId DESC OFFSET ? ROWS FETCH NEXT 8 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, (index - 1) * 8);
            rs = ps.executeQuery();
            while (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                listHomeStay.add(homestay);
            }
            return listHomeStay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Homestay> GetListHomeStayByUserId_All(int userId) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "SELECT * FROM Homestay WHERE UserId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                listHomeStay.add(homestay);
            }
            return listHomeStay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int GetListHomeStayByUserIdTotal(int userId) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "SELECT COUNT(*) FROM Homestay WHERE UserId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Homestay GetHomestayById(int homestayId) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "SELECT * FROM Homestay WHERE HomestayId = ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                return homestay;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Homestay> getHomeStayWaitToConfirm(int index) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "SELECT * FROM Homestay WHERE Status = 0 ORDER BY CreateAt DESC OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, (index - 1) * 12);
            rs = ps.executeQuery();
            while (rs.next()) {
                Homestay homestay = new Homestay();
                homestay.setHomestayId(rs.getInt("HomestayId"));
                homestay.setAddress(rs.getString("Address"));
                homestay.setDescription(rs.getString("Description"));
                homestay.setName(rs.getString("Name"));
                homestay.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                homestay.setImage(imageBase64);
                listHomeStay.add(homestay);
            }
            return listHomeStay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean AcceptHomeStay(int homestayId) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "UPDATE Homestay SET Status = 1 WHERE HomestayId = ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            int affectedRow = ps.executeUpdate();
            return affectedRow > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean RejectHomeStay(int homestayId) {
        try {
            List<Homestay> listHomeStay = new ArrayList<>();
            String sql = "UPDATE Homestay SET Status = 2 WHERE HomestayId = ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            int affectedRow = ps.executeUpdate();
            return affectedRow > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        HomestayDAO dao = new HomestayDAO();
        List<Homestay> list = dao.GetListHomeStayByUserId(6, 1);
        System.out.println(list.toString());
    }
}
