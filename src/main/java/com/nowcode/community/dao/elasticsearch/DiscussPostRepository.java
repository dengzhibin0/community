package com.nowcode.community.dao.elasticsearch;

import com.nowcode.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/7/10 9:08
 */
@Service
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {

}
