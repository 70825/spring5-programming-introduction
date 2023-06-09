package spring5fs.book.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final HttpSession session = request.getSession(false);
        if (session != null) {
            final Object authInfo = session.getAttribute("authInfo");
            if (authInfo != null) {
                return true;
            }
        }
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}
