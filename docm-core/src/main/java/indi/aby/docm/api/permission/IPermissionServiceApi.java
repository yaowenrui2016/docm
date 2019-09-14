package indi.aby.docm.api.permission;

import indi.rui.common.web.IApi;

import java.util.List;

public interface IPermissionServiceApi extends IApi<PermissionVO> {
    List<PermissionGroupedVO> findAll();
}
