package indi.aby.docm.core.auth;

import indi.aby.docm.core.account.UserVO;

import javax.servlet.http.HttpServletResponse;

public interface IAuthServiceApi {
    UserSummaryVO login(UserVO vo, HttpServletResponse servletResponse);
    UserSummaryVO parse(String token, HttpServletResponse servletResponse);
}
