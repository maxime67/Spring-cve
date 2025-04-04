package com.example.demo.services;

import com.example.demo.DTO.CveDTO;
import com.example.demo.DTO.CveMapper;
import com.example.demo.entities.CVE;
import com.example.demo.repositories.CveDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CveService {
    private static final Logger logger = Logger.getLogger(CveService.class.getName());

    private final CveDAO cveDAO;
    private final CveMapper cveMapper;

    @Autowired
    public CveService(CveDAO cveDAO, CveMapper cveMapper) {
        this.cveDAO = cveDAO;
        this.cveMapper = cveMapper;
    }

    @Transactional(readOnly = true)
    public CveDTO findById(String id) {
        if(cveDAO.findById(id).isPresent()) {
            CVE cve = cveDAO.findById(id).get();
            logger.info("Found CVE with ID: " + cve.getId());
            return cveMapper.toDto(cve);
        }
        logger.warning("No CVE found with ID: " + id);
        return null;
    }

    @Transactional(readOnly = true)
    public List<CveDTO> findAll() {
        List<CVE> cves = cveDAO.findAll();
        logger.info("Found " + cves.size() + " CVEs in database");

        // Debug output to check if CVEs have data
        for (CVE cve : cves) {
            logger.info("CVE ID: " + cve.getId() +
                    ", Source: " + cve.getSourceIdentifier() +
                    ", Status: " + cve.getVulnStatus() +
                    ", Descriptions: " + (cve.getDescriptions() != null ? cve.getDescriptions().size() : "null"));
        }

        List<CveDTO> cveDTOs = cveMapper.toDtoList(cves);

        // Debug output to check mapped DTOs
        for (CveDTO dto : cveDTOs) {
            logger.info("DTO ID: " + dto.getId() +
                    ", Source: " + dto.getSourceIdentifier() +
                    ", Status: " + dto.getVulnStatus() +
                    ", Descriptions: " + (dto.getDescriptions() != null ? dto.getDescriptions().size() : "null"));
        }
        return cveDTOs;
    }
}