package com.roomster.roomsterbackend.controller.management;

import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.service.IService.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@PreAuthorize("hasAnyRole('ROLE_MANAGE','ROLE_ADMIN')")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostController {

    private final IPostService service;

    @PostMapping(value = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpStatus saveNewPost(@RequestBody PostDto postDTO,
                                  @RequestPart(required = false, name = "images") @Valid List<MultipartFile> images) throws IOException {
        service.saveNewPost(postDTO, images);
        return HttpStatus.OK;
    }
}
