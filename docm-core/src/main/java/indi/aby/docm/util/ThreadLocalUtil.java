package indi.aby.docm.util;

import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ThreadLocalUtil {
    private static ThreadLocal<Map<String, Object>> threadVar = new ThreadLocal<>();

    public static Object get(String varName) {
        return Optional.ofNullable(threadVar.get())
                .map(map -> map.get(varName))
                .orElse(null);
    }

    public static Object put(String varName, Object value) {
        Map<String, Object> vars = threadVar.get();
        if (CollectionUtils.isEmpty(vars)) {
            vars = new HashMap<>();
            threadVar.set(vars);
        }
        return vars.put(varName, value);
    }

    public static Object remove(String varName) {
        Map<String, Object> vars = threadVar.get();
        if (CollectionUtils.isEmpty(vars)) {
            return null;
        }
        return vars.remove(varName);
    }
}
