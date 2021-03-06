/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {

	@Test
	public void slopeTo() {
		Point testee = new Point(1, 1);
		Point other = new Point(3, 2);
		assertThat(testee.slopeTo(other)).isEqualTo(0.5d);
	}

	@Test
	public void slopeTo_horizontalLine() {
		Point testee = new Point(1, 1);
		Point other = new Point(2, 1);
		assertThat(testee.slopeTo(other)).isEqualTo(0.0d);
	}

	@Test
	public void slopeTo_verticalLine() {
		Point testee = new Point(1, 1);
		Point other = new Point(1, 2);
		assertThat(testee.slopeTo(other)).isEqualTo(Double.POSITIVE_INFINITY);
	}

	@Test
	public void slopeTo_samePoint() {
		Point testee = new Point(1, 1);
		Point other = new Point(1, 1);
		assertThat(testee.slopeTo(other)).isEqualTo(Double.NEGATIVE_INFINITY);
	}
}
