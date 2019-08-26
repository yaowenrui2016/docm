package indi.aby.docm.core.permission;

import indi.aby.docm.api.permission.IPermissionServiceApi;
import indi.aby.docm.api.permission.PermissionGroupedVO;
import indi.aby.docm.api.permission.PermissionVO;
import indi.rui.common.web.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermissionService implements IPermissionServiceApi {
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
                        value.add(BeanUtil.copyProperties(entity, PermissionVO.class));
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
