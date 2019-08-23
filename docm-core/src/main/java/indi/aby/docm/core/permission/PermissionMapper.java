package indi.aby.docm.core.permission;

import indi.rui.common.web.dao.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper extends CommonMapper<PermissionEntity> {
    void add(@Param("perms") List<PermissionEntity> perms);
    List<PermissionEntity> findAll();
    void deleteAll();
}