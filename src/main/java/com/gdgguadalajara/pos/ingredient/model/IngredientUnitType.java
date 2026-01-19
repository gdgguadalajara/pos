package com.gdgguadalajara.pos.ingredient.model;

import com.gdgguadalajara.pos.common.model.DomainException;
import lombok.Getter;

@Getter
public enum IngredientUnitType {
    TON(Dimension.MASS, 1_000_000.0, "t"),
    KG(Dimension.MASS, 1_000.0, "kg"),
    G(Dimension.MASS, 1.0, "g"),
    MG(Dimension.MASS, 0.001, "mg"),

    L(Dimension.VOLUME, 1_000.0, "L"),
    ML(Dimension.VOLUME, 1.0, "ml"),

    PIECE(Dimension.UNITARY, 1.0, "pza");

    private final Dimension dimension;
    private final Double factorToBase;
    private final String symbol;

    IngredientUnitType(Dimension dimension, Double factorToBase, String symbol) {
        this.dimension = dimension;
        this.factorToBase = factorToBase;
        this.symbol = symbol;
    }

    public enum Dimension {
        MASS, VOLUME, UNITARY
    }

    public static Double getConversionFactor(IngredientUnitType from, IngredientUnitType to) {
        if (from.dimension != to.dimension) {
            throw DomainException.badRequest(
                    "Incompatibilidad de dimensiones: No se puede convertir " + from.name() + " a " + to.name());
        }
        return from.factorToBase / to.factorToBase;
    }
}
