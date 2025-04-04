package com.example.demo.services;

import com.example.demo.DTO.nvd.NvdCveDTO;
import com.example.demo.entities.CVE;
import com.example.demo.entities.CveResponse;
import com.example.demo.entities.Vulnerability;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class NvdMapperService {
    private static final Logger logger = Logger.getLogger(NvdMapperService.class.getName());

    /**
     * Convertit une réponse complète de l'API NVD en liste de DTO allégés
     */
    public List<NvdCveDTO> convertResponseToDto(CveResponse response) {
        List<NvdCveDTO> result = new ArrayList<>();

        if (response == null || response.getVulnerabilities() == null) {
            return result;
        }

        for (Vulnerability vulnerability : response.getVulnerabilities()) {
            if (vulnerability.getCve() != null) {
                try {
                    // Mapper les données vers le DTO
                    NvdCveDTO dto = mapToDto(vulnerability);
                    result.add(dto);
                } catch (Exception e) {
                    logger.warning("Erreur lors de la conversion de la CVE: " +
                            (vulnerability.getCve() != null ? vulnerability.getCve().getId() : "inconnue") +
                            " - " + e.getMessage());
                }
            }
        }

        logger.info("Converti " + result.size() + " CVEs en DTO allégés");
        return result;
    }

    /**
     * Convertit une vulnérabilité individuelle en DTO
     */
    private NvdCveDTO mapToDto(Vulnerability vulnerability) {
        // La conversion serait implémentée ici
        // Cette méthode nécessite de connaître la structure exacte des classes Vulnerability et CVE
        // pour les mapper correctement vers NvdCveDTO

        // Exemple simplifié - à adapter selon la structure réelle des données:
        NvdCveDTO dto = new NvdCveDTO();
        // Mappage des champs depuis vulnerability.getCve() vers le DTO

        return dto;
    }

    /**
     * Convertit une liste de DTO en entités prêtes à être sauvegardées
     */
    public List<CVE> convertDtoListToEntities(List<NvdCveDTO> dtoList) {
        List<CVE> entities = new ArrayList<>();

        for (NvdCveDTO dto : dtoList) {
            entities.add(dto.toEntity());
        }

        return entities;
    }
}