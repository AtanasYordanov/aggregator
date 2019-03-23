package softuni.aggregator.web.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import softuni.aggregator.utils.performance.PerformanceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PerformanceInterceptor extends HandlerInterceptorAdapter {

    private final Map<String, String> requestTimers = new HashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = UUID.randomUUID().toString();
        request.setAttribute("id", requestId);
        StringBuffer requestURL = request.getRequestURL();
        String timerName = String.format("%s:", requestURL);
        requestTimers.put(requestId, timerName);
        PerformanceUtils.startTimer(timerName);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        String id = (String) request.getAttribute("id");
        String timerName = requestTimers.get(id);
        PerformanceUtils.stopTimer(timerName);
    }
}
