package indi.aby.docm.api.dto;

import indi.aby.docm.util.ResourceUtil;
import indi.rui.common.base.dto.AbstractEntity;
import lombok.extern.slf4j.Slf4j;

import static indi.aby.docm.constant.ApplicationConstant.BASE_PACKAGE;

@Slf4j
public abstract class BaseEntity extends AbstractEntity {



    public String getModule() {
        String moduleStr = this.getClass().getCanonicalName();
        String separator = BASE_PACKAGE + ".";
        if (moduleStr.indexOf(separator) < 0) {
            return null;
        }
        moduleStr = moduleStr.split(separator)[1];
        moduleStr = moduleStr.substring(0, moduleStr.indexOf("."));
        return ResourceUtil.getString("module.name", moduleStr);
    }
}
