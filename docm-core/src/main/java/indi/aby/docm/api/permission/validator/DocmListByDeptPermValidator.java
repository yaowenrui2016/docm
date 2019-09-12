package indi.aby.docm.api.permission.validator;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import indi.aby.docm.api.account.DeptVO;
import indi.aby.docm.api.auth.UserSummaryVO;
import indi.aby.docm.api.contract.DocmVO;
import indi.rui.common.base.dto.AbstractVO;
import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.util.StringUtil;

/**
 * @author: yaowr
 * @create: 2019-09-12
 */
@Component("docmListByDept")
public class DocmListByDeptPermValidator extends AbstractPermValidator implements PermissionValidator {
    @Override
    public boolean validate(String permission, UserSummaryVO userInfo, Object param) {
        Objects.requireNonNull(param);
        String deptId = extractDeptId(userInfo);
        /* 拥有‘仅本科室查询列表权限’且拥有‘科室’则进行查询列表限制
         * 否则视为 无权限 */
        if (hasPermission(permission, userInfo) && !StringUtil.isEmpty(deptId)) {
            if (param instanceof QueryRequest) {
                AbstractVO vo = ((QueryRequest)param).getConditions();
                if (vo instanceof DocmVO) {
                    DeptVO dept = new DeptVO();
                    dept.setId(deptId);
                    ((DocmVO)vo).setDept(dept);
                    return true;
                }
            }
        }
        return false;
    }

    private String extractDeptId(UserSummaryVO userInfo) {
        return Optional.ofNullable(userInfo)
                .map(user -> user.getDept())
                .map(dept -> dept.getId()).orElse(null);
    }
}
