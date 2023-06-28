package com.example.mvc2.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ModelAndView resolveException(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {
        log.error("", ex);

        try {
            if (ex instanceof UserException) {
                log.info("UserException resolver to 400");
                final String acceptHeader = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                if (acceptHeader.equals("application/json")) {
                    final Map<String, Object> errorResult = Map.of(
                            "ex", ex.getClass(),
                            "message", ex.getMessage()
                    );

                    final String result = objectMapper.writeValueAsString(errorResult);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);

                    return new ModelAndView();
                } else {
                    log.info("error page to 500");
                    return new ModelAndView("error/500");
                }
            }
        } catch (final Exception e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
