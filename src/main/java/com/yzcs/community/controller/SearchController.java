package com.yzcs.community.controller;

import com.yzcs.community.entity.DiscussPost;
import com.yzcs.community.entity.Page;
import com.yzcs.community.service.ElasticsearchService;
import com.yzcs.community.service.LikeService;
import com.yzcs.community.service.UserService;
import com.yzcs.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController implements CommunityConstant {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    //search？keyword=xxx
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String search(String keyword, Page page, Model model) {
        //搜索帖子
        Map<String, Object> result = elasticsearchService.searchDiscussPost(keyword, page.getCurrent() - 1,
                page.getLimit());
        List<DiscussPost> discussPostList = (List) result.get("discussPosts");
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        for (DiscussPost post : discussPostList) {
            Map<String, Object> map = new HashMap<>();
            //帖子
            map.put("post", post);
            //作者
            map.put("user", userService.findUserById(post.getUserId()));
            //点赞数量
            map.put("likeCount", likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId()));

            discussPosts.add(map);
        }
        model.addAttribute("discussPosts", discussPosts);
        model.addAttribute("keyword", keyword);

        // 分页信息
        page.setPath("/search?keyword=" + keyword);
        page.setRows(((Long) result.get("totalCount")).intValue());

        return "/site/search";
    }
}
