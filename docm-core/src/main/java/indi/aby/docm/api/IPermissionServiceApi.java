package indi.aby.docm.api;

import indi.aby.docm.api.dto.PermissionGroupedVO;

import java.util.List;

public interface IPermissionServiceApi {
    List<PermissionGroupedVO> findAll();
}
