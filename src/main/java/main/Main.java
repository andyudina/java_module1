package main;

import frontend.SignInServlet;
import frontend.LogOutServlet;
import frontend.SignUpServlet;
import admin.AdminServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();

        SignInServlet signInServlet = new SignInServlet(accountService);
        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        LogOutServlet logOutServlet = new LogOutServlet(accountService);
        AdminServlet adminServlet = new AdminServlet(accountService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signInServlet), "/authform");
        context.addServlet(new ServletHolder(logOutServlet), "/logout");
        context.addServlet(new ServletHolder(signUpServlet), "/register");
        context.addServlet(new ServletHolder(adminServlet), "/admin");     

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();
    }
}
