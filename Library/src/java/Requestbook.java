/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ironhe_ad
 */
public class Requestbook extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Requestbook</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
                        
            out.println("</head>");
            out.println("<body>");
            
            HttpSession session=request.getSession(false);  
            String rname =  (String)session.getAttribute("globaluname");
            String s = (String)session.getAttribute("globalid");
            String status = (String)session.getAttribute("globalstatus");
            int rid = Integer.parseInt(s);
            
            try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
                    
                    java.util.Date date=new java.util.Date();
                    java.sql.Date sqlDate=new java.sql.Date(date.getTime());
                    
                    PreparedStatement pst = con.prepareStatement("INSERT INTO requested (Person_ID, Person_Name, Book_Name, Author_Name, Book_Edition, Date) VALUES (?,?,?,?,?,?)");
                    
                    String click = request.getParameter("btn1");
                    if(click.equals("Request")){
                        
                        String rebname = request.getParameter("abname");
                        String reaname = request.getParameter("aaname");
                        String rebe = request.getParameter("abe");
                        
                        pst.setInt(1, rid);
                        pst.setString(2, rname);
                        pst.setString(3, rebname);
                        pst.setString(4, reaname);
                        pst.setString(5, rebe);
                        pst.setDate(6, sqlDate);
                        
                        int i = pst.executeUpdate();
                        
                        if(i==0){
                            out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                                    out.println("_____________________________________________________________________________________Error______________________________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Requestbookview.html");
                            rd.include(request, response);
                        }
                        else if(i!=0){
                            out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                                    out.println("___________________________________________________________________________________Succesfull____________________________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Requestbookview.html");
                            rd.include(request, response);
                        }
                    }else if(click.equals("Home")){
                        if(status.equals("student")){
                                RequestDispatcher rd = request.getRequestDispatcher("Home");
                                rd.forward(request, response);
                            }
                            else if(status.equals("admin")){
                                RequestDispatcher rd = request.getRequestDispatcher("Adminhome");
                                rd.forward(request, response);
                            }
                    }
            }catch(Exception e){
                out.println(e);
            }
            
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
