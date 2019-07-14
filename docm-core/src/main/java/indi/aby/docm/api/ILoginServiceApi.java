package indi.aby.docm.api;

import indi.aby.docm.api.dto.UserSummaryVO;
import indi.aby.docm.api.dto.UserVO;

import javax.servlet.http.HttpServletResponse;

public interface ILoginServiceApi {
    UserSummaryVO login(UserVO vo, HttpServletResponse servletResponse);
}
