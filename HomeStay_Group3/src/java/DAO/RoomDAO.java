/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.Category;
import Model.Room;
import Model.ViewModel.BookingVM;
import Model.ViewModel.RoomServiceVM;
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

    public boolean BookingRoom(int roomId, int UserId) {
        try {
            String sql = "INSERT INTO Booking_Request (CreateAt, RoomId, BookBy, Status) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setString(1, currentDate);
            ps.setFloat(2, roomId);
            ps.setInt(3, UserId);
            ps.setInt(4, 0);
            int affectedRow = ps.executeUpdate();
            return affectedRow > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot Post HomeStay");
        }
        return false;
    }

    public List<BookingVM> getListRequestToOwner(int ownerId) {
        try {
            List<BookingVM> listBookingVM = new ArrayList<>();
            String sql = "SELECT "
                    + "br.RequestId, "
                    + "br.CreateAt, "
                    + "br.Status, "
                    + "r.Name, "
                    + "r.Price,"
                    + "r.RoomId, "
                    + "r.Image, "
                    + "u.UserName AS BookedByUserName, "
                    + "u.Email AS BookedByEmail, "
                    + "u.Phone AS BookedByPhone, "
                    + "o.UserName AS OwnerName, "
                    + "o.Email AS OwnerEmail, "
                    + "o.Phone AS OwnerPhone "
                    + "FROM Booking_Request br "
                    + "JOIN Room r ON br.RoomId = r.RoomId "
                    + "JOIN [User] u ON br.BookBy = u.UserId "
                    + "JOIN [Homestay] h ON r.HomeStayId = h.HomestayId "
                    + "JOIN [User] o ON br.BookBy = o.UserId "
                    + "WHERE h.UserId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, ownerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                BookingVM bookingInfo = new BookingVM();
                bookingInfo.setRequestId(rs.getInt("RequestId"));
                bookingInfo.setCreateAt(rs.getString("CreateAt"));
                bookingInfo.setStatus(rs.getInt("Status"));
                bookingInfo.setRoomName(rs.getString("Name"));
                bookingInfo.setPrice(rs.getFloat("Price"));
                bookingInfo.setRoomId(rs.getInt("RoomId"));
                byte[] imgData = rs.getBytes("Image");
                String base64Image = null;
                if (imgData != null) {
                    base64Image = Base64.getEncoder().encodeToString(imgData);
                }
                bookingInfo.setRoomImage(base64Image);

                bookingInfo.setBookedByUserName(rs.getString("BookedByUserName"));
                bookingInfo.setBookedByEmail(rs.getString("BookedByEmail"));
                bookingInfo.setBookedByPhone(rs.getString("BookedByPhone"));
                bookingInfo.setOwnerName(rs.getString("OwnerName"));
                bookingInfo.setOwnerEmail(rs.getString("OwnerEmail"));
                bookingInfo.setOwnerPhone(rs.getString("OwnerPhone"));
                listBookingVM.add(bookingInfo);
            }
            return listBookingVM;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BookingVM> getListRequestToBooker(int booker) {
        try {
            List<BookingVM> listBookingVM = new ArrayList<>();
            String sql = "SELECT "
                    + "br.RequestId, "
                    + "br.CreateAt, "
                    + "br.Status, "
                    + "r.Name, "
                    + "r.Price,"
                    + "r.RoomId, "
                    + "r.Image, "
                    + "u.UserName AS BookedByUserName, "
                    + "u.Email AS BookedByEmail, "
                    + "u.Phone AS BookedByPhone, "
                    + "o.UserName AS OwnerName, "
                    + "o.Email AS OwnerEmail, "
                    + "o.Phone AS OwnerPhone "
                    + "FROM Booking_Request br "
                    + "JOIN Room r ON br.RoomId = r.RoomId "
                    + "JOIN [User] u ON br.BookBy = u.UserId "
                    + "JOIN [Homestay] h ON r.HomeStayId = h.HomestayId "
                    + "JOIN [User] o ON br.BookBy = o.UserId "
                    + "WHERE br.BookBy = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, booker);
            rs = ps.executeQuery();
            while (rs.next()) {
                BookingVM bookingInfo = new BookingVM();
                bookingInfo.setRequestId(rs.getInt("RequestId"));
                bookingInfo.setCreateAt(rs.getString("CreateAt"));
                bookingInfo.setStatus(rs.getInt("Status"));
                byte[] imgData = rs.getBytes("Image");
                String base64Image = null;
                if (imgData != null) {
                    base64Image = Base64.getEncoder().encodeToString(imgData);
                }
                bookingInfo.setRoomImage(base64Image);
                bookingInfo.setRoomName(rs.getString("Name"));
                bookingInfo.setPrice(rs.getFloat("Price"));
                bookingInfo.setRoomId(rs.getInt("RoomId"));
                bookingInfo.setBookedByUserName(rs.getString("BookedByUserName"));
                bookingInfo.setBookedByEmail(rs.getString("BookedByEmail"));
                bookingInfo.setBookedByPhone(rs.getString("BookedByPhone"));
                bookingInfo.setOwnerName(rs.getString("OwnerName"));
                bookingInfo.setOwnerEmail(rs.getString("OwnerEmail"));
                bookingInfo.setOwnerPhone(rs.getString("OwnerPhone"));
                listBookingVM.add(bookingInfo);
            }
            return listBookingVM;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean ConfirmBooking(int requestId, int roomId) {
        try {
            String sql = "UPDATE [Booking_Request] SET Status = 1 WHERE RequestId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, requestId);
            int afftecRow = ps.executeUpdate();
            if (afftecRow > 0) {
                sql = "UPDATE [Room] SET Status = 2 WHERE RoomId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, roomId);
                afftecRow = ps.executeUpdate();
                return afftecRow > 0;
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean RejectBooking(int requestId, int roomId) {
        try {
            String sql = "UPDATE [Booking_Request] SET Status = 2 WHERE RequestId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, requestId);
            int afftecRow = ps.executeUpdate();
            if (afftecRow > 0) {
                sql = "UPDATE [Room] SET Status = 3 WHERE RoomId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, roomId);
                return afftecRow > 0;
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        RoomDAO roomDAO = new RoomDAO();
        List<RoomServiceVM> list = roomDAO.GetRoomService(7);
        for (RoomServiceVM roomServiceVM : list) {
            System.out.println(roomServiceVM.toString());
        }
    }

}
