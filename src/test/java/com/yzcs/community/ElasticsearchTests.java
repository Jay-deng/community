package com.yzcs.community;


import com.yzcs.community.Dao.DiscussPostMapper;
import com.yzcs.community.entity.DiscussPost;
import com.yzcs.community.service.elasticsearch.DiscussPostRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ElasticsearchTests {

    @Autowired
    private DiscussPostMapper discussMapper;

    @Autowired
    private DiscussPostRepository discussRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Qualifier("client")
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testInsert() {
        discussRepository.save(discussMapper.selectDiscussPostById(241));
        discussRepository.save(discussMapper.selectDiscussPostById(242));
        discussRepository.save(discussMapper.selectDiscussPostById(243));

    }

    @Test
    public void testInsertList() {
        discussRepository.saveAll(discussMapper.selectDiscussPosts(101, 0, 100, 0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(102, 0, 100, 0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(103, 0, 100, 0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(111, 0, 100, 0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(112, 0, 100, 0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(131, 0, 100, 0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(132, 0, 100, 0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(133, 0, 100, 0));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(134, 0, 100, 0));
    }

    @Test
    public void testUpdate() {
        DiscussPost post = discussMapper.selectDiscussPostById(231);
        post.setContent("????????????????????????????????????");
        discussRepository.save(post);
    }


    // ?????????
//    @Test
//    public void testSearchByRepository() {
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.multiMatchQuery("???????????????", "title", "content"))
//                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
//                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
//                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
//                .withPageable(PageRequest.of(0, 10))
//                .withHighlightFields(
//                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
//                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
//                ).build();
//
////         elasticsearchRestTemplate.queryForPage(searchQuery,class,searchResultMapper);
////        discussPostRepository?????????????????????????????????????????????????????????
////        ???????????????ElasticsearchRestTemplate
//        Page<DiscussPost> page = discussRepository.search(nativeSearchQuery);
//        System.out.println(page.getTotalElements());
//        System.out.println(page.getTotalPages());
//        System.out.println(page.getNumber());
//        System.out.println(page.getSize());
//        for(DiscussPost discussPost : page) {
//            System.out.println(discussPost);
//        }
//    }

//    //?????????????????????
//    @Test
//    public void noHighlightQuery() throws IOException {
//        SearchRequest searchRequest = new SearchRequest("discusspost");//discusspost???????????????????????????
//
//        //??????????????????
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
//                //???discusspost?????????title???content???????????????????????????????????????
//                .query(QueryBuilders.multiMatchQuery("???????????????", "title", "content"))
//                // matchQuery????????????????????????key???????????????searchSourceBuilder.query(QueryBuilders.matchQuery(key,value));
//                // termQuery??????????????????searchSourceBuilder.query(QueryBuilders.termQuery(key,value));
//                .sort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
//                .sort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
//                .sort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
//                //??????????????????????????????????????????????????????searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//                .from(0)// ???????????????????????????
//                .size(10);// ??????????????????????????????
//
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//
//        System.out.println(JSONObject.toJSON(searchResponse));
//
//        List<DiscussPost> list = new LinkedList<>();
//        for (SearchHit hit : searchResponse.getHits().getHits()) {
//            DiscussPost discussPost = JSONObject.parseObject(hit.getSourceAsString(), DiscussPost.class);
//            System.out.println(discussPost);
//            list.add(discussPost);
//        }
//    }

    //https://www.cnblogs.com/hi3254014978/p/14055771.html
    @Test
    public void testSearchByTemplate() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("???????????????", "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();

        SearchHits<DiscussPost> search = elasticsearchRestTemplate.search(searchQuery, DiscussPost.class);
        // ?????????????????????????????????
        List<SearchHit<DiscussPost>> searchHits = search.getSearchHits();
        // ??????????????????????????????????????????
        List<DiscussPost> discussPosts = new ArrayList<>();
        for (SearchHit<DiscussPost> searchHit : searchHits) {
            // ???????????????
            Map<String, List<String>> highLightFields = searchHit.getHighlightFields();
            // ???????????????????????????content???
            searchHit.getContent().setTitle(highLightFields.get("title") == null ? searchHit.getContent().getTitle() : highLightFields.get("title").get(0));
            searchHit.getContent().setTitle(highLightFields.get("content") == null ? searchHit.getContent().getContent() : highLightFields.get("content").get(0));
            // ??????????????????
            discussPosts.add(searchHit.getContent());
        }
        System.out.println(discussPosts.size());
        for (DiscussPost discussPost : discussPosts) {
            System.out.println(discussPost);
        }

    }

}
