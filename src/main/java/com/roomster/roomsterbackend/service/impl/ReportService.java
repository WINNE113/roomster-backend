package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.report.ReportDto;
import com.roomster.roomsterbackend.mapper.ReportMapper;
import com.roomster.roomsterbackend.repository.ReportRepository;
import com.roomster.roomsterbackend.service.IService.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService implements IReportService {
    @Autowired
    private ReportRepository repository;

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public ReportDto addReport(ReportDto reportDto) {
        reportDto.setCreatedDate(new Date());
        return reportMapper.entityToDto(repository.save(reportMapper.dtoToEntity(reportDto)));
    }

    @Override
    public List<ReportDto> getAllReportByPostId(Long postId) {
        return repository.getAllByPostId_Id(postId).stream().map(reportEntity -> reportMapper.entityToDto(reportEntity)).collect(Collectors.toList());
    }

    @Override
    public void deleteReportById(Long[] reportId) {
        for (Long item : reportId
        ) {
            repository.deleteById(item);
        }
    }
}
