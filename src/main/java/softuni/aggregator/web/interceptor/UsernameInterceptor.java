package softuni.aggregator.web.interceptor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import softuni.aggregator.domain.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsernameInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String name;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            name =  user.getFirstName();
        } catch (Exception e) {
            name = "Anonymous";
        }
        request.setAttribute("firstName", name);
        return true;
    }
}
