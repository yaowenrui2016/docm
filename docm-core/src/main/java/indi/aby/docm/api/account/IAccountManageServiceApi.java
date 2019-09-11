package indi.aby.docm.api.account;

import indi.rui.common.base.field.IFieldIds;
import indi.rui.common.web.IApi;

public interface IAccountManageServiceApi extends IApi<UserVO> {
    /**
     * 冻结和解冻账号
     * @param fieldIds
     * @param doFreeze 如果为true则冻结，否则解冻
     */
    void freeze(IFieldIds fieldIds, boolean doFreeze);

    /**
     * 用户名唯一校验
     * @param userVO
     * @return 返回true表示用户名已存在，false表示不存在
     */
    Boolean checkUniqueUsername(UserVO userVO);
}
