package com.roomster.roomsterbackend.controller.guest;

import com.roomster.roomsterbackend.dto.PostDtoWithRating;
import com.roomster.roomsterbackend.service.IService.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guest")
@RequiredArgsConstructor
public class GuestController {

    private final IPostService postService;
    @GetMapping(value = "/list-post-by-rating")
    public List<PostDtoWithRating> ListPostByRating(@RequestParam( name = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", required = false, defaultValue = "5") Integer size){
        Pageable pageable = PageRequest.of(page, size);

        return postService.getPostByRating(pageable);
    }
}
