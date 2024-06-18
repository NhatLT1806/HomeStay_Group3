/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.Category;
import Model.Homestay;
import Model.Room;
import Model.ViewModel.RoomServiceVM;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author Datnt
 */
public class RoomDAO {

    private Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public RoomDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int GetListRoomByHomeStayIdTotal(int homestayId) {
        try {
            List<Room> listRoom = new ArrayList<>();
            String sql = "SELECT COUNT(*) FROM Room WHERE HomestayId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Room> GetListRoomByHomeStayId(int homestayId) {
        try {
            List<Room> listRoom = new ArrayList<>();
            String sql = "SELECT * FROM Room WHERE HomestayId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setHomestayId(rs.getInt("HomestayId"));
                room.setRoomId(rs.getInt("RoomId"));
                room.setPrice(rs.getFloat("Price"));
                room.setName(rs.getString("Name"));
                room.setStatus(rs.getInt("Status"));
                room.setMaxParticipants(rs.getInt("MaxParticipant"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                room.setImage(imageBase64);
                listRoom.add(room);
            }
            return listRoom;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> GetListService() {
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

    public Room GetRoomById(int roomId) {
        try {
            String sql = "SELECT * FROM Room WHERE RoomID = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("RoomId"));
                room.setName(rs.getString("Name"));
                room.setPrice(rs.getFloat("Price"));
                room.setHomestayId(rs.getInt("HomeStayId"));
                room.setMaxParticipants(rs.getInt("MaxParticipant"));      
                room.setStatus(rs.getInt("Status"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                room.setImage(imageBase64);
                return room;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RoomServiceVM> GetRoomService(int roomId) {
        try {
            List<RoomServiceVM> listRoomService = new ArrayList<>();
            String sql = "SELECT\n"
                    + "    Room.RoomId,\n"
                    + "    Room.Name AS RoomName,\n"
                    + "    Category.Name AS CategoryName\n"
                    + "FROM\n"
                    + "    Room\n"
                    + "JOIN\n"
                    + "    Room_Service ON Room.RoomId = Room_Service.RoomId\n"
                    + "JOIN\n"
                    + "    Category ON Room_Service.CategoryId = Category.Id AND Room.RoomId = ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            while (rs.next()) {
                RoomServiceVM cate = new RoomServiceVM();
                cate.setCategoryName(rs.getString("CategoryName"));
                cate.setRoomName(rs.getString("RoomName"));
                cate.setRoomId(rs.getString("RoomId"));
                listRoomService.add(cate);
            }
            return listRoomService;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean InsertRoomService(int RoomId, List<String> cate) {
        try {
            boolean isSuccess = false;
            for (String _cate : cate) {
                String sql = "INSERT INTO [Room_Service] (RoomId, CategoryId)"
                        + " VALUES (?, ?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, RoomId);
                ps.setInt(2, Integer.parseInt(_cate));
                int affectedRow = ps.executeUpdate();
                if (affectedRow > 0) {
                    isSuccess = true;
                }
            }
            return isSuccess;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot Post HomeStay");
        }
        return false;
    }

    public boolean PostRoomByHomeStay(Room room, Part roomImage, List<String> cate) {
        try {
            String sql = "INSERT INTO [Room] (Name, Image, Price, MaxParticipant, HomestayId, Status )"
                    + " VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, room.getName());
            InputStream fileContent = roomImage.getInputStream();
            ps.setBinaryStream(2, fileContent, (int) roomImage.getSize());
            ps.setFloat(3, room.getPrice());
            ps.setInt(4, room.getMaxParticipants());
            ps.setInt(5, room.getHomestayId());
            ps.setInt(6, 1);
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                sql = "SELECT * FROM Room ORDER BY RoomId DESC";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    boolean result = InsertRoomService(rs.getInt("RoomId"), cate);
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot Post HomeStay");
        }
        return false;
    }

    public static void main(String[] args) {
        RoomDAO roomDAO = new RoomDAO();
    }
}
