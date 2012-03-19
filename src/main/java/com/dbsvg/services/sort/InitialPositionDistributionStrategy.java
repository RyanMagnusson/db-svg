package com.dbsvg.services.sort;

import java.util.List;

import com.dbsvg.objects.view.Vertex;

public interface InitialPositionDistributionStrategy {

	public void distributeVertices(List<? extends Vertex> vertices);
}
