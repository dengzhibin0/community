package com.nowcode.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/7/10 14:50
 */
public class WebQueryUtils {
    public static Pageable buildPage(HttpServletRequest request) {
        String pageNumber = request.getParameter("page_number");
        String pageSize = request.getParameter("page_size");
        int page = StringUtils.isBlank(pageNumber) ? 0 : Integer.parseInt(pageNumber) - 1;
        int size = StringUtils.isBlank(pageSize) ? 10 : Integer.parseInt(pageSize);
        Pageable pageable = PageRequest.of(page,size);
        return pageable;
    }

    public static Pageable buildPage(HttpServletRequest request, Sort sort) {
        String pageNumber = request.getParameter("page_number");
        String pageSize = request.getParameter("page_size");
        int page = StringUtils.isBlank(pageNumber) ? 0 : Integer.parseInt(pageNumber) - 1;
        int size = StringUtils.isBlank(pageSize) ? 10 : Integer.parseInt(pageSize);
        Pageable pageable = PageRequest.of(page, size, sort);
        return pageable;
    }

    public static Pageable buildPage(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return pageable;
    }

}
