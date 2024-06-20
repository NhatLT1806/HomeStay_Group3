<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Timeshare</title>
        <link
            href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
            rel="stylesheet"
            />
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
            />
    </head>
    <body>
        <div class="flex">
            <div class="w-2/12 bg-gray-800 text-white min-h-screen p-4">
                <div class="mb-4">
                    <h1 class="text-2xl font-bold flex items-center p-4">
                        <i class="fas fa-building mr-2"></i>Timeshare
                    </h1>
                </div>
                <nav class="space-y-2">
                    <a
                        href="#"
                        class="block py-2 px-4 rounded text-sm text-white flex items-center bg-blue-600"
                        >
                        <i class="fas fa-clipboard mr-2"></i>Homestay Management
                    </a>
                    <a
                        href="GetAllUser"
                        class="block py-2 px-4 rounded text-sm flex items-center hover:bg-gray-700"
                        >
                        <i class="fas fa-user mr-2"></i>User Management
                    </a>
                    <a
                        href="${pageContext.request.contextPath}/auth?action=logout"
                        class="block py-2 px-4 rounded text-sm hover:bg-gray-700 flex items-center"
                        >
                        <i class="fas fa-sign-out-alt mr-2"></i>Logout
                    </a>
                </nav>
            </div>
            <div class="w-9/12 p-8">
                <div class="flex justify-between mb-4">
                    <h2 class="text-2xl font-bold">Timeshare Management</h2>
                    <div class="relative">
                        <input
                            type="text"
                            class="border border-gray-300 rounded pl-8 pr-4 py-2"
                            placeholder="Search with title..."
                            />
                        <i
                            class="fas fa-search absolute top-1/2 transform -translate-y-1/2 left-2 text-gray-400"
                            ></i>
                    </div>
                </div>
                <div class="grid grid-cols-4 gap-4">
                    <!-- Card 1 -->
                    <c:forEach items="${HOMESTAYS}" var="homestay">
                        <div class="bg-white rounded-lg shadow-md overflow-hidden">
                            <img
                                src="data:image/png;base64,${homestay.image}"
                                alt="Image"
                                class="w-full"
                                />
                            <div class="p-4">
                                <h3 class="text-xl font-bold">${homestay.name}</h3>
                                <p class="text-gray-600">
                                   ${homestay.description}
                                </p>
                            </div>
                            <div class="flex justify-between p-4">
                                <a href="manage-homestay?action=accept&id=${homestay.homestayId}" class="text-green-500">
                                    <i class="fas fa-check-circle text-2xl"></i>
                                </a>
                                <a  href="manage-homestay?action=reject&id=${homestay.homestayId}" class="text-red-500">
                                    <i class="fas fa-times-circle text-2xl"></i>
                                </a>
                                <a class="text-blue-500">
                                    <i class="fas fa-info-circle text-2xl"></i>
                                </a>
                            </div>
                        </div>
                    </c:forEach>

                </div>
                <div class="mt-4 flex justify-end">
                    <nav
                        class="relative z-0 inline-flex shadow-sm -space-x-px"
                        aria-label="Pagination"
                        >
                        <a
                            href="#"
                            class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
                            >
                            <span class="sr-only">Previous</span>
                            <
                        </a>
                        <a
                            href="#"
                            class="z-10 bg-indigo-50 border-indigo-500 text-indigo-600 relative inline-flex items-center px-4 py-2 border text-sm font-medium"
                            >1</a
                        >
                        <a
                            href="#"
                            class="bg-white border-gray-300 text-gray-500 hover:bg-gray-50 relative inline-flex items-center px-4 py-2 border text-sm font-medium"
                            >2</a
                        >
                        <a
                            href="#"
                            class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
                            >
                            <span class="sr-only">Next</span>
                            >
                        </a>
                    </nav>
                </div>
            </div>
        </div>
    </body>
</html>
