package com.dasom.gongtalk.crawler;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.repository.BoardRepository;
import com.dasom.gongtalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class Crawler {

    public static Post createPost(Board board, Integer postNum) throws IOException {

        Post post = new Post();
        post.setBoard(board);
        post.setPostNum(postNum);

        String pageUrl = post.getUrl();

        Parser parser = new Parser(board, pageUrl);
        post.setContent(parser.extractContent());
        post.setTitle(parser.extractTitle());
        post.setWriter(parser.extractWriter());
        post.setDate(parser.extractDate(board.getDatePattern()));

        try{
            post.setCategory(parser.extractCategory());
        }catch (Exception ex){
        }

        return post;
    }

    public static void crawl(Board board, PostService postService, BoardRepository boardRepository){
        Integer lastPostNum = board.getLastPostNum();
        Integer newPostNum = lastPostNum + 1;
        int noPostCount = 0;

        while(true){
            try{
                Post post = createPost(board, newPostNum);
                postService.save(post);
            }catch (Exception ex){
                noPostCount ++;
                if (noPostCount >= 10){
                    break;
                }
            }
            newPostNum ++ ;
        }

        board.setLastPostNum(newPostNum);
        boardRepository.save(board);

    }
}
