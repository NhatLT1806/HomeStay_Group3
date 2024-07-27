<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            .card {
                border-radius: 10px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body>
        <jsp:include page="headeradmin.jsp"/>

        
        
        <div class="container-fluid">
            <div class="row">
                <jsp:include page="navadmin.jsp"/>
                <div class="col-md-9 ms-sm-auto col-lg-10 px-md-4">

                    <h1>Dashboard</h1>
                    <div class="row mt-4">
                        <div class="col-md-4">
                            <div class="card bg-primary text-white p-4">
                                <h5 class="card-title">Total Customers</h5>
                                <p class="card-text display-4">${TOTAL_CUSTOMERS}</p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card bg-success text-white p-4">
                                <h5 class="card-title">Total Owners</h5>
                                <p class="card-text display-4">${TOTAL_OWNERS}</p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card bg-info text-white p-4">
                                <h5 class="card-title">Total Booked Rooms</h5>
                                <p class="card-text display-4">${TOTAL_BOOKED_ROOMS}</p>
                            </div>
                        </div>
                    </div>

                    <div class="row mt-4">
                        <div class="col-md-4">
                            <div class="card bg-warning text-white p-4">
                                <h5 class="card-title">Total Available Rooms</h5>
                                <p class="card-text display-4">${TOTAL_AVAILABLE_ROOMS}</p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card bg-warning text-white p-4">
                                <i class="fas fa-wallet card-icon"></i>
                                <div>
                                    <h5 class="card-title">Total Wallet Balance</h5>
                                    <p class="card-text display-4"><fmt:formatNumber value="${TOTAL_WALLET_BALANCE}" type="number" pattern="#,###"/>VND</p>
                                </div>
                            </div>
                        </div>

                        <div class="col-12 col-md-6 col-lg-4 mb-4">
                            <div class="card card-bg-primary ">
                                <div class="card-body d-flex align-items-center">
                                    <i class="fas fa-chart-line card-icon"></i>
                                    <div>
                                        <h5 class="card-title">Monthly Wallet Revenue</h5>
                                        <p class="card-text display-4"><fmt:formatNumber value="${MONTHLY_WALLET_REVENUE}" type="number" pattern="#,###"/>VND</p>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div style="display: flex">              
                        <canvas id="monthly-confirmed-posts" style="width:100%;max-width:700px"></canvas>          
                        <canvas id="monthly-saved-posts" style="width:100%;max-width:700px"></canvas>        
                    </div>
                    <div style="display: flex">
                        <canvas id="wallet-revenue" style="width:100%;max-width:700px"></canvas>
                    </div>  
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Monthly Wallet Orders</h5>
                                    <c:forEach var="i" begin="1" end="12">
                                        <a href="DashboardController?month=${i}" class="btn ${i == currentMonth ? 'btn-primary' : 'btn-secondary'}">${i}</a>
                                    </c:forEach>
                                </div>
                                <div class="card-body">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>User</th>
                                                <th>Amount</th>
                                                <th>Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${WALLET_ORDERS}" var="order">
                                                <tr>
                                                    <td>${order.id}</td>
                                                    <td>${order.userWalletId}</td>
                                                    <td>${order.ammount} VND</td>
                                                    <td>${order.createAt}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <script
                    src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
                </script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
                <script type="text/javascript">
                        var chart = document.getElementById('wallet-revenue').getContext('2d');
                        var myChart = new Chart(chart, {
                            type: 'line',
                            data: {
                                labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
                                datasets: [{
                                        label: 'Revenue',
                                        data: [${REVENUE_MOUNTH_1}, ${REVENUE_MOUNTH_2}, ${REVENUE_MOUNTH_3}, ${REVENUE_MOUNTH_4}, ${REVENUE_MOUNTH_5}, ${REVENUE_MOUNTH_6}, ${REVENUE_MOUNTH_7}, ${REVENUE_MOUNTH_8}, ${REVENUE_MOUNTH_9}, ${REVENUE_MOUNTH_10}, ${REVENUE_MOUNTH_11}, ${REVENUE_MOUNTH_12}, ],
                                        backgroundColor: 'rgba(0, 156, 255, .5)'
                                    }]
                            },
                            options: {
                                responsive: true
                            }
                        });
                        
                         const labels = [];
                const data = [];
                                        <c:forEach var="entry" items="${MONTHLY_CONFIRMED_POSTS}">
                labels.push('${entry.key}');
                data.push(${entry.value});
                                        </c:forEach>

                // Create the chart
                const ctx = document.getElementById('monthly-confirmed-posts').getContext('2d');
                const postChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: labels,
                        datasets: [{
                                label: 'Số phòng thuê thành công',
                                data: data,
                                backgroundColor: 'rgba(75, 192, 192, 0.8)',
                                borderColor: 'rgba(75, 192, 192, 1)',
                                borderWidth: 1
                            }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        },
                        responsive: true
                    }
                });

                const labels2 = [];
                const data2 = [];
                                        <c:forEach var="entry" items="${MONTHLY_SAVED_POSTS}">
                labels2.push('${entry.key}');
                data2.push(${entry.value});
                                        </c:forEach>

                // Create the chart
                const ctx2 = document.getElementById('monthly-saved-posts').getContext('2d');
                const postSavedChart = new Chart(ctx2, {
                    type: 'bar',
                    data: {
                        labels: labels2,
                        datasets: [{
                                label: 'Số yêu cầu đang chờ được duyệt',
                                data: data2,
                                backgroundColor: 'rgb(255, 99, 71, 0.8)',
                                borderColor: 'rgba(75, 192, 192, 1)',
                                borderWidth: 1
                            }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        },
                        responsive: true
                    }
                });


                        
                        
                </script>
                
            
            
            
            </body>
                </html>
