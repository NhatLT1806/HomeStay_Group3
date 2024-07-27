/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.HomestayDAO;
import DAO.NotificationDAO;
import DAO.RoomDAO;
import Model.BookingRequest;
import Model.Homestay;
import Model.Room;
import Model.User;
import Model.ViewModel.BookingVM;
import Model.ViewModel.RoomServiceVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class BookingController extends HttpServlet {

    private static String LOGIN_PAGE = "auth?action=login";
    private static String CREATE_PAGE = "views/user/post-homestay.jsp";
    private static String ROOM_PAGE = "views/common/room-detail.jsp";
    private static String CREATE_ROOM_PAGE = "views/user/post-room.jsp";
    private static String CUSTOMER_HOMESTAY = "views/user/customer-homestay.jsp";
    private static String MANAGE_BOOKING = "views/user/manage-booking.jsp";
    private static String HISTORY_BOOKING = "views/user/booking-history.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String url = "";
            HttpSession session = request.getSession();
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            if (session != null && session.getAttribute("USER") != null) {
                User user = (User) session.getAttribute("USER");
                switch (action) {
                    case "book":
                        BookRoom(request, response);
                        break;
                    case "view-booking-history":
                        ViewBookingHistory(request, response);
                        break;
                    case "view-room-booking":
                        ViewBookingList(request, response);
                        break;
                    case "confirm-booking":
                        AcceptBooking(request, response);
                        break;
                    case "reject-booking":
                        RejectBooking(request, response);
                        break;
                    case "cancle-booking":
                        CancleBooking(request, response);
                        break;
                }
            } else {
                response.sendRedirect(LOGIN_PAGE);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void BookRoom(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            String roomIds = request.getParameter("roomId");
            if (roomIds != null) {
                int roomId = Integer.parseInt(roomIds);
                RoomDAO roomDAO = new RoomDAO();
                boolean result = roomDAO.BookingRoom(roomId, user.getId());
                if (result) {

                    NotificationDAO notiDAO = new NotificationDAO();
                    // tạo noti cho book room

                    Room room = roomDAO.GetRoomById(roomId);
                    String title = "YÊU CẦU THUÊ PHÒNG";
                    String contentNoti = "Yêu cầu thuê phòng " + room.getName() + " của bạn đã được gửi đi vui lòng chờ được duyệt!";
                    notiDAO.createNotification(user.getId(), title, contentNoti);

                    HomestayDAO homestayDAO = new HomestayDAO();
                    int ownerId = homestayDAO.getUserIdByHomeStayId(room.getHomestayId());
                    title = "YÊU CẦU THUÊ PHÒNG";
                    contentNoti = room.getName() + " đang có yêu cầu thuê phòng từ " + user.getFirstName() +" "+ user.getLastName();
                    notiDAO.createNotification(ownerId, title, contentNoti);

                    // gửi yêu cầu cho chủ homestay luôn
                    request.setAttribute("MESSAGE", "Bạn đã đặt phòng roomId - " + roomIds + "thành công ! Vui lòng đợi owner chấp nhận yêu cầu của bạn");
                } else {
                    request.setAttribute("ERROR", "Bạn đã đặt phòng roomId - " + roomIds + " không thành công ! Hãy thử lại sau");
                }
                Room room = roomDAO.GetRoomById(roomId);
                List<RoomServiceVM> listServices = roomDAO.GetRoomService(roomId);
                if (room != null) {
                    HomestayDAO homestayDAO = new HomestayDAO();
                    Homestay homestay = homestayDAO.GetHomestayById(room.getHomestayId());
                    User onwer = homestayDAO.GetOnwerByHomestayId(room.getHomestayId());
                    request.setAttribute("OWNER", onwer);
                    request.setAttribute("HOMESTAY", homestay);
                    request.setAttribute("SERVICES", listServices);
                    request.setAttribute("ROOM", room);
                }
                request.getRequestDispatcher(ROOM_PAGE).forward(request, response);

            }
        } catch (Exception e) {
        }
    }

    private void ViewBookingList(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            String statusS = request.getParameter("status");
            String indexS = request.getParameter("index");
            if (indexS == null) {
                indexS = "1";
            }
            if (statusS == null || statusS == "") {
                // neu truong hop k truyen gi hoac vao mac dinh se lay het request.
                statusS = "4";
            }
            int index = Integer.parseInt(indexS);
            int status = Integer.parseInt(statusS);
            RoomDAO roomDAO = new RoomDAO();
            int total = roomDAO.getListRequestToOwnerTotal(user.getId(), status);
            List<BookingVM> listBooking = roomDAO.getListRequestToOwner(user.getId(), status, index);
            if (listBooking != null) {
                request.setAttribute("BOOKINGS", listBooking);
            } else {
                System.out.println("Khong co yeu cau booking");
            }
            int lastPage = total / 8;
            if (total % 8 != 0) {
                lastPage++;
            }
            request.setAttribute("endP", lastPage);
            request.setAttribute("selectedPage", index);
            request.setAttribute("status", status);
            request.getRequestDispatcher(MANAGE_BOOKING).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ViewBookingHistory(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            RoomDAO roomDAO = new RoomDAO();
            User user = (User) session.getAttribute("USER");
            String statusS = request.getParameter("status");
            String indexS = request.getParameter("index");
            if (indexS == null) {
                indexS = "1";
            }
            if (statusS == null || statusS == "") {
                // neu truong hop k truyen gi hoac vao mac dinh se lay het request.
                statusS = "4";
            }
            int index = Integer.parseInt(indexS);
            int status = Integer.parseInt(statusS);

            int total = roomDAO.getListRequestToBookerTotal(user.getId(), status);
            List<BookingVM> listBooking = roomDAO.getListRequestToBooker(user.getId(), status, index);

            if (listBooking != null) {
                request.setAttribute("BOOKINGS", listBooking);
            } else {
                System.out.println("Khong co yeu cau booking");
            }

            int lastPage = total / 8;
            if (total % 8 != 0) {
                lastPage++;
            }
            request.setAttribute("endP", lastPage);
            request.setAttribute("selectedPage", index);
            request.setAttribute("status", status);
            request.getRequestDispatcher(HISTORY_BOOKING).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AcceptBooking(HttpServletRequest request, HttpServletResponse response) {
        try {
            String requestIds = request.getParameter("requestId");
            String roomIdS = request.getParameter("roomId");

            int requestId = Integer.parseInt(requestIds);
            int roomId = Integer.parseInt(roomIdS);

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            RoomDAO roomDAO = new RoomDAO();

            boolean result = roomDAO.ConfirmBooking(requestId, roomId);
            if (result) {

                // get booking userId Booking request
                BookingRequest brq = roomDAO.getRequetsBookingById(requestId);
                // taoj noti khi duoc thue phong
                NotificationDAO notiDAO = new NotificationDAO();
                Room room = roomDAO.GetRoomById(roomId);
                String title = "YÊU CẦU THUÊ PHÒNG";
                String contentNoti = "Yêu cầu thuê phòng " + room.getName() + " của bạn đã được duyệt!";
                notiDAO.createNotification(brq.getBookBy(), title, contentNoti);

                request.setAttribute("MESSAGE", "Phòng id - " + roomIdS + " đã được duyệt thuê thành công");
            } else {
                System.out.println("EROR at accept Booking");
            }
            request.getRequestDispatcher("book?action=view-room-booking").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RejectBooking(HttpServletRequest request, HttpServletResponse response) {
        try {
            String requestIds = request.getParameter("requestId");
            String roomIdS = request.getParameter("roomId");

            int requestId = Integer.parseInt(requestIds);
            int roomId = Integer.parseInt(roomIdS);

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            RoomDAO roomDAO = new RoomDAO();
            boolean result = roomDAO.RejectBooking(requestId, roomId);
            if (result) {

                // get booking userId Booking request
                BookingRequest brq = roomDAO.getRequetsBookingById(requestId);
                // taoj noti khi duoc thue phong
                NotificationDAO notiDAO = new NotificationDAO();
                Room room = roomDAO.GetRoomById(roomId);
                String title = "YÊU CẦU THUÊ PHÒNG";
                String contentNoti = "Yêu cầu thuê phòng " + room.getName() + " của bạn đã bị từ chối!";
                notiDAO.createNotification(brq.getBookBy(), title, contentNoti);
                request.setAttribute("MESSAGE", "Cập nhật trạng thái phòng thành công");
                
            } else {
                System.out.println("EROR at accept Booking");
            }
            request.getRequestDispatcher("book?action=view-room-booking").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CancleBooking(HttpServletRequest request, HttpServletResponse response) {
        try {
            String requestIds = request.getParameter("requestId");
            int requestId = Integer.parseInt(requestIds);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            RoomDAO roomDAO = new RoomDAO();
            boolean result = roomDAO.CancleBooking(requestId);
            if (result) {
                request.setAttribute("MESSAGE", "Cập nhật trạng thái phòng thành công");
            } else {
                System.out.println("EROR at accept Booking");
            }
            request.getRequestDispatcher("book?action=view-booking-history").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
