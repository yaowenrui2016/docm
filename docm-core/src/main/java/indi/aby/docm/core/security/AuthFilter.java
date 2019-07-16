package indi.aby.docm.core.security;

import indi.aby.docm.api.IAuthServiceApi;
import indi.aby.docm.api.dto.UserSummaryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter implements Ordered {
    /**
     * 开放的url
     */
    @Value("${docm.auth.freeUrl:}")
    private String[] freeUrls;

    @Autowired
    private IAuthServiceApi authServiceApi;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        if (shouldAuth(uri) && !"OPTIONS".equals(method)) {
            String token = request.getHeader("X-AUTH-TOKEN");
            try {
                UserSummaryVO vo = authServiceApi.parse(token, response);
                log.debug("XXX Request token is valid [{}]", vo.getUsername());
                request.setAttribute("currentUser", vo);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    protected boolean shouldAuth(String url) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (freeUrls != null && freeUrls.length > 0) {
            for (String freeUrl : freeUrls) {
                if (antPathMatcher.match(freeUrl, url)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}