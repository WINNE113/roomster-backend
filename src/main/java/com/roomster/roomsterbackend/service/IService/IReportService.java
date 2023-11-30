package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.dto.report.ReportDto;

import java.util.List;

public interface IReportService {
    ReportDto addReport(ReportDto reportDto);
    List<ReportDto> getAllReportByPostId(Long postId);
    void deleteReportById(Long[] reportId);
}
