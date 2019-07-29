package indi.aby.docm.core.permission.service;

import indi.aby.docm.api.IPermissionServiceApi;
import indi.aby.docm.api.dto.PermissionGroupedVO;
import indi.aby.docm.api.dto.PermissionVO;
import indi.aby.docm.core.permission.dao.PermissionMapper;
import indi.aby.docm.core.permission.entity.PermissionEntity;
import indi.rui.common.web.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermissionService extends AbstractService implements IPermissionServiceApi {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionGroupedVO> findAll() {
        List<PermissionEntity> entities = permissionMapper.findAll();
        Map<String, List<PermissionVO>> map = new HashMap<>();
        if (entities != null) {
            entities.stream()
                    .forEach(entity -> {
                        List<PermissionVO> value = map.get(entity.getModule());
                        if (value == null) {
                            value = new ArrayList<>();
                            map.put(entity.getModule(), value);
                        }
                        value.add(copyProperties(entity, PermissionVO.class));
                    });
        }
        List<PermissionGroupedVO> list = new ArrayList<>();
        Iterator<Map.Entry<String, List<PermissionVO>>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<PermissionVO>> entry = it.next();
            list.add(new PermissionGroupedVO(entry.getKey(), entry.getValue()));
        }
        return list;
    }
}
