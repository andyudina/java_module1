package frontend;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SignInServlet extends HttpServlet {
    private AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sessionId = session.toString();
        String login = (String) session.getAttribute("bestGameEverLogin");
        if (login != null && accountService.getSessions(sessionId) != null) {
            response.sendRedirect("/logout");
        } 
        else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("Error", "");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        /*Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("login", email == null ? "" : login);
        pageVariables.put("password", password == null ? "" : password);*/
        HttpSession session = request.getSession();
        String sessionId = session.toString();
        String sessionLogin = (String) session.getAttribute("bestGameEverLogin");
        if (sessionLogin != null && accountService.getSessions(sessionId) != null) {
            response.sendRedirect("/logout");
            return;
        }  

        UserProfile user = accountService.getUser(login);
        if (user != null && password != null && user.getPassword().equals(password)) {
            session.setAttribute("bestGameEverLogin", login);
            accountService.addSessions(session.toString(), user);
            response.sendRedirect("/logout");   
        }
        else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("Error", "There is no such user. Please register before siginig in.");
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
        }
    }
}
