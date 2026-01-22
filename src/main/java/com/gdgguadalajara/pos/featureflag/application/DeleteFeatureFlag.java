package com.gdgguadalajara.pos.featureflag.application;

import com.gdgguadalajara.pos.featureflag.model.FeatureFlag;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
@Transactional
public class DeleteFeatureFlag {

    public boolean run(UUID key) {
        return FeatureFlag.deleteById(key);
    }
}
