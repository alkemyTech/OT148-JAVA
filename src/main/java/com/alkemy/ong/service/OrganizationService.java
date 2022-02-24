package com.alkemy.ong.service;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.model.OrganizationModel;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Transactional
    public List<OrganizationDTO> findAll() {
        List<OrganizationModel> organizationModelList = organizationRepository.findAll();
        return organizationModelList.stream().map(OrganizationMapper::mapModelToDomain)
                .map(OrganizationMapper::mapDomainToDTO)
                .collect(Collectors.toList());
    }
}
