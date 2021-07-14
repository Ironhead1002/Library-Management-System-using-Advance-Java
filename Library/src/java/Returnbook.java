/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
public class Returnbook extends HttpServlet {

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
            out.println("<title>Returnbook</title>");            
            out.println("</head>");
            out.println("<body>");
            
            try{
                    out.println("<form action=\"Returnbook\" method=\"post\">");
                    Class.forName("com.mysql.jdbc.Driver");
                    
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
                    
                    Statement st1 = con.createStatement();
                    PreparedStatement pst = con.prepareStatement("UPDATE books SET Quantity = Quantity + 1 WHERE Book_ID = ?");
                    PreparedStatement pst1 = con.prepareStatement("UPDATE issued SET Status = ? WHERE Issue_ID = ?");
                    
                    String click = request.getParameter("btn1");
                    String bookstatus = "";
                    int bookid=0,i=0,j=0;
                    
                    if(click.equals("Return")){
                        int issueid = Integer.parseInt(request.getParameter("isid"));
                        ResultSet rs = st1.executeQuery("select * from issued");
                        
                        while(rs.next())
                        {
                            if(rs.getInt(1)==issueid){
                               bookid = rs.getInt(4);
                               bookstatus=rs.getString(8);
                               break;
                            }
                        }
                        if(bookstatus.equals("Issued")){
                            pst.setInt(1,bookid);
                            pst1.setString(1, "returned");
                            pst1.setInt(2, issueid);
                            
                            i = pst.executeUpdate();
                            j = pst1.executeUpdate();
                        }
                        
                        
                        if(i==0){
                           out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                                    out.println("_____________________________________________________________________________________Error______________________________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Returnbookview.html");
                            rd.include(request, response);
                        }
                        else{
                            out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                                    out.println("___________________________________________________________________________________Succesfull____________________________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Returnbookview.html");
                            rd.include(request, response);
                        }
                    } 
                    else if(click.equals("Home")){
                        RequestDispatcher rd = request.getRequestDispatcher("Adminhome");
                        rd.forward(request, response);
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
