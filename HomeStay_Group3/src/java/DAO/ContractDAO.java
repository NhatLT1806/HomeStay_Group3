/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import Model.User;
import Model.ViewModel.RoomServiceVM;
import DAL.DBContext;
import Model.Contract;
import Model.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ContractDAO {

    private Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public ContractDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Contract getContractByContractId(int contractId) {
        try {
            Contract contract = new Contract();
            String sql = "SELECT * FROM Contract WHERE ContractId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, contractId);
            rs = ps.executeQuery();
            if (rs.next()) {
                contract.setContractId(rs.getInt("ContractId"));
                contract.setName(rs.getString("Name"));
                contract.setOwnerId(rs.getInt("OwnerId"));
                contract.setUserId(rs.getInt("UserId"));
                contract.setRoomId(rs.getInt("RoomId"));
                contract.setHomestayId(rs.getInt("HomestayId"));
                contract.setContent(rs.getString("Content"));
                contract.setCreateAt(rs.getString("CreateAt"));
                contract.setStatus(rs.getInt("Status"));
                contract.setStartDate(rs.getString("StartDate"));
                contract.setEndDate(rs.getString("EndDate"));
                return contract;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Room getRoomById(int roomId) {
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
                room.setArea(rs.getFloat("Area"));
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

    public List<RoomServiceVM> getRoomServices(int roomId) {
        try {
            List<RoomServiceVM> listRoomService = new ArrayList<>();
            String sql = "SELECT\n" + "    Room.RoomId,\n" + "    Room.Name AS RoomName,\n" + "    Category.Name AS CategoryName\n" + "FROM\n" + "    Room\n" + "JOIN\n" + "    Room_Service ON Room.RoomId = Room_Service.RoomId\n" + "JOIN\n" + "    Category ON Room_Service.CategoryId = Category.Id AND Room.RoomId = ? ";
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

    public User getOwnerByHomestayId(int homestayId) {
        try {
            String sql = "SELECT * FROM Homestay h JOIN [USER] u ON h.UserId = u.UserId WHERE HomestayId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                byte[] imgData = rs.getBytes("Avatar");
                String base64Image = null;
                if (imgData != null) {
                    base64Image = Base64.getEncoder().encodeToString(imgData);
                }
                user.setAvatar(base64Image);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
     public User getUserById(int customerId) {
        try {
            String sql = "SELECT * FROM  [USER]  WHERE UserId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                byte[] imgData = rs.getBytes("Avatar");
                String base64Image = null;
                if (imgData != null) {
                    base64Image = Base64.getEncoder().encodeToString(imgData);
                }
                user.setAvatar(base64Image);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
}
