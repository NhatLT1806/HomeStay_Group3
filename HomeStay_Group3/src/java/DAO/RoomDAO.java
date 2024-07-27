/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.BookingRequest;
import Model.Category;
import Model.Contract;
import Model.Room;
import Model.User;
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
            String sql = "SELECT COUNT(*) FROM Room WHERE HomestayId = ? AND Status != 0";
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

    public List<Contract> getAllContract() {
        List<Contract> listContract = new ArrayList();
        try {
            String sql = "SELECT * FROM Contract";
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                Contract contract = new Contract();
                contract.setRoomId(rs.getInt("RoomId"));
                contract.setName(rs.getString("Name"));
                contract.setContractId(rs.getInt("ContractId"));
                contract.setHomestayId(rs.getInt("HomestayId"));
                contract.setUserId(rs.getInt("UserId"));
                listContract.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listContract;
    }

    public List<Room> GetAllListRoomByHomeStayId(int homestayId) {
        try {
            List<Room> listRoom = new ArrayList<>();

            // check room đó có hợp dồng chưa nếu chưa có thì lấy sql trên
//            String sql = "SELECT r.*, c.Status as cStatus, c.ContractId, c.HomestayId as cHomestayId,"
//                    + " c.RoomId as cRoomId "
//                    + "FROM Room r  JOIN Contract c ON r.RoomId = c.RoomId WHERE r.HomestayId = ?";
            String sql = "SELECT * FROM Room r  WHERE r.HomestayId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);

            rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();
//                Contract contract = new Contract();
//                contract.setStatus(rs.getInt("cStatus"));
//                contract.setContractId(rs.getInt("ContractId"));
//                room.setContract(contract);
                room.setHomestayId(rs.getInt("HomestayId"));
                room.setRoomId(rs.getInt("RoomId"));
                room.setPrice(rs.getFloat("Price"));
                room.setName(rs.getString("Name"));
                room.setStatus(rs.getInt("Status"));
                room.setArea(rs.getInt("Area"));
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

    public List<Room> GetListRoomByHomeStayId(int homestayId, int index) {
        try {
            List<Room> listRoom = new ArrayList<>();
            String sql = "SELECT * FROM Room WHERE HomestayId = ? AND Status != 0 ORDER BY RoomId DESC OFFSET ? ROW FETCH NEXT 8 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            ps.setInt(2, (index - 1) * 8);

            rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setHomestayId(rs.getInt("HomestayId"));
                room.setRoomId(rs.getInt("RoomId"));
                room.setPrice(rs.getFloat("Price"));
                room.setName(rs.getString("Name"));
                room.setStatus(rs.getInt("Status"));
                room.setArea(rs.getInt("Area"));
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
            String sql = "INSERT INTO [Room] (Name, Image, Price, MaxParticipant, HomestayId, Status, Area )"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, room.getName());
            InputStream fileContent = roomImage.getInputStream();
            ps.setBinaryStream(2, fileContent, (int) roomImage.getSize());
            ps.setFloat(3, room.getPrice());
            ps.setInt(4, room.getMaxParticipants());
            ps.setInt(5, room.getHomestayId());
            ps.setInt(6, 1);
            ps.setFloat(7, room.getArea());

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

    public int getListRequestToOwnerTotal(int ownerId, int status) {
        try {
            List<BookingVM> listBookingVM = new ArrayList<>();
            String sql = "SELECT COUNT(*) "
                    + "FROM Booking_Request br "
                    + "JOIN Room r ON br.RoomId = r.RoomId "
                    + "JOIN [User] u ON br.BookBy = u.UserId "
                    + "JOIN [Homestay] h ON r.HomeStayId = h.HomestayId "
                    + "JOIN [User] o ON br.BookBy = o.UserId "
                    + "WHERE h.UserId = ? AND br.Status = ? AND br.Status != 3 ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, ownerId);
            ps.setInt(2, status);

            if (status == 4) {
                sql = "SELECT COUNT(*) "
                        + "FROM Booking_Request br "
                        + "JOIN Room r ON br.RoomId = r.RoomId "
                        + "JOIN [User] u ON br.BookBy = u.UserId "
                        + "JOIN [Homestay] h ON r.HomeStayId = h.HomestayId "
                        + "JOIN [User] o ON br.BookBy = o.UserId "
                        + "WHERE h.UserId = ? AND br.Status != 3 ";
                ps = con.prepareStatement(sql);
                ps.setInt(1, ownerId);

            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<BookingVM> getListRequestToOwner(int ownerId, int status, int index) {
        try {
            List<BookingVM> listBookingVM = new ArrayList<>();
            String sql = "SELECT "
                    + "br.RequestId, "
                    + "br.CreateAt, "
                    + "br.Status, "
                    + "r.Name, "
                    + "r.Price,"
                    + "r.RoomId, "
                    + "r.Image,"
                    + "h.HomestayId, "
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
                    + "WHERE h.UserId = ? AND br.Status = ? AND br.Status != 3  ORDER BY br.CreateAt DESC OFFSET ? ROWS FETCH NEXT 8 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, ownerId);
            ps.setInt(2, status);
            ps.setInt(3, (index - 1) * 8);

            if (status == 4) {
                sql = "SELECT "
                        + "br.RequestId, "
                        + "br.CreateAt, "
                        + "br.Status, "
                        + "r.Name, "
                        + "r.Price,"
                        + "r.RoomId, "
                        + "r.Image, "
                        + "h.HomestayId, "
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
                        + "WHERE h.UserId = ? AND br.Status != 3 ORDER BY br.CreateAt DESC OFFSET ? ROWS FETCH NEXT 8 ROWS ONLY";
                ps = con.prepareStatement(sql);
                ps.setInt(1, ownerId);
                ps.setInt(2, (index - 1) * 8);

            }
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
                bookingInfo.setHomestayId(rs.getInt("HomestayId"));
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

    public int getListRequestToBookerTotal(int booker, int status) {
        try {
            List<BookingVM> listBookingVM = new ArrayList<>();
            String sql = "SELECT COUNT(*) "
                    + "FROM Booking_Request br "
                    + "JOIN Room r ON br.RoomId = r.RoomId "
                    + "JOIN [User] u ON br.BookBy = u.UserId "
                    + "JOIN [Homestay] h ON r.HomeStayId = h.HomestayId "
                    + "JOIN [User] o ON br.BookBy = o.UserId "
                    + "WHERE br.BookBy = ? AND br.Status = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, booker);
            ps.setInt(2, status);
            if (status == 4) {
                sql = "SELECT COUNT(*) "
                        + "FROM Booking_Request br "
                        + "JOIN Room r ON br.RoomId = r.RoomId "
                        + "JOIN [User] u ON br.BookBy = u.UserId "
                        + "JOIN [Homestay] h ON r.HomeStayId = h.HomestayId "
                        + "JOIN [User] o ON br.BookBy = o.UserId "
                        + "WHERE br.BookBy = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, booker);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<BookingVM> getListRequestToBooker(int booker, int status, int index) {
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
                    + "WHERE br.BookBy = ? AND br.Status = ? ORDER BY br.CreateAt DESC OFFSET ? ROWS FETCH NEXT 8 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, booker);
            ps.setInt(2, status);
            ps.setInt(3, (index - 1) * 8);

            if (status == 4) {
                sql = "SELECT "
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
                        + "WHERE br.BookBy = ? ORDER BY br.CreateAt DESC OFFSET ? ROWS FETCH NEXT 8 ROWS ONLY";
                ps = con.prepareStatement(sql);
                ps.setInt(1, booker);
                ps.setInt(2, (index - 1) * 8);
            }
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

    public boolean RejectOtherBookingRequestWhenConfirmOne(int requestId, int roomId) {
        try {
            List<BookingRequest> listrequestId = new ArrayList<>();
            String sql = "SELECT * FROM [Booking_Request] WHERE RoomId = ? AND RequestId != ? AND Status != 1";
            ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            ps.setInt(2, requestId);

            rs = ps.executeQuery();
            int afftecRow = 0;
            while (rs.next()) {
                BookingRequest bq = new BookingRequest();
                bq.setRequetsId(rs.getInt("RequestId"));
                bq.setBookBy(rs.getInt("BookBy"));
                listrequestId.add(bq);
            };

            for (BookingRequest bookingRequest : listrequestId) {
                sql = "UPDATE [Booking_Request] SET Status = 2 WHERE RequestId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, bookingRequest.getRequetsId());
                afftecRow = ps.executeUpdate();

                RoomDAO roomDAO = new RoomDAO();
                NotificationDAO notiDAO = new NotificationDAO();
                Room room = roomDAO.GetRoomById(roomId);
                String title = "YÊU CẦU THUÊ PHÒNG";
                String contentNoti = "Yêu cầu thuê phòng " + room.getName() + " đã bị từ chối do đã có người khác thuê phòng này trước!";
                notiDAO.createNotification(bookingRequest.getBookBy(), title, contentNoti);
            }
            return afftecRow > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

                // Thuc hien viec tao hop dong cho nay
                createContract(requestId, roomId);

                RejectOtherBookingRequestWhenConfirmOne(requestId, roomId);

                return afftecRow > 0;
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createContract(int requestId, int roomId) {
        try {
            HomestayDAO homestayDAO = new HomestayDAO();
            Room room = GetRoomById(roomId);
            User owner = homestayDAO.GetOnwerByHomestayId(room.getHomestayId());
            User customer = homestayDAO.GetCustomerByRequestId(requestId);

            Contract contract = new Contract();
            contract.setRoomId(roomId);
            contract.setHomestayId(room.getHomestayId());
            // get owner
            contract.setUserId(customer.getId());
            contract.setOwnerId(owner.getId());
            contract.setName("Contract for Room " + room.getName());
            contract.setContent("This is a contract for renting the room " + room.getName());
            contract.setStatus(0); // 0 for pending, 1 for approved, 2 for rejected

            // get customerId
            String sql = "INSERT INTO Contract (Name, OwnerId, UserId, RoomId, HomestayId, Content, CreateAt, Status, StartDate, EndDate) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, contract.getName());
            ps.setInt(2, contract.getOwnerId());
            ps.setInt(3, contract.getUserId());
            ps.setInt(4, contract.getRoomId());
            ps.setInt(5, contract.getHomestayId());
            ps.setString(6, contract.getContent());

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setString(7, currentDate);

            ps.setInt(8, contract.getStatus());
            ps.setString(9, contract.getStartDate());
            ps.setString(10, contract.getEndDate());

            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                System.out.println("Contract created successfully.");
            } else {
                System.out.println("Error creating contract.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creating contract: " + e.getMessage());
        }
    }

    public boolean RejectBooking(int requestId, int roomId) {
        try {
            String sql = "UPDATE [Booking_Request] SET Status = 2 WHERE RequestId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, requestId);
            int afftecRow = ps.executeUpdate();
            if (afftecRow > 0) {
                sql = "UPDATE [Room] SET Status = 1 WHERE RoomId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, roomId);
                return afftecRow > 0;
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean CancleBooking(int requestId) {
        try {
            String sql = "UPDATE [Booking_Request] SET Status = 3 WHERE RequestId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, requestId);
            int afftecRow = ps.executeUpdate();
            return afftecRow > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int GetListRoomByHomeStayIdSearchTotal(int homestayId, String search) {
        try {
            List<Room> listRoom = new ArrayList<>();
            String sql = "SELECT COUNT(*) FROM Room WHERE HomestayId = ? AND Name LIKE ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            ps.setString(2, "%" + search + "%");

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Room> GetListRoomByHomeStayIdSearch(int homestayId, int index, String search) {
        try {
            List<Room> listRoom = new ArrayList<>();
            String sql = "SELECT * FROM Room WHERE HomestayId = ? AND Name LIKE ? ORDER BY RoomId DESC OFFSET ? ROW FETCH NEXT 8 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            ps.setString(2, "%" + search + "%");
            ps.setInt(3, (index - 1) * 8);

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

    public int GetTotalBookedRooms() {
        try {
            String sql = "SELECT COUNT(*) FROM Room WHERE Status = 2";
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

    public int GetTotalAvailableRooms() {
        try {
            String sql = "SELECT COUNT(*) FROM Room WHERE Status = 1";
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

    public int GetListRoomByHomeStayIdFilteredTotal(int homestayId, float minPrice, float maxPrice) {
        try {
            List<Room> listRoom = new ArrayList<>();
            String sql = "SELECT COUNT(*) FROM Room WHERE HomestayId = ? AND Price BETWEEN ? AND ?  AND Status != 0";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            ps.setFloat(2, minPrice);
            ps.setFloat(3, maxPrice);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Room> GetListRoomByHomeStayIdFiltered(int homestayId, float minPrice, float maxPrice, int index) {
        try {
            List<Room> listRoom = new ArrayList<>();
            String sql = "SELECT * FROM Room WHERE HomestayId = ? AND Price BETWEEN ? AND ? AND Status != 0 ORDER BY RoomId DESC OFFSET ? ROW FETCH NEXT 8 ROWS ONLY";
            ps = con.prepareStatement(sql);
            ps.setInt(1, homestayId);
            ps.setFloat(2, minPrice);
            ps.setFloat(3, maxPrice);
            ps.setInt(4, (index - 1) * 8);
            rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setHomestayId(rs.getInt("HomestayId"));
                room.setRoomId(rs.getInt("RoomId"));
                room.setPrice(rs.getFloat("Price"));
                room.setName(rs.getString("Name"));
                room.setStatus(rs.getInt("Status"));
                room.setArea(rs.getInt("Area"));
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

    public void updateRoomStatus(int roomId) {
        try {
            Room h = GetRoomById(roomId);
            if (h != null) {
                int status = h.getStatus();
                if (status == 1) {
                    status = 0;
                } else {
                    status = 1;
                }
                String sql = "UPDATE Room Set Status = ? WHERE RoomId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, status);
                ps.setInt(2, roomId);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRoomStatusToAllowBooking(int roomId) {
        try {
            Room h = GetRoomById(roomId);
            if (h != null) {
                String sql = "UPDATE Room Set Status = ? WHERE RoomId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, 1);
                ps.setInt(2, roomId);
                ps.executeUpdate();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateContractStatus(int status, int roomId, int contractId) {
        try {
            Room h = GetRoomById(roomId);
            if (h != null) {
                String sql = "UPDATE Contract Set Status = ? WHERE RoomId = ? AND ContractId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, status);
                ps.setInt(2, roomId);
                ps.setInt(3, contractId);

                ps.executeUpdate();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Room updateRoom(Room room) {
        try {
            String sql = "UPDATE Room SET Name = ?, Area = ?, Price = ?, MaxParticipant = ? WHERE RoomId = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, room.getName());
            ps.setFloat(2, room.getArea());
            ps.setFloat(3, room.getPrice());
            ps.setInt(4, room.getMaxParticipants());
            ps.setInt(5, room.getRoomId());

            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                Room updateRoom = GetRoomById(room.getRoomId());
                return updateRoom;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BookingRequest getRequetsBookingById(int requestId) {
        try {
            String sql = "SELECT * FROM Booking_Request WHERE RequestId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, requestId);

            rs = ps.executeQuery();
            if (rs.next()) {
                BookingRequest bk = new BookingRequest();
                bk.setBookBy(rs.getInt("BookBy"));
                bk.setRequetsId(rs.getInt("RequestId"));
                bk.setRoomId(rs.getInt("RoomId"));
                bk.setStatus(rs.getInt("Status"));
                bk.setCreateAt(rs.getString("CreateAt"));
                return bk;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        RoomDAO roomDAO = new RoomDAO();
        roomDAO.ConfirmBooking(5, 9);
    }

}
