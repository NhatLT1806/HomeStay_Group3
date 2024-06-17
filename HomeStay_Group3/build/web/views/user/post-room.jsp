<%@ page import="java.net.URLEncoder" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>HomeStay</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/post-timeshare.css">
        <!-- Google Web Fonts -->
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <form action="room" method="POST" enctype="multipart/form-data">
            <div class="registersform">
                <div class="flexsForm">
                    <input type="hidden" name="action" value="post"/>
                    <h1>Create Room</h1>
                    <label style="color: green">${MESSAGE}</label>     
                    <label style="color: red">${ERROR}</label>

                    <div class="dividerSocial"></div>

                    <div class="textField">
                        <label>Room Name</label><input type="text" name="name" /><span class="error"></span>
                    </div>

                    <div class="textField">
                        <label>Price</label><input name="price" type="number" /><span class="error"></span>
                    </div>
                    <div class="textField">
                        <label>MaxParticipant</label><input name="limit" type="number" /><span class="error"></span>
                    </div>
                    <div class="textField">
                        <label>Services</label >
                        <select name="selectedCategories" multiple="multiple" style="height: 100px">
                                <c:forEach items="${CATEGORIES}" var="category">
                                    <option value="${category.id}">${category.name}</option>
                                </c:forEach>
                            </select>
                        <span class="error"></span>
                    </div>
                    <div class="textField">
                        <label>Homestay</label>
                        <select name="homestayId">
                            <c:forEach items="${HOMESTAYS}" var="home">
                                <option value="${home.homestayId}">${home.name}</option>
                            </c:forEach>
                        </select >
                        <span class="error"></span>
                    </div>
                    <button type="submit">Create</button>

                </div>
                <span class="divider"></span>
                <div class="loginsForm">
                    <h3>Add Image Time Share</h3>
                    <input type="file" accept="image/*" name="image" class="registerButton" />
                </div>
        </form>
    </div>
    <jsp:include page="footer.jsp" />

</body>


</html>