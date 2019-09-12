package indi.aby.docm.core.permission;

import indi.aby.docm.api.permission.annotation.Permission;
import indi.aby.docm.api.permission.annotation.Permissions;
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
        List<PermissionEntity> entities = new ArrayList<>();
        entities.addAll(extract4Multiple(reflections));    // 扫描Permissions注解提取一个方法上定义的多个权限
        entities.addAll(extract4Single(reflections));    // 扫描Permission注解提取一个方法上定义的单个权限

        save(entities); // 保存权限
    }

    private Collection<? extends PermissionEntity> extract4Multiple(Reflections reflections) {
        List<PermissionEntity> entities = new ArrayList<>();
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Permissions.class);
        if (methods != null) {
            methods.stream()
                .forEach(method -> {
                    Permissions permissions = method.getAnnotation(Permissions.class);
                    Arrays.stream(permissions.value())
                            .forEach(permission -> entities.add(build(permission)));
                });
        }
        return entities;
    }

    private Collection<? extends PermissionEntity> extract4Single(Reflections reflections) {
        List<PermissionEntity> entities = new ArrayList<>();
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Permission.class);
        if (methods != null) {
            methods.stream()
                .forEach(method -> entities.add(build(method.getAnnotation(Permission.class))));
        }
        return entities;
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

    private void save(List<PermissionEntity> entities) {
        /* 保存权限是先清除表中的记录，再进行插入 */
        permissionMapper.deleteAll();
        permissionMapper.add(entities);
        log.info("权限初始化成功");
    }
}
