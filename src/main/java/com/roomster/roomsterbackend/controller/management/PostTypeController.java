package com.roomster.roomsterbackend.controller.management;

import com.roomster.roomsterbackend.dto.PostTypeDto;
import com.roomster.roomsterbackend.service.IService.IPostTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/postType")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_MANAGE','ROLE_ADMIN')")
@CrossOrigin(origins = "*")
public class PostTypeController {

    private final IPostTypeService postTypeService;

    @GetMapping("/getAll")
    public List<PostTypeDto> getAllPostType(){
        return postTypeService.getAllPostType();
    }
}
