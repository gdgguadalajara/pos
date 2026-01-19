package com.gdgguadalajara.pos.featureflag.application;

import com.gdgguadalajara.pos.featureflag.model.FeatureFlag;
import com.gdgguadalajara.pos.featureflag.model.dto.UpdateFeatureFlagRequest;
import com.gdgguadalajara.pos.common.model.DomainException;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UpdateFeatureFlag {

    public FeatureFlag execute(UUID key, UpdateFeatureFlagRequest request) {
        FeatureFlag featureFlag = FeatureFlag.findById(key);
        if (featureFlag == null) 
            throw DomainException.notFound("Feature flag no encontrada");
        featureFlag.enabled = request.enabled();
        featureFlag.description = request.description();
        return featureFlag;
    }
}
