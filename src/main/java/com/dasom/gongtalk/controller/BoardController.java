package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.dto.PostListResponse;
import com.dasom.gongtalk.dto.PostResponse;
import com.dasom.gongtalk.repository.PostRepository;
import com.dasom.gongtalk.service.BoardService;
import com.dasom.gongtalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/boards")
public class BoardController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final BoardService boardService;

    @GetMapping(value="{id}", params = {"max"})
    public ResponseEntity<List<PostListResponse>> getOnePost(@PathVariable Integer id,
                                                         @RequestParam(value="max") int max){
        List<Post> posts = postService.getPostsFromBoard(boardService.getFromId(id), max);
        List<PostListResponse> response = PostListResponse.fromPosts(posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "{id}/search", params = {"keyword"})
    public ResponseEntity<List<PostListResponse>> getAllPostsFromKeywords(@PathVariable Integer id,
                                                                          @RequestParam(value = "keyword") List<String> searchKeywords){
        Board board = boardService.getFromId(id);
        List<Post> posts = postRepository.findAllByBoardAndKeywordsContentIn(board,searchKeywords);
        List<PostListResponse> response = PostListResponse.fromPosts(posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
