package com.nowcode.community.service;

import com.nowcode.community.dao.elasticsearch.DiscussPostRepository;
import com.nowcode.community.entity.DiscussPost;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/7/10 13:49
 */
@Service
public class ElasticSearchService {
    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public void saveDiscussPost(DiscussPost post){
        discussPostRepository.save(post);
    }

    public void deleteDiscussPost(int id){
        discussPostRepository.deleteById(id);
    }

    public Page<DiscussPost> searchDiscussPost(String keyword, int current, int limit){
        NativeSearchQueryBuilder queryBuilder=new NativeSearchQueryBuilder();
        NativeSearchQuery query= queryBuilder
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content"))  // 查询的条件
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))    // 排序条件
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current, limit))  // 分页条件
                .withHighlightFields(   // 指定对哪些字段高亮显示，设置关键词标签
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();

        SearchHits<DiscussPost> searchHits = elasticsearchRestTemplate.search(query, DiscussPost.class);

        Pageable pageable=PageRequest.of(current, limit);

        Page<DiscussPost> page;
        if(searchHits.getTotalHits()<=0){
            return null;
        }
        List<DiscussPost> searchProductList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        page=new PageImpl<>(searchProductList,pageable,searchHits.getTotalHits());
        return page;
    }
}
