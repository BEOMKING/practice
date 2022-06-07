package practice.mvc.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
//        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setHeader("Content-Type", "text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("bjp-header", "hi");

        Cookie cookie = new Cookie("BJP", "허접");
        cookie.setMaxAge(400);
        Cookie[] cookies = req.getCookies();
        System.out.println(cookies[0].getValue());
        resp.addCookie(cookie);
        resp.sendRedirect("/basic/hello-form.html");

        PrintWriter writer = resp.getWriter();
        writer.println("해위");
    }
}
