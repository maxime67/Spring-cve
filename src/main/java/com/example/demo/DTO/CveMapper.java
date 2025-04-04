package com.example.demo.DTO;

import com.example.demo.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CveMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "sourceIdentifier", target = "sourceIdentifier")
    @Mapping(source = "published", target = "published")
    @Mapping(source = "lastModified", target = "lastModified")
    @Mapping(source = "vulnStatus", target = "vulnStatus")
    CveDTO toDto(CVE cve);
    List<CveDTO> toDtoList(List<CVE> cves);

    DescriptionDTO toDto(Description description);
    MetricsDTO toDto(Metrics metrics);
    CvssMetricV2DTO toDto(CvssMetricV2 cvssMetricV2);
    CvssDataDTO toDto(CvssData cvssData);
}
