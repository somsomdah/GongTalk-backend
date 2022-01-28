package com.dasom.gongtalk.crawler;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.domain.user.Alarm;
import com.dasom.gongtalk.domain.user.Subscribe;
import com.dasom.gongtalk.repository.AlarmRepository;
import com.dasom.gongtalk.repository.BoardRepository;
import com.dasom.gongtalk.repository.SubscribeRepository;
import com.dasom.gongtalk.service.AlarmService;
import com.dasom.gongtalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Crawler {

    @Autowired
    private PostService postService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private AlarmService alarmService;

    private static final int MAX_NO_POST_COUNT = 5;

    public Post createPost(Board board, Integer postNum) throws IOException {

        Post post = new Post(board, postNum);

        String pageUrl = post.getUrl();

        Parser parser = new Parser(board, pageUrl);
        post.setContent(parser.extractContent());
        post.setTitle(parser.extractTitle());
        post.setWriter(parser.extractWriter());
        post.setDate(parser.extractDate(board.getDatePattern()));

        try{
            post.setCategory(parser.extractCategory());
        }catch (Exception ignored){
        }

        return post;
    }



    public void crawl(Board board){
        Integer lastPostNum = board.getLastPostNum();
        Integer newPostNum = lastPostNum + 1;
        int noPostCount = 0;

        while(true){
            try{
                Post post = createPost(board, newPostNum);
                postService.save(post);
                alarmService.save(post);
            }catch (Exception ex){
                noPostCount ++;
                if (noPostCount >= MAX_NO_POST_COUNT){
                    break;
                }
            }
            newPostNum ++ ;
        }

        board.setLastPostNum(newPostNum);
        boardRepository.save(board);

    }

}
