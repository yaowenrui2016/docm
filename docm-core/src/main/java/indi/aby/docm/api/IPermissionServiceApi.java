package indi.aby.docm.api;

import indi.aby.docm.api.permission.PermissionGroupedVO;

import java.util.List;

public interface IPermissionServiceApi {
    List<PermissionGroupedVO> findAll();
}
