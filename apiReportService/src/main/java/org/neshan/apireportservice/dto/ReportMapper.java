package org.neshan.apireportservice.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.neshan.apireportservice.entity.Report;

@Mapper
public interface ReportMapper {
    ReportDto INSTANCE = Mappers.getMapper(ReportDto.class);

    ReportDto ReportToDto(Report report);
}
