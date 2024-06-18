/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.HomestayDAO;
import DAO.RoomDAO;
import Model.Homestay;
import Model.Room;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Datnt
 */
@MultipartConfig
public class HomestayController extends HttpServlet {

    private static String HOME_PAGE = "views/common/index.jsp";
    private static String HOMESTAY_DETAIL = "views/common/homestay-detail.jsp";

    private static String LOGIN_PAGE = "auth?action=login";
    private static String CREATE_PAGE = "views/user/post-homestay.jsp";
    private static String CUSTOMER_HOMESTAY = "views/user/customer-homestay.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String url = "";
            HttpSession session = request.getSession();
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            boolean isView = true;
            switch (action) {
                case "homestay-detail":
                    homestayDetail(request, response);
                    isView = false;
                    break;
            }
            if (session != null && session.getAttribute("USER") != null) {
                switch (action) {
                    case "post":
                        request.getRequestDispatcher(CREATE_PAGE).forward(request, response);
                        break;
                    case "view-own-homestay":
                        getUserHomeStay(request, response);
                        break;
                }
            } else {
                if (isView) {
                    response.sendRedirect(LOGIN_PAGE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String url = "";
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            switch (action) {
                case "post":
                    PostHomeStay(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    private void PostHomeStay(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User userLogin = (User) session.getAttribute("USER");
            Homestay homestay = new Homestay();
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String desc = request.getParameter("description");
            Part mainImage = request.getPart("image");

            homestay.setAddress(address);
            homestay.setName(name);
            // defeault status = 1 is confirm => 0
            homestay.setStatus(0);
            homestay.setDescription(desc);
            homestay.setUserId(userLogin.getId());

            HomestayDAO homestayDAO = new HomestayDAO();

            boolean result = homestayDAO.PostHomestay(homestay, mainImage);
            if (result) {
                request.setAttribute("MESSAGE", "Tạo homestay thành công ! "
                        + "Vui lòng đợi quản trị viên liên lạc với bạn để xác nhận thông tin,"
                        + " sau đó homestay của bạn sẽ được xuất hiện trên hệ thống của chúng tôi");
            } else {
                request.setAttribute("ERROR", "Tạo homestay không thành công");
            }
            request.getRequestDispatcher(CREATE_PAGE).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserHomeStay(HttpServletRequest request, HttpServletResponse response) {
        try {
            String indexS = request.getParameter("index");
            if (indexS == null) {
                indexS = "1";
            }
            int index = Integer.parseInt(indexS);
            HttpSession session = request.getSession();
            User userLogin = (User) session.getAttribute("USER");
            HomestayDAO homestayDAO = new HomestayDAO();
            List<Homestay> listHomeStay = homestayDAO.GetListHomeStayByUserId(userLogin.getId(), index);
            int total = homestayDAO.GetListHomeStayByUserIdTotal(userLogin.getId());
            int lastPage = total / 8;
            if (total % 8 != 0) {
                lastPage++;
            }
            if (listHomeStay != null) {
                request.setAttribute("HOMESTAY", listHomeStay);
            } else {
                request.setAttribute("MESSAGE", "Bạn chưa có homestay nào trên hệ thống");
            }
            request.setAttribute("endP", lastPage);
            request.setAttribute("selectedPage", index);
            request.getRequestDispatcher(CUSTOMER_HOMESTAY).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void homestayDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            String indexS = request.getParameter("index");
            String homestayIdS = request.getParameter("homestayId");
            if (indexS == null) {
                indexS = "1";
            }
            int index = Integer.parseInt(indexS);
            int homestayId = Integer.parseInt(homestayIdS);
            RoomDAO roomDAO = new RoomDAO();
            HomestayDAO homestayDAO = new HomestayDAO();
            Homestay homestay = homestayDAO.GetHomestayById(homestayId);
            List<Room> listRoom = roomDAO.GetListRoomByHomeStayId(homestayId);
            int total = roomDAO.GetListRoomByHomeStayIdTotal(homestayId);
            int lastPage = total / 8;
            if (total % 8 != 0) {
                lastPage++;
            }
            if (listRoom != null) {
                request.setAttribute("ROOMS", listRoom);
                request.setAttribute("HOMESTAY", homestay);
            } else {
                request.setAttribute("MESSAGE", "Bạn chưa có homestay nào trên hệ thống");
            }
            request.setAttribute("endP", lastPage);
            request.setAttribute("selectedPage", index);
            request.getRequestDispatcher(HOMESTAY_DETAIL).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
