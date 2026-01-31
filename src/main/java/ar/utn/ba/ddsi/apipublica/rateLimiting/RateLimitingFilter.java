package ar.utn.ba.ddsi.apipublica.rateLimiting;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS = 60;
    private static final long WINDOW_MS = 60_000;

    private final Map<String, RequestCounter> counters = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        long now = System.currentTimeMillis();

        RequestCounter counter = counters.computeIfAbsent(ip, k -> new RequestCounter(now));

        synchronized (counter) {
            if (now - counter.windowStart > WINDOW_MS) {
                counter.windowStart = now;
                counter.count = 0;
            }

            counter.count++;

            if (counter.count > MAX_REQUESTS) {
                response.setStatus(429);
                response.getWriter().write("Too many requests");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private static class RequestCounter {
        long windowStart;
        int count;

        RequestCounter(long windowStart) {
            this.windowStart = windowStart;
            this.count = 0;
        }
    }
}
