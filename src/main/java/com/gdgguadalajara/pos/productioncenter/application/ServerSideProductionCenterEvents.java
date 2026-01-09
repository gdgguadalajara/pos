package com.gdgguadalajara.pos.productioncenter.application;

import com.gdgguadalajara.pos.productioncenter.model.ProductionCenter;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServerSideProductionCenterEvents {

    private final BroadcastProcessor<ProductionCenter> processor = BroadcastProcessor.create();

    public void publish(ProductionCenter event) {
        processor.onNext(event);
    }

    public Multi<ProductionCenter> getEventStream() {
        return processor;
    }
}
