package Controller.Admin;

import DAO.DashboardDAO;
import DAO.RoomDAO;
import DAO.UserManageDAO;
import DAO.UserWalletDAO;
import Model.User;
import Model.UserWalletOrder;
import Model.ViewModel.UserWalletOrderVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("USER") != null) {
                User user = (User) session.getAttribute("USER");

                // Get total number of customers and owners
                UserManageDAO userDAO = new UserManageDAO();
                int totalCustomers = userDAO.GetAllUserTotalByRoleId(2);
                int totalOwners = userDAO.GetAllUserTotalByRoleId(3);

                // Get total number of booked rooms and available rooms
                RoomDAO roomDAO = new RoomDAO();
                int totalBookedRooms = roomDAO.GetTotalBookedRooms();
                int totalAvailableRooms = roomDAO.GetTotalAvailableRooms();

                // Get total wallet balance
                UserWalletDAO walletDAO = new UserWalletDAO();
                UserWalletDAO userWalletDAO = new UserWalletDAO();
                UserManageDAO userManageDAO = new UserManageDAO();

                float totalWalletBalance = walletDAO.getTotalWalletBalance();

                int totalUsers = userManageDAO.GetAllUserTotal();
                // Set the attributes and forward to the dashboard.jsp                 

                LocalDateTime now = LocalDateTime.now();
                int currentMonth = now.getMonthValue();
                int currentYear = now.getYear();

                String months = request.getParameter("month");
                if (months != null) {
                    currentMonth = Integer.parseInt(months);
                }

                float monthlyWalletRevenueCurentMonth = 0.0f;
                List<UserWalletOrder> monthlyWalletOrders = userWalletDAO.getMonthlyWalletOrders(currentMonth, currentYear);
                for (int i = 1; i <= 12; i++) {
                    float revenueMoneth = 0.0f;
                    List<UserWalletOrder> monthlyWalletOrdersMonth = userWalletDAO.getMonthlyWalletOrders(i, currentYear);
                    for (UserWalletOrder order : monthlyWalletOrdersMonth) {
                        System.out.println("Month" + i + " " + order.getAmmount());
                        if (order.getAmmount() == 0) {
                            revenueMoneth += 0;
                        } else {
                            revenueMoneth += order.getAmmount();
                        }
                        if (i == currentMonth) {
                            monthlyWalletRevenueCurentMonth += order.getAmmount();
                        }
                    }
                    request.setAttribute("REVENUE_MOUNTH_" + i, revenueMoneth);
                }

                DashboardDAO dashboardDAO = new DashboardDAO();
                Map<YearMonth, Integer> monthlyConfirmedRequest = dashboardDAO.getMonthlyConfirmedRequest();
                Map<YearMonth, Integer> monthlySendRequest = dashboardDAO.getMonthlyCreateBookingRequest();
                request.setAttribute("MONTHLY_CONFIRMED_POSTS", monthlyConfirmedRequest);
                request.setAttribute("MONTHLY_SAVED_POSTS", monthlySendRequest);

                List<UserWalletOrderVM> orders = walletDAO.getWalletOrdersPending();
                request.setAttribute("MONTHLY_WALLET_REVENUE", monthlyWalletRevenueCurentMonth);
                request.setAttribute("WALLET_ORDERS", monthlyWalletOrders);
                request.setAttribute("TOTAL_USERS", totalUsers);
                request.setAttribute("TOTAL_WALLET_BALANCE", totalWalletBalance);
                request.setAttribute("currentMonth", currentMonth);
                request.setAttribute("TOTAL_CUSTOMERS", totalCustomers);
                request.setAttribute("TOTAL_OWNERS", totalOwners);
                request.setAttribute("TOTAL_BOOKED_ROOMS", totalBookedRooms);
                request.setAttribute("TOTAL_AVAILABLE_ROOMS", totalAvailableRooms);
                request.setAttribute("TOTAL_WALLET_BALANCE", totalWalletBalance);

                request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
            } else {
                response.sendRedirect("auth?action=login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("auth?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private Map<String, Float> getMonthlyRevenue(UserWalletDAO walletDAO) {
        Map<String, Float> monthlyRevenue = new HashMap<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (int i = 0; i < 12; i++) {
            String month = currentDate.minusMonths(i).format(formatter);
            float revenue = walletDAO.getTotalRevenueForMonth(month);
            monthlyRevenue.put(month, revenue);
        }

        return monthlyRevenue;
    }
}
