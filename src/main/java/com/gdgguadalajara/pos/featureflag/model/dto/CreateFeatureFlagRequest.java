package com.gdgguadalajara.pos.featureflag.model.dto;

public record CreateFeatureFlagRequest(String name, Boolean enabled, String description) {
}
