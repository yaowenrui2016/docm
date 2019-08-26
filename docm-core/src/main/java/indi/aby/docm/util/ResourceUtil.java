package indi.aby.docm.util;

import indi.rui.common.base.dto.DefaultStatus;
import indi.rui.common.base.util.StringUtil;
import indi.rui.common.web.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import static indi.aby.docm.constant.ApplicationConstant.BASE_PACKAGE;
import static indi.aby.docm.constant.ApplicationConstant.CORE_PACKAGE;

@Slf4j
public class ResourceUtil {
    private static final String SUFFIX = ".resource.ApplicationResource";

    public static String getString(String msgKey, String module) {
        if (StringUtil.isEmpty(msgKey)) {
            return null;
        }
        String baseName = (StringUtil.isEmpty(module) ? BASE_PACKAGE : CORE_PACKAGE + "." + module) + SUFFIX;
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName);
        try {
            return new String(resourceBundle.getString(msgKey).getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            throw new BizException(DefaultStatus.EXCEPTION, "获取资源文件异常");
        }
    }

    public static String getString(String msgKey) {
        return getString(msgKey, null);
    }
}
