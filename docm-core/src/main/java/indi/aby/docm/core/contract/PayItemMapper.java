package indi.aby.docm.core.contract;

import org.apache.ibatis.annotations.Mapper;

import indi.rui.common.web.dao.IMapper;

/**
 * 付款项Mapper
 */
@Mapper
public interface PayItemMapper extends IMapper<PayItemEntity> {
    void deleteAll(PayItemEntity entity);
}
