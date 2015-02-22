package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.PasswordHash;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.jdbc.UserDAOImpl;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Anton on 2015.02.22..
 */
public class LoginController implements MVCController {
    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession(true);
        session.setAttribute("page_name", "Login");


        if((Integer)session.getAttribute("access_level") > AccessLevel.GUEST.getValue())
            if (request.getServletPath().equals("/logout")) {
                //session.invalidate();

                //session = request.getSession(true);
                //session.setAttribute("access_level", AccessLevel.GUEST.getValue());
                return new MVCModel("/logout.jsp");
            }
            else return new MVCModel("/access.jsp", "Only guests can access this page.");

        if (request.getMethod().equals("POST")) {
            String error = null;
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            System.out.println("Login method: " + username + " " + password);

            if(username.isEmpty() || password.isEmpty())
                return new MVCModel("/login.jsp", "Login and password fields can't be empty.");

            UserDAO userDAO = new UserDAOImpl();
            User user = null;
            try {
                user = userDAO.getByLogin(username);
            } catch (DBException e) {
                //TODO: exception handling
                e.printStackTrace();
            }

            try {
                if (user == null)
                    error = "Incorrect login or/and password.";
                else if (!PasswordHash.validatePassword(password, user.getPassword()))
                    error = "Incorrect login or/and password.";
                else {
                    System.out.println("Login Successful!");

                    session.setAttribute("username", username);
                    session.setAttribute("name", user.getName());
                    session.setAttribute("surname", user.getSurname());
                    session.setAttribute("access_level", user.getAccessLevel());
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            return new MVCModel("/login.jsp", error);
        }

        return new MVCModel("/login.jsp", null);
    }
}