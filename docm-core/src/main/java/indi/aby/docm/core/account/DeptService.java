package indi.aby.docm.core.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.aby.docm.api.account.DeptVO;
import indi.aby.docm.api.account.IDeptApi;
import indi.rui.common.base.util.StringUtil;
import indi.rui.common.web.AbstractService;
import indi.rui.common.web.util.BeanUtil;

/**
 * 科室service
 *
 * @author: yaowr
 * @create: 2019-09-06
 */
@Service
public class DeptService extends AbstractService<DeptMapper, DeptEntity, DeptVO> implements IDeptApi {
    @Override
    @Autowired
    protected void setMapper(DeptMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void add(DeptVO vo) {
        checkUniqueName(vo);
        super.add(vo);
    }

    @Override
    public void edit(DeptVO vo) {
        checkUniqueName(vo);
        super.edit(vo);
    }

    @Override
    public List<DeptVO> list() {
        return BeanUtil.copyPropertiesForList(mapper.getAll(), DeptVO.class);
    }

    @Override
    public Boolean checkUniqueName(DeptVO vo) {
        DeptEntity entity = mapper.findByName(vo.getName());
        return entity != null &&
                (StringUtil.isEmpty(vo.getId()) || !vo.getId().equals(entity.getId()));
    }

    @Override
    public Boolean checkRef(DeptVO vo) {
        return Boolean.FALSE;
    }
}
