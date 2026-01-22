package com.gdgguadalajara.pos.user.application;

import java.util.Set;
import java.util.UUID;

import com.gdgguadalajara.pos.common.model.DomainException;
import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;
import com.gdgguadalajara.pos.user.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class DeleteUsertProductionCenter {

    public Set<ProductionCenter> run(UUID userId, UUID productionCenterId) {
        var user = User.<User>findById(userId);
        if (user == null)
            throw DomainException.notFound("Usuario no encontrado");
        var productionCenter = ProductionCenter.<ProductionCenter>findById(productionCenterId);
        if (productionCenter == null)
            throw DomainException.notFound("Centro de producción no encontrado");
        var account = user.account;
        account.productionCenters.remove(productionCenter);
        account.persistAndFlush();
        return account.productionCenters;
    }
}
