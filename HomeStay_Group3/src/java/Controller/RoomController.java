/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CategoryDAO;
import DAO.HomestayDAO;
import DAO.RoomDAO;
import DAO.RoomFavouriteDAO;
import Model.Category;
import Model.Homestay;
import Model.Room;
import Model.User;
import Model.ViewModel.RoomServiceVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MultipartConfig
public class RoomController extends HttpServlet {

    private static String HOME_PAGE = "views/common/index.jsp";

    private static String LOGIN_PAGE = "auth?action=login";
    private static String CREATE_PAGE = "views/user/post-homestay.jsp";
    private static String FAVOURITE_ROOM_PAGE = "views/user/favourite-room.jsp";

    private static String ROOM_PAGE = "views/common/room-detail.jsp";
    private static String CREATE_ROOM_PAGE = "views/user/post-room.jsp";
    private static String CUSTOMER_HOMESTAY = "views/user/customer-homestay.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String url = "";
            HttpSession session = request.getSession();
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            switch (action) {
                case "room-detail":
                    RoomDetail(request, response);
                    break;
            }
            if (session != null && session.getAttribute("USER") != null) {
                User user = (User) session.getAttribute("USER");
                switch (action) {
                    case "post": {
                        HomestayDAO homestayDAO = new HomestayDAO();
                        CategoryDAO cateDAO = new CategoryDAO();

                        List<Homestay> listHome = homestayDAO.GetListHomeStayByUserId_All(user.getId());
                        List<Category> listCate = cateDAO.GetAllCategory();
                        request.setAttribute("HOMESTAYS", listHome);
                        request.setAttribute("CATEGORIES", listCate);

                        request.getRequestDispatcher(CREATE_ROOM_PAGE).forward(request, response);
                        break;
                    }
                    case "view-favourite-room": {
                        ViewFavouriteRoom(request, response);
                        break;
                    }
                    case "add-to-favourite": {
                        AddToFavourite(request, response);
                        break;
                    }
                    case "remove-from-favourite": {
                        RemoveFromFavourite(request, response);
                        break;
                    }
                    // aanr va hien
                    case "change-status-room": {
                        changeStatusRoom(request, response);
                        break;
                    }
                    // cho phep nguoi dung thue lai
                    case "update-room-status": {
                        allowForBookingAgain(request, response);
                        break;
                    }
                    // cho phep nguoi dung thue lai
                    case "update-contract-status": {
                        updateConstractStatus(request, response);
                        break;
                    }
                }
            } else {
                response.sendRedirect(LOGIN_PAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            switch (action) {
                case "post":
                    PostRoom(request, response);
                    break;
                case "update":
                    UpdateRoom(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PostRoom(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User userLogin = (User) session.getAttribute("USER");
            String[] selectedCategories = request.getParameterValues("selectedCategories");
            List<String> categories = selectedCategories != null ? Arrays.asList(selectedCategories) : new ArrayList<>();
            Room room = new Room();
            String name = request.getParameter("name");
            String priceS = request.getParameter("price");
            String areaS = request.getParameter("area");
            String limits = request.getParameter("limit");
            String homestayIds = request.getParameter("homestayId");
            Part mainImage = request.getPart("image");

            float price = Float.parseFloat(priceS);
            float area = Float.parseFloat(areaS);
            int litmit = Integer.parseInt(limits);

            boolean isValidate = true;
            if (price < 0 || price > 10000000) {
                isValidate = false;
                request.setAttribute("ERROR", "Giá tiền phòng chỉ có thể nằm trong khoản  0 < x < 10.000.000 vnđ");
            } else if (area < 0 || area > 100) {
                isValidate = false;
                request.setAttribute("ERROR", "Diện tích phòng chỉ có thể nằm trong khoản  0 < x < 100m2");
            } else if (litmit < 0 || litmit > 100) {
                isValidate = false;
                request.setAttribute("ERROR", "Chỉ cho phép ở tối đa 5 người trên 1 pòng");
            }

            HomestayDAO homestayDAO = new HomestayDAO();
            CategoryDAO cateDAO = new CategoryDAO();

            List<Homestay> listHome = homestayDAO.GetListHomeStayByUserId_All(userLogin.getId());
            List<Category> listCate = cateDAO.GetAllCategory();
            request.setAttribute("HOMESTAYS", listHome);
            request.setAttribute("CATEGORIES", listCate);

            if (isValidate) {
                int homestayId = Integer.parseInt(homestayIds);
                room.setMaxParticipants(litmit);
                room.setName(name);
                room.setStatus(1);
                room.setPrice(price);
                room.setArea(area);
                room.setHomestayId(homestayId);
                RoomDAO roomDAO = new RoomDAO();

                boolean result = roomDAO.PostRoomByHomeStay(room, mainImage, categories);
                if (result) {
                    request.setAttribute("MESSAGE", "Tạo room thành công ! Bạn có thể xem lại vào chi tiết của homestay");

                } else {
                    request.setAttribute("ERROR", "Tạo homestay không thành công");
                }
            }
            request.getRequestDispatcher(CREATE_PAGE).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RoomDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            String roomIdS = request.getParameter("roomId");
            if (roomIdS != null) {
                int roomId = Integer.parseInt(roomIdS);
                RoomDAO roomDAO = new RoomDAO();
                HomestayDAO homestayDAO = new HomestayDAO();

                Room room = roomDAO.GetRoomById(roomId);
                List<RoomServiceVM> listServices = roomDAO.GetRoomService(roomId);
                if (room != null) {
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

    private void AddToFavourite(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            String roomIdS = request.getParameter("roomId");
            int roomId = Integer.parseInt(roomIdS);
            HomestayDAO homestayDAO = new HomestayDAO();
            int homestayId = homestayDAO.getHomeStayByRoomId(roomId);
            RoomFavouriteDAO roomFavouriteDAO = new RoomFavouriteDAO();
            boolean result = roomFavouriteDAO.addRoomToFavourite(user.getId(), roomId);
            if (result) {
                request.setAttribute("MESSAGE", "Thêm phòng thành công vào danh sách yêu thích");
            } else {
                request.setAttribute("ERROR", "Bạn đã có phòng này trong danh sách yêu thích");
            }
            request.getRequestDispatcher("homestay?action=homestay-detail&homestayId=" + homestayId).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RemoveFromFavourite(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            String roomIdS = request.getParameter("roomId");
            int roomId = Integer.parseInt(roomIdS);
            RoomFavouriteDAO roomFavouriteDAO = new RoomFavouriteDAO();
            boolean result = roomFavouriteDAO.removeRoomFromFavourite(user.getId(), roomId);
            if (result) {
                request.setAttribute("MESSAGE", "Room removed from favourite list successfully.");
            } else {
                request.setAttribute("ERROR", "Failed to remove room from favourite list.");
            }
            request.getRequestDispatcher("room?action=view-favourite-room").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ViewFavouriteRoom(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            String indexS = request.getParameter("index");
            if (indexS == null) {
                indexS = "1";
            }

            int index = Integer.parseInt(indexS);
            User user = (User) session.getAttribute("USER");
            RoomFavouriteDAO roomFavouriteDAO = new RoomFavouriteDAO();
            List<Room> favouriteRooms = roomFavouriteDAO.getFavouriteRoomsByUserId(user.getId(), index);
            int total = roomFavouriteDAO.getFavouriteRoomsByUserIdTotal(index);
            int lastPage = total / 8;
            if (total % 8 != 0) {
                lastPage++;
            }
            request.setAttribute("endP", lastPage);
            request.setAttribute("selectedPage", index);
            request.setAttribute("ROOMS", favouriteRooms);
            request.getRequestDispatcher(FAVOURITE_ROOM_PAGE).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeStatusRoom(HttpServletRequest request, HttpServletResponse response) {
        try {
            String roomId = request.getParameter("roomId");
            String homestayId = request.getParameter("homestayId");

            if (roomId != null) {
                RoomDAO roomDAO = new RoomDAO();
                roomDAO.updateRoomStatus(Integer.parseInt(roomId));
                String url = "homestay?action=view-detail-homestay&homestayId=" + homestayId;
                response.sendRedirect(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void UpdateRoom(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Room room = new Room();
            String name = request.getParameter("name");
            String priceS = request.getParameter("price");
            String areaS = request.getParameter("area");
            String limits = request.getParameter("limit");
            String roomIds = request.getParameter("roomId");
            float price = Float.parseFloat(priceS);
            float area = Float.parseFloat(areaS);

            int litmit = Integer.parseInt(limits);
            int roomId = Integer.parseInt(roomIds);
            room.setMaxParticipants(litmit);

            HomestayDAO homestayDAO = new HomestayDAO();
            int homestayId = homestayDAO.getHomeStayByRoomId(roomId);
            boolean isValidate = true;
            if (price < 0 || price > 10000000) {
                isValidate = false;
                session.setAttribute("ERROR", "Giá tiềnrequest phòng chỉ có thể nằm trong khoản  0 < x < 10.000.000 vnđ");
            } else if (area < 0 || area > 100) {
                isValidate = false;
                session.setAttribute("ERROR", "Diện tích phòng chỉ có thể nằm trong khoản  0 < x < 100m2");
            } else if (litmit < 0 || litmit > 100) {
                isValidate = false;
                session.setAttribute("ERROR", "Chỉ cho phép ở tối đa 5 người trên 1 pòng");
            }
            if (isValidate) {
                room.setRoomId(roomId);
                room.setName(name);
                room.setPrice(price);
                room.setArea(area);

                RoomDAO roomDAO = new RoomDAO();
                Room roomUpdated = roomDAO.updateRoom(room);
                if (roomUpdated != null) {
                    String url = "homestay?action=view-detail-homestay&homestayId=" + roomUpdated.getHomestayId();
                    response.sendRedirect(url);
                }
            } else {
                String url = "homestay?action=view-detail-homestay&homestayId=" + homestayId;
                response.sendRedirect(url);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void allowForBookingAgain(HttpServletRequest request, HttpServletResponse response) {
        // get ra truoc sau do xoa di hop dong cu.
        try {
            String roomId = request.getParameter("roomId");
            String homestayId = request.getParameter("homestayId");

            if (roomId != null) {
                RoomDAO roomDAO = new RoomDAO();
                roomDAO.updateRoomStatusToAllowBooking(Integer.parseInt(roomId));
                String url = "homestay?action=view-detail-homestay&homestayId=" + homestayId;
                response.sendRedirect(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateConstractStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            String roomId = request.getParameter("roomId");
            String homestayId = request.getParameter("homestayId");
            String contractId = request.getParameter("contractId");
            String type = request.getParameter("type");

            if (roomId != null) {
                RoomDAO roomDAO = new RoomDAO();

                if (type.equals("approve")) {
                    roomDAO.updateContractStatus(1, Integer.parseInt(roomId), Integer.parseInt(contractId));
                } else {
                    roomDAO.updateContractStatus(2, Integer.parseInt(roomId), Integer.parseInt(contractId));
                }
                roomDAO.updateRoomStatusToAllowBooking(Integer.parseInt(roomId));
                String url = "homestay?action=view-detail-homestay&homestayId=" + homestayId;
                response.sendRedirect(url);
            }
        } catch (Exception e) {
        }
    }
}
