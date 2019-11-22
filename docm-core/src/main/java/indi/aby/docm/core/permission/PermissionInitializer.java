package indi.aby.docm.core.permission;

import indi.aby.docm.core.permission.annotation.Permission;
import indi.aby.docm.core.permission.annotation.Permissions;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.*;

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
        Map<String, PermissionEntity> entities = new HashMap<>();
        extract4Multiple(reflections, entities);    // 扫描Permissions注解提取一个方法上定义的多个权限
        extract4Single(reflections, entities);    // 扫描Permission注解提取一个方法上定义的单个权限
        save(entities.values()); // 保存权限
    }

    private void extract4Multiple(Reflections reflections, Map<String, PermissionEntity> entities) {
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Permissions.class);
        if (methods != null) {
            methods.stream()
                .forEach(method -> {
                    Permissions permissions = method.getAnnotation(Permissions.class);
                    Arrays.stream(permissions.value())
                            .forEach(permission -> entities.putIfAbsent(permission.id(), build(permission)));
                });
        }
    }

    private void extract4Single(Reflections reflections, Map<String, PermissionEntity> entities) {
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Permission.class);
        if (methods != null) {
            methods.stream()
                .forEach(method -> {
                    Permission permission = method.getAnnotation(Permission.class);
                    entities.putIfAbsent(permission.id(), build(permission));
                });
        }
    }

    private PermissionEntity build(Permission permission) {
        Objects.requireNonNull(permission);
        PermissionEntity entity = new PermissionEntity();
        entity.setId(permission.id());
        entity.setName(permission.name());
        entity.setModule(permission.module());
        entity.setDesc(permission.desc());
        return entity;
    }

    private void save(Collection<PermissionEntity> entities) {
        /* 保存权限是先清除表中的记录，再进行插入 */
        permissionMapper.deleteAll();
        permissionMapper.add(entities);
        log.info("权限初始化成功");
    }
}
