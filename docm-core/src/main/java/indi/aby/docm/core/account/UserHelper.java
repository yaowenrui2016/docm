package indi.aby.docm.core.account;

import indi.aby.docm.core.auth.UserSummaryVO;
import indi.aby.docm.util.ThreadLocalUtil;

import javax.servlet.http.HttpServletRequest;

public class UserHelper {
    public static final String CURRENT_USER = "threadVar.currentUser";

    public static void setCurrentUser(UserSummaryVO vo) {
        setCurrentUser(null, vo);
    }

    public static void setCurrentUser(HttpServletRequest request, UserSummaryVO vo) {
        if (vo == null) {
            return;
        }
        if (request != null) {
            request.setAttribute(CURRENT_USER, vo);
        }
        ThreadLocalUtil.put(CURRENT_USER, vo);
    }

    public static UserSummaryVO getCurrentUser(HttpServletRequest request) {
        Object curUser = request.getAttribute(CURRENT_USER);
        if (curUser != null) {
            return (UserSummaryVO) curUser;
        }
        return null;
    }

    public static UserSummaryVO getCurrentUser() {
        Object curUser = ThreadLocalUtil.get(CURRENT_USER);
        if (curUser != null) {
            return (UserSummaryVO) curUser;
        }
        return null;
    }

    public static void removeCurrentUser() {
        ThreadLocalUtil.remove(CURRENT_USER);
    }
}
