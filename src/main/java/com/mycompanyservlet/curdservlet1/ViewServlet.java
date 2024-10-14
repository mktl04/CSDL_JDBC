/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompanyservlet.curdservlet1;

import common.DatabaseUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ViewServlet", urlPatterns = {"/ViewServlet"})
public class ViewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //B1. Lấy giá trị tham số từ client
            //String uname = request.getParameter("uname");
            //String upass = request.getParameter("upass");
            //String email = request.getParameter("email");
            //String country = request.getParameter("country");
            //B2. Xử lí yêu cầu
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            String data = " ";
            try {
                conn = DatabaseUtil.getConnection();
                ps = conn.prepareStatement("select * from users");
                rs = ps.executeQuery();
                data += "<table border=1s>";
                data += "<tr><th>Id</th><th>Name</th><th>Password</th><th>Email</th><th>Country</th><th>Edit</th><th>Delete</th></tr>";
                while (rs.next()){
                    data += "<tr>";
                    data += "<td>" + rs.getInt(1) + "</td>\n";
                    data += "<td>" + rs.getString(2) + "</td>\n";
                    data += "<td>" + rs.getString(3) + "</td>\n";
                    data += "<td>" + rs.getString(4) + "</td>\n";
                    data += "<td>" + rs.getString(5) + "</td>\n";
                    data += "<td><a href=EditServlet?id=" + rs.getInt(1)+ ">Edit</a></td>\n";
                    data += "<td><a href=DeleteServlet?id=" + rs.getInt(1)+ " onclick= \"return confirm ('Are you sure to delete?')\">Delete</a></td>\n";
                }
                data += "</tr>";
                conn.close();
            }catch (Exception e){
                System.out.println("Loi: " + e.toString());
                out.println("<h2>Thêm user thất bại</h2>");
            }
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<a href=index.html>Add new user</a>");
            out.println("<h1>User list</h1>");
            out.println(data);
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
