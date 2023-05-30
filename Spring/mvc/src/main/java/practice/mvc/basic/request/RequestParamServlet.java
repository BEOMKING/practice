package practice.mvc.basic.request;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RequestParamServlet", value = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("전체 파라미터");
        req.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + " " + req.getParameter(paramName)));
        System.out.println("단일");
        String username = req.getParameter("username");
        System.out.println(username);
        int age = Integer.parseInt(req.getParameter("age"));
        System.out.println(age);
        System.out.println("속성?");
        req.getAttributeNames().asIterator()
                .forEachRemaining(attributeName -> System.out.println(attributeName + " " + req.getAttribute(attributeName)));
    }
}
