<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Verify OTP</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f6f6f6;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .verify-container {
            background-color: #fff;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }

        .verify-container h2 {
            font-size: 24px;
            margin-bottom: 20px;
        }

        .verify-container label {
            display: block;
            text-align: left;
            margin-bottom: 5px;
        }

        .verify-container input[type="text"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .verify-container input[type="submit"] {
            width: 100%;
            padding: 10px;
            border: none;
            background-color: #007bff;
            color: #fff;
            font-weight: bold;
            cursor: pointer;
            border-radius: 5px;
        }

        .verify-container input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .verify-container .message {
            margin-top: 10px;
        }

        .message.success {
            color: green;
        }

        .message.error {
            color: red;
        }
    </style>
</head>
<body>
    <div class="verify-container">
        <h2>Verify OTP</h2>
        <%-- Hiển thị thông báo thành công hoặc thất bại nếu có --%>
        <% String successMessage = (String) request.getAttribute("successMessage"); %>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>

        <% if (successMessage != null) { %>
            <p class="message success"><%= successMessage %></p>
        <% } else if (errorMessage != null) { %>
            <p class="message error"><%= errorMessage %></p>
        <% } %>
        <a href="auth">Trở về trang đăng nhập</a>
    </div>
</body>
</html>
