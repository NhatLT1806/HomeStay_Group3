<%-- 
    Document   : notify
    Created on : Jul 17, 2024, 6:16:24 PM
    Author     : Datnt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/MaterialDesign-Webfont/5.3.45/css/materialdesignicons.css" integrity="sha256-NAxhqDvtY0l4xn+YVa6WjAcmd94NNfttjNsDmNatFVc=" crossorigin="anonymous" />

        <style>
            body{
                margin-top:20px;
                background-color: #f0f2f5;
            }
            .dropdown-list-image {
                position: relative;
                height: 2.5rem;
                width: 2.5rem;
            }
            .dropdown-list-image img {
                height: 2.5rem;
                width: 2.5rem;
            }
            .btn-light {
                color: #2cdd9b;
                background-color: #e5f7f0;
                border-color: #d8f7eb;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-lg-3 left">
                    <div class="box shadow-sm mb-3 rounded bg-white ads-box text-center">
                        <c:choose>
                            <c:when test="${USER.avatar != null}">
                                <img src="data:image/png;base64,${USER.avatar}" class="img-fluid" style="width: 100%"  />
                            </c:when>
                            <c:otherwise>
                                <img  src="https://placehold.co/100x100" class="img-fluid" style="width: 100%" />
                            </c:otherwise>
                        </c:choose>

                        <div class="p-3 border-bottom">
                            <h6 class="font-weight-bold text-dark">${USER.email}</h6>
                            <p class="mb-0 text-muted">You’re all caught up! Check back later for new notifications</p>
                        </div>
                        <div class="p-3">
                            <a class="btn btn-success" href="profile?action=view">View profile</a>
                        </div>
                        <div class="p-3">
                            <a class="btn btn-success" href="auth">Back home</a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-9 right">
                    <div class="box shadow-sm rounded bg-white mb-3" style="min-height: 500px">
                        <div class="box-title border-bottom p-3">
                            <h6 class="m-0">Recent</h6>
                        </div>
                        <div class="box-body p-0">
                            <c:forEach items="${LIST_NOTIFY}" var="noti">
                                <div class="p-3 d-flex align-items-center bg-light border-bottom osahan-post-header" style="justify-content: space-between">
                                    <div class="font-weight-bold mr-3">
                                        <div class="text-truncate">${noti.title}</div>
                                        <div class="small">${noti.content}</div>
                                    </div>
                                    <span class="ml-auto mb-auto">
                                        <div class="btn-group">
                                            <a href="NotificationController?action=delete&notifyId=${noti.id}" class="btn btn-danger"><i class="mdi mdi-delete"></i> Delete</a>
                                        </div>
                                        <c:if test="${noti.isRead == false}">
                                            <a href="NotificationController?action=markIsRead&notifyId=${noti.id}" class="btn btn-warning" style="margin-left: 15px">Read</a>
                                        </c:if>
                                        <br />
                                        <div class="text-right text-muted pt-1">${noti.createAt}</div>
                                    </span>
                                </div>
                            </c:forEach>
                        </div>

                    </div>
                    <nav aria-label="Page navigation example" style="display: flex; justify-content:center;">
                        <ul class="pagination">
                            <c:choose>
                                <c:when test ="${selectedPage - 1 < 1}">
                                    <li class="page-item disabled">
                                        <a class="page-link" href="#">«</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link" href="NotificationController?action=view&index=${selectedPage-1}">«</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach var="i" begin="1" end="${endP}">
                                <li class="page-item ${i == selectedPage ? "active" : "" }"> <a class="page-link" href="NotificationController?action=view&search=${search}&index=${i}">${i}</a> <li>
                                </c:forEach>
                                <c:choose>
                                    <c:when test ="${selectedPage >= endP}">
                                    <li class="page-item disabled">
                                        <a class="page-link" href="#">»</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link" href="NotificationController?action=view&index=${selectedPage+1}">»</a></li>
                                    </c:otherwise>
                                </c:choose>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </body>
</html>
