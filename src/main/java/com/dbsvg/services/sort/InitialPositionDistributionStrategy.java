package com.dbsvg.services.sort;

import java.util.Collection;

import com.dbsvg.objects.view.Vertex;

public interface InitialPositionDistributionStrategy {

	public void distributeVertices(Collection<? extends Vertex> vertices);
}
