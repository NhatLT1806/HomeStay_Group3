/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CategoryDAO;
import DAO.HomestayDAO;
import DAO.RoomDAO;
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
                    case "post":
                        HomestayDAO homestayDAO = new HomestayDAO();
                        CategoryDAO cateDAO = new CategoryDAO();

                        List<Homestay> listHome = homestayDAO.GetListHomeStayByUserId_All(user.getId());
                        List<Category> listCate = cateDAO.GetAllCategory();
                        request.setAttribute("HOMESTAYS", listHome);
                        request.setAttribute("CATEGORIES", listCate);

                        request.getRequestDispatcher(CREATE_ROOM_PAGE).forward(request, response);
                        break;

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
            String limits = request.getParameter("limit");
            String homestayIds = request.getParameter("homestayId");
            Part mainImage = request.getPart("image");

            float price = Float.parseFloat(priceS);
            int litmit = Integer.parseInt(limits);
            int homestayId = Integer.parseInt(homestayIds);
            room.setMaxParticipants(litmit);
            room.setName(name);
            room.setStatus(1);
            room.setPrice(price);
            room.setHomestayId(homestayId);
            RoomDAO roomDAO = new RoomDAO();

            boolean result = roomDAO.PostRoomByHomeStay(room, mainImage, categories);
            if (result) {
                request.setAttribute("MESSAGE", "Tạo room thành công ! Bạn có thể xem lại vào chi tiết của homestay");
            } else {
                request.setAttribute("ERROR", "Tạo homestay không thành công");
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
                    request.setAttribute("HOMESTAY", homestay);
                    request.setAttribute("SERVICES", listServices);
                    request.setAttribute("ROOM", room);
                }
                request.getRequestDispatcher(ROOM_PAGE).forward(request, response);
            }
        } catch (Exception e) {
        }
    }

}
