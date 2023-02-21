package lims.api.ts.service;

import lims.api.ts.vo.TestCollectionVO;

import java.util.List;

public interface TestCollectionService {
    List<TestCollectionVO> getTestCollectionList(TestCollectionVO request);
    void collect(TestCollectionVO request);
    void save(TestCollectionVO request);
    int saveFile(TestCollectionVO request);
}
