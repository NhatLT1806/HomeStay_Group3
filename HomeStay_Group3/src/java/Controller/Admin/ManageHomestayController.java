/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import DAO.HomestayDAO;
import DAO.NotificationDAO;
import Model.Homestay;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ManageHomestayController extends HttpServlet {
    
    private static String HOMESTAY_MANAGE_PAGE = "/views/admin/manage-homestay.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String url = "";
            HttpSession session = request.getSession(false);
            // Get session ra neu khong co session account tuc la chua login thi response ve trang login.
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            if (session != null && session.getAttribute("USER") != null) {
                User user = (User) session.getAttribute("USER");
                switch (action) {
                    case "": {
                        homeStayManagement(request, response);
                        break;
                    }
                    case "accept": {
                        accpetHomestay(request, response);
                        break;
                    }
                    case "reject": {
                        rejectHomettay(request, response);
                        break;
                    }
                }
            } else {
                url = "views/common/sign-in.jsp";
                request.getRequestDispatcher(url).forward(request, response);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void homeStayManagement(HttpServletRequest request, HttpServletResponse response) {
        try {
            HomestayDAO homestayDAO = new HomestayDAO();
            List<Homestay> listhomestay = homestayDAO.getHomeStayWaitToConfirm(1);
            if (listhomestay != null) {
                request.setAttribute("HOMESTAYS", listhomestay);
            } else {
                request.setAttribute("MESSAGE", "Không có homestay nào trong trạng thái chờ duyệt");
            }
            request.getRequestDispatcher(HOMESTAY_MANAGE_PAGE).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void accpetHomestay(HttpServletRequest request, HttpServletResponse response) {
        try {
            String ids = request.getParameter("id");
            String ownerIds = request.getParameter("userId");
            int id = Integer.parseInt(ids);
            int ownerId = Integer.parseInt(ownerIds);
            
            HomestayDAO homestayDAO = new HomestayDAO();
            boolean result = homestayDAO.AcceptHomeStay(id, ownerId);
            if (result) {
                Homestay _homestay = homestayDAO.GetHomestayById(id);
                NotificationDAO notiDAO = new NotificationDAO();
                String title = "THÊM NHÀ";
                String _content = "Yêu cầu thêm nhà " + _homestay.getName() + " của bạn đã được  phê duyệt!";
                notiDAO.createNotification(ownerId, title, _content);
                request.setAttribute("MESSAGE", "Đã cho phép homestay xuất hiện ở homepage");
                
            } else {
                request.setAttribute("ERROR", "Có lỗi xảy ra");
            }
            homeStayManagement(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void rejectHomettay(HttpServletRequest request, HttpServletResponse response) {
        try {
            String ids = request.getParameter("id");
            int id = Integer.parseInt(ids);
            HomestayDAO homestayDAO = new HomestayDAO();
            boolean result = homestayDAO.RejectHomeStay(id);
            if (result) {
                request.setAttribute("MESSAGE", "Đã từ chối homestay được xuất hiện ở homepage");
                Homestay _homestay = homestayDAO.GetHomestayById(id);
                NotificationDAO notiDAO = new NotificationDAO();
                String title = "THÊM NHÀ";
                String _content = "Yêu cầu thêm nhà " + _homestay.getName() + " của bạn đã được  phê duyệt!";
                notiDAO.createNotification(_homestay.getUserId(), title, _content);
            } else {
                request.setAttribute("ERROR", "Có lỗi xảy ra");
            }
            homeStayManagement(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
