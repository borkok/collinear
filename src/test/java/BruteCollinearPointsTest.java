import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
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
				,Arguments.of(singletonList(xy(1, 1)), Collections.EMPTY_LIST)
				,Arguments.of(asList(xy(1,1),xy(2,2)), Collections.EMPTY_LIST)
				,Arguments.of(asList(xy(1,1),xy(2,2),xy(3,3)), Collections.EMPTY_LIST)
				,Arguments.of(asList(xy(1,1),xy(2,2),xy(3,3),xy(4,4)), singletonList(line(xy(1, 1), xy(4, 4))))
				,Arguments.of(asList(xy(1,1),xy(2,2),xy(4,4),xy(3,3)), singletonList(line(xy(1, 1), xy(4, 4))))
				,Arguments.of(asList(xy(1,1),xy(-2,-2),xy(3,3),xy(4,4)), singletonList(line(xy(-2, -2), xy(4, 4))))
//				,Arguments.of(asList(xy(1,1),xy(2,2),xy(3,3),xy(4,4),xy(5,6)), singletonList(line(xy(1, 1), xy(4, 4))))
//				,Arguments.of(asList(xy(1,1),xy(2,2),xy(3,3),xy(1,2)), Collections.EMPTY_LIST)
//
/*
				,Arguments.of(asList(
								xy(1,1),xy(2,2),xy(3,3),xy(4,4),
								xy(0,2),xy(2,0),xy(3,-1),
								xy(2,5)
							,asList(
								line(xy(1, 1), xy(4, 4)),
								line(xy(3, -1), xy(0, 2))
							)
				)
*/
				/*
				,Arguments.of(asList(
								xy(1,1),xy(2,2),xy(3,3),xy(4,4),
								xy(1,0),xy(3,1),xy(5,2),xy(-1,-1),
								xy(2,5)
							,asList(
								line(xy(1, 1), xy(4, 4)),
								line(xy(-1, -1), xy(5, 2))
							)
				)
*/
		);
	}

	private static LineSegment line(Point p1, Point p2) {
		return new LineSegment(p1, p2);
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