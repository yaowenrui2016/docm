package indi.aby.docm.api.contract;

import indi.rui.common.web.IApi;

import java.util.List;

public interface IDocmServiceApi extends IApi<DocmVO> {
    List<String> findAllType();
}
