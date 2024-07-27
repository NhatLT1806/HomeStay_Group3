<%-- 
    Document   : navadmin
    Created on : May 26, 2024, 11:55:38 AM
    Author     : bachq
--%>

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
        <style>
            .nav-item:hover{
                text-decoration: underline;
                /*background-color: orange;*/
            }
        </style>
    </head>
    <body>
        <nav
            id="sidebarMenu"
            class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse"
            >
            <div class="position-sticky pt-3">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">
                            Dashboard
                        </a>
                    </li>
                    
                      <li class="nav-item">
                        <a class="nav-link" href="GetAllUser">
                            User Management
                        </a>
                    </li>
                    
                       <li class="nav-item">
                        <a class="nav-link" href="manage-homestay">
                            Homestay Management
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"
        ></script>
    </body>
</html>



