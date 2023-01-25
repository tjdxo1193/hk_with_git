package lims.api.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PagingResult<T extends Paging> {

    private int offset = 0;
    private int limit = 0;
    private int total = 0;
    private List<T> resultList = new ArrayList<>();

    public PagingResult(List<T> resultList) {
        if (CollectionUtils.isNotEmpty(resultList)) {
            this.resultList = resultList;
            setPagingMeta(resultList.get(0));
        }
    }

    private void setPagingMeta(T t) {
        this.offset = t.getOffset();
        this.limit = t.getLimit();
        this.total = t.getTotal();
    }

}