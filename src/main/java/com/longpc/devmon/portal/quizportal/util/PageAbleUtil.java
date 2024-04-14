package com.longpc.devmon.portal.quizportal.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;

/**
 * Long PC
 * 05/03/2024| 00:59 | 2024
 **/
public class PageAbleUtil {
    public static Pageable convertPageable(Integer pageNumber, Integer pageSize) {
        Sort sort = null;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return pageable;
    }

    public static Pageable convertPageableAndSort(Integer pageNumber, Integer pageSize, List<BaseSort> sorts) {
        Sort sort = SortUtil.convertToSort(sorts);
        return Objects.nonNull(sort) ? PageRequest.of(pageNumber, pageSize, sort) : PageRequest.of(pageNumber, pageSize);
    }
}
