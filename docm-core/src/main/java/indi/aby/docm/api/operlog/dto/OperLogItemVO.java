package indi.aby.docm.api.operlog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperLogItemVO {
    private String model;
    private Object val;

    public static OperLogItemVO of(Object item) {
        if (item == null) {
            return null;
        }
        return new OperLogItemVO(item.getClass().getSimpleName(), item);
    }
}
