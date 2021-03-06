/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {
	/**
	 * 	 * Returns the slope between this point and the specified point.
	 * 	 * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
	 * 	 * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
	 * 	 * +0.0 if the line segment connecting the two points is horizontal;
	 * 	 * Double.POSITIVE_INFINITY if the line segment is vertical;
	 */
	@Test
	public void slopeTo_samePoint() {
		Point testee = new Point(1, 1);
		Point other = new Point(1, 1);
		assertThat(testee.slopeTo(other)).isEqualTo(Double.NEGATIVE_INFINITY);
	}
}
