import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BruteCollinearPointsTest {
	@Test
	public void init_invalidInputs() {
		assertThatThrownBy(() -> new BruteCollinearPoints(null))
				.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new BruteCollinearPoints(new Point[]{new Point(1,1), null}))
				.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new BruteCollinearPoints(new Point[]{new Point(1,1), new Point(1,1)}))
				.isInstanceOf(IllegalArgumentException.class);
	}

	private static Stream<Arguments> segments() {
		return Stream.of(
				Arguments.of(Collections.EMPTY_LIST, Collections.EMPTY_LIST)
					//asList()

		);
	}

	private static Point xy(int x, int y) {
		return new Point(x, y);
	}

	@ParameterizedTest
	@MethodSource("segments")
	public void calculates_segments(List<Point> points, List<LineSegment> expectedSegments) {
		//WHEN
		BruteCollinearPoints testee = new BruteCollinearPoints(points.toArray(new Point[0]));
		//THEN
		assertThat(testee.segments())
				.containsExactlyInAnyOrder(expectedSegments.toArray(new LineSegment[0]));
		assertThat(testee.numberOfSegments()).isEqualTo(expectedSegments.size());
	}
}