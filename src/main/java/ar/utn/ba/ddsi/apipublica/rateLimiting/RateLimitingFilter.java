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

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_MS = 60_000;

    private final Map<String, RequestCounter> requestsPorIp = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String ip = obtenerIp(request);
        long now = System.currentTimeMillis();

        RequestCounter counter = requestsPorIp.get(ip);

        if (counter == null || now - counter.windowStart > WINDOW_MS) {
            requestsPorIp.put(ip, new RequestCounter(now));
        } else {
            counter.count++;
            if (counter.count > MAX_REQUESTS) {
                response.setStatus(429);
                response.getWriter().write("Rate limit excedido");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String obtenerIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        return ip != null ? ip.split(",")[0] : request.getRemoteAddr();
    }

    private static class RequestCounter {
        int count;
        long windowStart;

        RequestCounter(long now) {
            this.count = 1;
            this.windowStart = now;
        }
    }
}
