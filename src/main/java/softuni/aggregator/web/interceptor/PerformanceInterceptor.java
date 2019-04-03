package softuni.aggregator.web.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import softuni.aggregator.utils.performance.PerformanceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class PerformanceInterceptor extends HandlerInterceptorAdapter {

    private static final String REQUEST_ID_ATTRIBUTE = "requestId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getAttribute(REQUEST_ID_ATTRIBUTE) == null) {
            String requestId = UUID.randomUUID().toString();
            request.setAttribute(REQUEST_ID_ATTRIBUTE, requestId);
            StringBuffer requestURL = request.getRequestURL();
            String timerName = String.format("%s:", requestURL);
            PerformanceUtils.startTimer(requestId, timerName);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        String requestId = (String) request.getAttribute(REQUEST_ID_ATTRIBUTE);
        PerformanceUtils.stopTimer(requestId);
    }
}
