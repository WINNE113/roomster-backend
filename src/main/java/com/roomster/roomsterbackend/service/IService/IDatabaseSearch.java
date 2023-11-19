package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.dto.SearchResult;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public interface IDatabaseSearch {
    SearchResult searchFilter(Pageable pageable, LinkedHashMap<String, Object> map) throws SQLException;
}
