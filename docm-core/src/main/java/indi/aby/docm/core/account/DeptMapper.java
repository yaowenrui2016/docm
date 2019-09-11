package indi.aby.docm.core.account;

import indi.rui.common.base.dto.QueryRequest;
import indi.rui.common.base.field.IFieldId;
import indi.rui.common.web.dao.IMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 科室mapper
 *
 * @author: yaowr
 * @create: 2019-09-06
 */
@Mapper
public interface DeptMapper extends IMapper<DeptEntity> {
    @Override
    void add(DeptEntity deptEntity);

    @Override
    void update(DeptEntity deptEntity);

    @Override
    Integer findTotalNum(QueryRequest queryRequest);

    @Override
    List<DeptEntity> findAll(QueryRequest queryRequest);

    List<DeptEntity> getAll();

    @Override
    DeptEntity findById(IFieldId iFieldId);

    @Override
    void delete(IFieldId iFieldId);

    DeptEntity findByName(String name);
}
