package indi.aby.docm.core.auth;

import static indi.aby.docm.core.auth.Constant.X_AUTH_TOKEN;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import indi.aby.docm.api.account.UserHelper;
import indi.aby.docm.api.auth.IAuthServiceApi;
import indi.aby.docm.api.auth.UserSummaryVO;
import indi.rui.common.base.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        if (shouldAuth(uri) && !"OPTIONS".equals(method)) {
            try {
                String token = Optional.ofNullable(request.getParameter("xAuthToken"))
                    .orElseGet(() -> Optional.ofNullable(request.getHeader(X_AUTH_TOKEN)).orElseGet(() -> {
                        Cookie[] cookies = request.getCookies();
                        if (cookies != null) {
                            return Arrays.stream(cookies).filter(cookie -> X_AUTH_TOKEN.equals(cookie.getName()))
                                .map(cookie -> cookie.getValue()).collect(Collectors.toList()).get(0);
                        } else {
                            return null;
                        }
                    }));
                if (!StringUtil.isEmpty(token)) {
                    UserSummaryVO vo = authServiceApi.parse(token, response);
                    log.debug("Parse x-auth-token of '{}' success", vo.getUsername());
                    UserHelper.setCurrentUser(request, vo); // 保存当前用户到线程变量
                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            } finally {
                UserHelper.removeCurrentUser(); // 移除线程变量的当前用户
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