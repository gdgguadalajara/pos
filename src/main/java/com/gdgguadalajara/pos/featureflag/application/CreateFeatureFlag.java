package com.gdgguadalajara.pos.featureflag.application;

import com.gdgguadalajara.pos.featureflag.model.FeatureFlag;
import com.gdgguadalajara.pos.featureflag.model.dto.CreateFeatureFlagRequest;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateFeatureFlag {

    public FeatureFlag execute(CreateFeatureFlagRequest request) {
        FeatureFlag featureFlag = new FeatureFlag();
        featureFlag.name = request.name();
        featureFlag.enabled = request.enabled();
        featureFlag.description = request.description();
        featureFlag.persistAndFlush();
        return featureFlag;
    }
}
