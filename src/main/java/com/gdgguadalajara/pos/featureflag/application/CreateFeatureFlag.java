package com.gdgguadalajara.pos.featureflag.application;

import com.gdgguadalajara.pos.featureflag.model.FeatureFlag;
import com.gdgguadalajara.pos.featureflag.model.dto.CreateFeatureFlagRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CreateFeatureFlag {

    public FeatureFlag run(CreateFeatureFlagRequest request) {
        FeatureFlag featureFlag = new FeatureFlag();
        featureFlag.name = request.name();
        featureFlag.enabled = request.enabled();
        featureFlag.description = request.description();
        featureFlag.persistAndFlush();
        return featureFlag;
    }
}
