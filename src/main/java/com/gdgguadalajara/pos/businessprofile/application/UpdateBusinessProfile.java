package com.gdgguadalajara.pos.businessprofile.application;

import com.gdgguadalajara.pos.businessprofile.model.BusinessProfile;
import com.gdgguadalajara.pos.businessprofile.model.dto.UpdateBusinessProfileRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class UpdateBusinessProfile {

    public BusinessProfile run(UpdateBusinessProfileRequest updateBusinessProfileRequest) {
        BusinessProfile businessProfile = BusinessProfile.findAll().firstResult();
        businessProfile.name = updateBusinessProfileRequest.name();
        businessProfile.legalName = updateBusinessProfileRequest.legalName();
        businessProfile.taxId = updateBusinessProfileRequest.taxId();
        businessProfile.address = updateBusinessProfileRequest.address();
        businessProfile.phone = updateBusinessProfileRequest.phone();
        businessProfile.email = updateBusinessProfileRequest.email();
        businessProfile.website = updateBusinessProfileRequest.website();
        businessProfile.ticketFooter = updateBusinessProfileRequest.ticketFooter();
        businessProfile.logoUrl = updateBusinessProfileRequest.logoUrl();
        businessProfile.persistAndFlush();
        return businessProfile;
    }
}
