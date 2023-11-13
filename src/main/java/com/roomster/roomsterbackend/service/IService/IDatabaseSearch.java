package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.dto.PostDto;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public interface IDatabaseSearch {
    List<PostDto> searchFilter(Pageable pageable, LinkedHashMap<String, Object> map) throws SQLException;
}
