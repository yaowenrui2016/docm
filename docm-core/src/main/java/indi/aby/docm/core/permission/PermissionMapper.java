package indi.aby.docm.core.permission;

import indi.rui.common.web.dao.IMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper extends IMapper<PermissionEntity> {
    @Override
    void add(List<PermissionEntity> entities);

    List<PermissionEntity> findAll();
    void deleteAll();
}
