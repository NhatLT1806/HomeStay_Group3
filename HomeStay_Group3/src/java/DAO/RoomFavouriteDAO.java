/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.Room;
import Model.RoomFavourite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class RoomFavouriteDAO extends DBContext {

    private Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public RoomFavouriteDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAddToFavouriteList(int userId, int roomId) {
        try {
            String sql = "SELECT * FROM  Room_Favourite WHERE RoomId = ? AND UserId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean addRoomToFavourite(int userId, int roomId) {
        try {
            boolean result = isAddToFavouriteList(userId, roomId);
            if (result) {
                return false;
            } else {
                String sql = "INSERT INTO Room_Favourite (RoomId, UserId, CreateAt) VALUES (?, ?, ?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, roomId);
                ps.setInt(2, userId);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String currentDate = dateFormat.format(date);
                ps.setString(3, currentDate);
                int affectedRow = ps.executeUpdate();
                return affectedRow > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeRoomFromFavourite(int userId, int roomId) {
        try {
            String sql = "DELETE FROM Room_Favourite WHERE RoomId = ? AND UserId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            ps.setInt(2, userId);
            int affectedRow = ps.executeUpdate();
            return affectedRow > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Room> getFavouriteRoomsByUserId(int userId, int index) {
        List<Room> favouriteRooms = new ArrayList<>();
        try {
            String sql = "SELECT r.RoomId, r.Name, r.Price, r.Area, r.MaxParticipant, r.HomestayId, r.Status, r.Image FROM Room_Favourite rf "
                    + "JOIN Room r ON r.RoomId = rf.RoomId WHERE rf.UserId = ? ORDER BY r.RoomId DESC OFFSET ? ROWS FETCH NEXT 8 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);     
            ps.setInt(2, (index - 1) * 8);

            rs = ps.executeQuery();
            while (rs.next()) {
                Room roomFavourite = new Room();
                roomFavourite.setHomestayId(rs.getInt("HomestayId"));
                roomFavourite.setRoomId(rs.getInt("RoomId"));
                roomFavourite.setPrice(rs.getFloat("Price"));
                roomFavourite.setName(rs.getString("Name"));
                roomFavourite.setStatus(rs.getInt("Status"));
                roomFavourite.setArea(rs.getInt("Area"));
                roomFavourite.setMaxParticipants(rs.getInt("MaxParticipant"));
                byte[] imageByte = rs.getBytes("Image");
                String imageBase64 = Base64.getEncoder().encodeToString(imageByte);
                roomFavourite.setImage(imageBase64);
                favouriteRooms.add(roomFavourite);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favouriteRooms;
    }
    
    public int  getFavouriteRoomsByUserIdTotal(int userId) {
        List<Room> favouriteRooms = new ArrayList<>();
        try {
            String sql = "SELECT COUNT(*) FROM Room_Favourite rf "
                    + "JOIN Room r ON r.RoomId = rf.RoomId WHERE rf.UserId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
               return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
