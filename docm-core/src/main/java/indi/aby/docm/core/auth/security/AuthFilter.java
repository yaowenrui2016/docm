package indi.aby.docm.core.auth.security;

import indi.aby.docm.api.IAuthServiceApi;
import indi.aby.docm.api.auth.UserSummaryVO;
import indi.aby.docm.api.user.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthFilter extends OncePerRequestFilter implements Ordered {
    /**
     * 开放的url
     */
    private String[] freeUrls;

    private IAuthServiceApi authServiceApi;

    public AuthFilter(String[] freeUrls, IAuthServiceApi authServiceApi) {
        this.freeUrls = freeUrls;
        this.authServiceApi = authServiceApi;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        if (shouldAuth(uri) && !"OPTIONS".equals(method)) {
            String token = request.getHeader("X-AUTH-TOKEN");
            try {
                UserSummaryVO vo = authServiceApi.parse(token, response);
                log.debug("Parse x-auth-token of '{}' success", vo.getUsername());
                UserHelper.setCurrentUser(request, vo);
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