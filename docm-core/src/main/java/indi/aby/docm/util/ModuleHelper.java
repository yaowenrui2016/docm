package indi.aby.docm.util;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static indi.aby.docm.constant.ApplicationConstant.API_PACKAGE;
import static indi.aby.docm.constant.ApplicationConstant.CORE_PACKAGE;

public class ModuleHelper {
    public static String getModule(String className) {
        List<String> modules = Arrays.stream(new String[]{API_PACKAGE, CORE_PACKAGE})
                .map(separator -> separator + ".")
                .filter(separator -> className.indexOf(separator) >= 0)
                .map(separator -> className.split(separator)[1])
                .map(moduleStr -> moduleStr.substring(0, moduleStr.indexOf(".")))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(modules)) {
            return null;
        }
        return modules.get(0);
    }

    public static String getModuleName(String module) {
        return ResourceUtil.getString("module.name", module);
    }

    public static String getModuleName(Class<?> klass) {
        if (klass == null) {
            return null;
        }
        return ResourceUtil.getString("module.name", getModule(klass.getCanonicalName()));
    }
}
