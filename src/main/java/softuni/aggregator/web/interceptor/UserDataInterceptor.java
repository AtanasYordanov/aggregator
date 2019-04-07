package softuni.aggregator.web.interceptor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.vo.LoggedUserDataVO;
import softuni.aggregator.utils.performance.CustomStringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserDataInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            LoggedUserDataVO loggedUserDataVO = new LoggedUserDataVO();
            loggedUserDataVO.setFirstName(user.getFirstName());
            loggedUserDataVO.setRole(CustomStringUtils.getUserRole(user));
            modelAndView.addObject("loggedUser", loggedUserDataVO);
        } catch (Exception ignore) { }
    }
}
