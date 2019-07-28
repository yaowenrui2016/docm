package indi.aby.docm.core.permission.init;

import indi.aby.docm.core.auth.annotation.Permission;
import indi.aby.docm.core.permission.dao.PermissionMapper;
import indi.aby.docm.core.permission.entity.PermissionEntity;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PermissionInitializer implements ApplicationRunner {
    @Autowired
    private PermissionMapper permissionMapper;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("indi.aby.docm.core"))
                .setScanners(new MethodAnnotationsScanner()));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Permission.class);
        if (methods != null) {
            List<PermissionEntity> perms = methods.stream()
                    .map(method -> {
                        Permission permission = method.getAnnotation(Permission.class);
                        PermissionEntity entity = new PermissionEntity();
                        entity.setId(permission.id());
                        entity.setName(permission.name());
                        entity.setModule(permission.module());
                        entity.setDesc(permission.desc());
                        return entity;
                    })
                    .sorted(Comparator.comparing((a) -> a.getId()))
                    .collect(Collectors.toList());
            permissionMapper.deleteAll();   // 清空权限
            permissionMapper.add(perms);    // 保存权限
            log.info("权限初始化成功");
        }
    }
}
