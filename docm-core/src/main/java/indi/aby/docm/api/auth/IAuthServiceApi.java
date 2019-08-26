package indi.aby.docm.api.auth;

import indi.aby.docm.api.auth.UserSummaryVO;
import indi.aby.docm.api.account.UserVO;

import javax.servlet.http.HttpServletResponse;

public interface IAuthServiceApi {
    UserSummaryVO login(UserVO vo, HttpServletResponse servletResponse);
    UserSummaryVO parse(String token, HttpServletResponse servletResponse);
}
