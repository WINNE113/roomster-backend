package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.PostTypeDto;

import java.util.List;


public interface IPostTypeService {
    List<PostTypeDto> getAllPostType();

    BaseResponse addPostType(PostTypeDto postTypeDto);
}
