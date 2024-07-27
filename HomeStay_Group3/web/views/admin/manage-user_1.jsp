<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>User</title>
    <link
      href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
     <style>
        .modal {
            transition: opacity 0.25s ease;
        }
        body.modal-active {
            overflow-x: hidden;
            overflow-y: visible !important;
        }
    </style>
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
            href="./timeshare-management.html"
            class="block py-2 px-4 rounded text-sm text-white flex items-center hover:bg-gray-700"
          >
            <i class="fas fa-clipboard mr-2"></i>Timeshare Management
          </a>
          <a
            href="./user-management.html"
            class="block py-2 px-4 rounded text-sm flex items-center bg-blue-600"
          >
            <i class="fas fa-user mr-2"></i>User Management
          </a>
          <a
            href="#"
            class="block py-2 px-4 rounded text-sm hover:bg-gray-700 flex items-center"
          >
            <i class="fas fa-sign-out-alt mr-2"></i>Logout
          </a>
        </nav>
      </div>
      <div class="w-9/12 p-8">
        <div class="flex justify-between mb-4">
          <h2 class="text-2xl font-bold">User Management</h2>
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
       <div class="container mx-auto">
        <div class="mb-4 flex justify-end">
            <button class="bg-blue-500 text-white px-4 py-2 rounded" id="createUserButton">Create User</button>
        </div>
        <div class="bg-white shadow rounded-lg">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                    <tr>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">User Name</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email Address</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Phone Number</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Action</th>
                    </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                    <tr>
                        <td class="px-6 py-4 whitespace-nowrap text-blue-600 hover:underline">booking</td>
                        <td class="px-6 py-4 whitespace-nowrap">booking@gmail.com</td>
                        <td class="px-6 py-4 whitespace-nowrap">0123456789</td>
                        <td class="px-6 py-4 whitespace-nowrap"><span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">ACTIVE</span></td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <a href="#" class="text-indigo-600 hover:text-indigo-900" id="editUserButton">Edit</a>
                            <a href="#" class="text-red-600 hover:text-red-900 ml-4">Delete</a>
                        </td>
                    </tr>
                    <!-- Repeat the above <tr> block for each user -->
                </tbody>
            </table>
        </div>
        <div class="mt-4 flex justify-end">
            <nav class="relative z-0 inline-flex shadow-sm -space-x-px" aria-label="Pagination">
                <a href="#" class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                    <span class="sr-only">Previous</span>
                   <
                </a>
                <a href="#" class="z-10 bg-indigo-50 border-indigo-500 text-indigo-600 relative inline-flex items-center px-4 py-2 border text-sm font-medium">1</a>
                <a href="#" class="bg-white border-gray-300 text-gray-500 hover:bg-gray-50 relative inline-flex items-center px-4 py-2 border text-sm font-medium">2</a>
                <a href="#" class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                    <span class="sr-only">Next</span>
                    <!-- Heroicon name: chevron-right -->
                    &gt;
                </a>
            </nav>
        </div>
      </div>
    </div>

    <!-- //modal  -->
    <div class="fixed z-10 inset-0 overflow-y-auto hidden" id="createUserModal">
        <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div class="fixed inset-0 transition-opacity" aria-hidden="true">
                <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
            </div>
            <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">&#8203;</span>
            <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                    <div class="">
                        <div class="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                            <h3 class="text-lg leading-6 font-medium text-gray-900" id="modal-title">
                                Create User
                            </h3>
                            <div class="mt-2">
                                <form id="createUserForm">
                                    <div class="mb-4">
                                        <label class="block text-gray-700 text-sm font-bold mb-2" for="username">User Name</label>
                                        <input type="text" id="username" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" placeholder="User Name">
                                    </div>
                                    <div class="mb-4">
                                        <label class="block text-gray-700 text-sm font-bold mb-2" for="name">Name</label>
                                        <input type="text" id="name" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" placeholder="Name">
                                    </div>
                                    <div class="mb-4">
                                        <label class="block text-gray-700 text-sm font-bold mb-2" for="status">Status</label>
                                        <select id="status" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                                            <option value="ACTIVE">Active</option>
                                            <option value="INACTIVE">Inactive</option>
                                            <option value="BANNED">Banned</option>
                                        </select>
                                    </div>
                                    <div class="mb-4">
                                        <label class="block text-gray-700 text-sm font-bold mb-2" for="email">Email</label>
                                        <input type="email" id="email" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" placeholder="Email">
                                    </div>
                                    <div class="mb-4">
                                        <label class="block text-gray-700 text-sm font-bold mb-2" for="phone">Phone Number</label>
                                        <input type="text" id="phone" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" placeholder="Phone Number">
                                    </div>
                                    <div class="mb-4">
                                        <label class="block text-gray-700 text-sm font-bold mb-2" for="password">Password</label>
                                        <input type="password" id="password" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" placeholder="Password">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                    <button type="button" class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-blue-600 text-base font-medium text-white hover:bg-blue-700 focus:outline-none sm:ml-3 sm:w-auto sm:text-sm" id="saveButton">OK</button>
                    <button type="button" class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none sm:mt-0 sm:w-auto sm:text-sm" id="cancelButton">Cancel</button>
                </div>
            </div>
        </div>
    </div>
    <script>
        // Get modal element
        var modal = document.getElementById('createUserModal');
        var createUserButton = document.getElementById('createUserButton');
        var editUserButton = document.getElementById('editUserButton');
        var cancelButton = document.getElementById('cancelButton');

        // Event listener to open modal
        createUserButton.addEventListener('click', function() {
            modal.classList.remove('hidden');
            document.body.classList.add('modal-active');
        });

        editUserButton.addEventListener('click', function() {
            modal.classList.remove('hidden');
            document.body.classList.add('modal-active');
        });

        // Event listener to close modal
        cancelButton.addEventListener('click', function() {
            modal.classList.add('hidden');
            document.body.classList.remove('modal-active');
        });
    </script>
  </body>
</html>
