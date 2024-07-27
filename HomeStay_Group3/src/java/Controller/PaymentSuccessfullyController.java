/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.NotificationDAO;
import DAO.UserWalletDAO;
import Model.User;
import Model.UserWallet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "PaymentSuccessfullyController", urlPatterns = {"/PaymentSuccessfullyController"})
public class PaymentSuccessfullyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            String ammount = request.getParameter("vnp_Amount");
            UserWalletDAO userWalletDAO = new UserWalletDAO();
            UserWallet userWallet = userWalletDAO.getUserWalletByUserId(user.getId());
            if (userWallet != null) {
                float ammountPrice = Float.parseFloat(ammount) / 100;
                boolean result = userWalletDAO.addWalletOrder(userWallet.getId(), user.getId(), ammountPrice);
                if (result) {
                    String content = "Gửi yêu cầu thêm  " + ammountPrice + " vào ví tiền thành công";
                    userWalletDAO.addTransitionHistory(content, userWallet.getId());

                    NotificationDAO notiDAO = new NotificationDAO();
                    String title = "NẠP TIỀN VÀO VÍ";
                    String _content = "Yêu cầu nạp tiền của bạn đã được gửi, vui lòng đợi admin phê duyệt!";
                    notiDAO.createNotification(user.getId(), title, _content);

                    request.setAttribute("MESSAGE", "Send request successfully please wait admin approve");
                }
            }
            request.getRequestDispatcher("profile?action=wallet-history").forward(request, response);
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

}
