package com.longpc.devmon.portal.quizportal.util;

import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Long PC
 * 05/03/2024| 01:00 | 2024
 **/
public class SortUtil {
    public static Sort convertToSort(List<BaseSort> sorts) {
        Sort sort = null;
        if (!CollectionUtils.isEmpty(sorts)) {
            Iterator var2 = sorts.iterator();

            while (var2.hasNext()) {
                BaseSort item = (BaseSort) var2.next();
                if (!item.isAsc()) {
                    sort = sort == null ? Sort.by(new String[]{item.getKey()}).descending() : sort.and(Sort.by(new String[]{item.getKey()}).descending());
                } else {
                    sort = sort == null ? Sort.by(new String[]{item.getKey()}) : sort.and(Sort.by(new String[]{item.getKey()}));
                }
            }
        }
        return sort;
    }

    public static List<BaseSort> getCreateTimeSort() {
        List<BaseSort> sorts = new ArrayList<>();
        BaseSort createdStampSort = new BaseSort();
        createdStampSort.setKey("createdTime");
        createdStampSort.setAsc(false);
        sorts.add(createdStampSort);
        return sorts;
    }
}
