package com.gdgguadalajara.pos.featureflag.application;

import com.gdgguadalajara.pos.featureflag.model.FeatureFlag;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class DeleteFeatureFlag {

    public boolean execute(UUID key) {
        return FeatureFlag.deleteById(key);
    }
}
