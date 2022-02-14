package indi.aby.docm.core.contract;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2022-02-14
 */
@Getter
@Setter
public class InvalidAttachInfo {

    /**
     * 路径
     */
    private String path;

    /**
     * 附件ID
     */
    private String attachId;

}
