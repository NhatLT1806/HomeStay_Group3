/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.NotificationDAO;
import Model.UserNotification;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "NotificationController", urlPatterns = {"/NotificationController"})
public class NotificationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            switch (action) {
                case "view":
                    viewNotify(request, response);
                    break;
                case "markIsRead":
                    markIsRead(request, response);
                    break;
                case "delete":
                    deleteNotify(request, response);
                    break;
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

    private void viewNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User userLogedIn = (User) session.getAttribute("USER");
            String url = "views/user/notify.jsp";
            if (userLogedIn != null) {
                String indexS = request.getParameter("index");
                if (indexS == null) {
                    indexS = "1";
                }
                int index = Integer.parseInt(indexS);
                NotificationDAO notiDAO = new NotificationDAO();
                int total = notiDAO.getTotalNotificationByUserId(userLogedIn.getId());
                List<UserNotification> listNoti = notiDAO.getAllNotifyByUserId(userLogedIn.getId(), index);

                int lastPage = total / 9;
                if (total % 9 != 0) {
                    lastPage++;
                }
                session.setAttribute("LIST_NOTIFY", listNoti);
                request.setAttribute("endP", lastPage);
                request.setAttribute("selectedPage", index);

            } else {
                url = "auth?action=login";
            }

            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void markIsRead(HttpServletRequest request, HttpServletResponse response) {
        try {
            String notifyIds = request.getParameter("notifyId");
            if (notifyIds != null) {
                int notifyId = Integer.parseInt(notifyIds);
                NotificationDAO notiDAO = new NotificationDAO();
                notiDAO.markIsReadNotify(notifyId);
            }
            request.getRequestDispatcher("NotificationController?action=view").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String notifyIds = request.getParameter("notifyId");
            if (notifyIds != null) {
                int notifyId = Integer.parseInt(notifyIds);
                NotificationDAO notiDAO = new NotificationDAO();
                notiDAO.deleteNotification(notifyId);
            }
            request.getRequestDispatcher("NotificationController?action=view").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
