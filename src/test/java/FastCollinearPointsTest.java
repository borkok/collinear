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

class FastCollinearPointsTest {
    @Test
    public void init_invalidInputs() {
        assertThatThrownBy(() -> new FastCollinearPoints(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new FastCollinearPoints(new Point[] { null }))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new FastCollinearPoints(new Point[] { xy(1, 1), null }))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new FastCollinearPoints(new Point[] { xy(1, 1), xy(1, 1) }))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> segments() {
        return Stream.of(
                Arguments.of(Collections.EMPTY_LIST, Collections.EMPTY_LIST)
                , Arguments.of(singletonList(xy(1, 1)), Collections.EMPTY_LIST)
                , Arguments.of(asList(xy(1, 1), xy(2, 2)), Collections.EMPTY_LIST)
                , Arguments.of(asList(xy(1, 1), xy(2, 2), xy(3, 3)), Collections.EMPTY_LIST)
                , Arguments.of(asList(xy(1, 1), xy(2, 2), xy(3, 3), xy(4, 4)),
                               singletonList(line(xy(1, 1), xy(4, 4))))
                , Arguments.of(asList(xy(1, 1), xy(2, 2), xy(4, 4), xy(3, 3)),
                               singletonList(line(xy(1, 1), xy(4, 4))))
                , Arguments.of(asList(xy(1, 1), xy(-2, -2), xy(3, 3), xy(4, 4)),
                               singletonList(line(xy(-2, -2), xy(4, 4))))
                , Arguments.of(asList(xy(1, 1), xy(2, 2), xy(3, 3), xy(4, 4), xy(5, 6)),
                               singletonList(line(xy(1, 1), xy(4, 4))))
                ,
                Arguments.of(asList(xy(1, 1), xy(2, 2), xy(3, 3), xy(1, 2)), Collections.EMPTY_LIST)

                , Arguments.of(asList(
                        xy(1, 1), xy(2, 2), xy(3, 3), xy(4, 4),
                        xy(0, 4), xy(4, 0), xy(5, -1),
                        xy(2, 5))
                        , asList(
                                line(xy(1, 1), xy(4, 4)),
                                line(xy(5, -1), xy(0, 4))
                        )
                )

                , Arguments.of(asList(
                        xy(7, 3), xy(1, 0),
                        xy(1, 1), xy(2, 2),
                        xy(3, 3), xy(4, 4),
                        xy(3, 1), xy(5, 2),
                        xy(2, 5))
                        , asList(
                                line(xy(1, 1), xy(4, 4)),
                                line(xy(1, 0), xy(7, 3)))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("segments")
    public void calculates_segments(List<Point> points, List<LineSegment> expectedSegments) {
        //WHEN
        FastCollinearPoints testee = new FastCollinearPoints(points.toArray(new Point[0]));
        //THEN
        assertThat(testee.segments())
                .containsExactlyInAnyOrder(expectedSegments.toArray(new LineSegment[0]));
        assertThat(testee.numberOfSegments()).isEqualTo(expectedSegments.size());
    }


    @ParameterizedTest
    @MethodSource("autograder")
    public void calculates_autograder(List<Point> points, List<LineSegment> expectedSegments) {
        FastCollinearPoints testee = new FastCollinearPoints(points.toArray(new Point[0]));
        //checking only if contains at least segment listed in autograder failure output
        assertThat(testee.segments()).containsAnyOf(expectedSegments.toArray(new LineSegment[0]));
    }

    private static Stream<Arguments> autograder() {
        return Stream.of(
                //equidistant
                Arguments.of(asList(
                        xy(10000, 0),
                        xy(8000, 2000),
                        xy(2000, 8000),
                        xy(0, 10000),

                        xy(20000, 0),
                        xy(18000, 2000),
                        xy(2000, 18000),

                        xy(10000, 20000),
                        xy(30000, 0),
                        xy(0, 30000),
                        xy(20000, 10000),

                        xy(13000, 0),
                        xy(11000, 3000),
                        xy(5000, 12000),
                        xy(9000, 6000)
                             )
                        , asList(
                                line(xy(10000, 0), xy(0, 10000)),
                                line(xy(10000, 0), xy(30000, 0)),
                                line(xy(30000, 0), xy(0, 30000)),
                                line(xy(13000, 0), xy(5000, 12000))
                        )),


                //input40
                Arguments.of(asList(
                        xy(1000, 17000),
                        xy(14000, 24000),
                        xy(26000, 8000),
                        xy(10000, 28000),
                        xy(18000, 5000),
                        xy(1000, 27000),
                        xy(14000, 14000),
                        xy(11000, 16000),
                        xy(29000, 17000),
                        xy(5000, 21000),
                        xy(19000, 26000),
                        xy(28000, 21000),
                        xy(25000, 24000),
                        xy(30000, 10000),
                        xy(25000, 14000),
                        xy(31000, 16000),
                        xy(5000, 12000),
                        xy(1000, 31000),
                        xy(2000, 24000),
                        xy(13000, 17000),
                        xy(1000, 28000),
                        xy(14000, 16000),
                        xy(26000, 26000),
                        xy(10000, 31000),
                        xy(12000, 4000),
                        xy(9000, 24000),
                        xy(28000, 29000),
                        xy(12000, 20000),
                        xy(13000, 11000),
                        xy(4000, 26000),
                        xy(8000, 10000),
                        xy(15000, 12000),
                        xy(22000, 29000),
                        xy(7000, 15000),
                        xy(10000, 4000),
                        xy(2000, 29000),
                        xy(17000, 17000),
                        xy(3000, 15000),
                        xy(4000, 29000),
                        xy(19000, 2000))
                        , singletonList(
                                line(xy(2000, 29000), xy(28000, 29000))
                        )),


                //horizontal5
                Arguments.of(asList(
                        xy(7453,14118),
                        xy(2682,14118),
                        xy(7821,14118),
                        xy(5067,14118),
                        xy(9972,4652),
                        xy(16307,4652),
                        xy(5766,4652),
                        xy(4750,4652),
                        xy(13291,7996),
                        xy(20547,7996),
                        xy(10411,7996),
                        xy(8934,7996),
                        xy(1888,7657),
                        xy(7599,7657),
                        xy(12772,7657),
                        xy(13832,7657),
                        xy(10375,12711),
                        xy(14226,12711),
                        xy(20385,12711),
                        xy(18177,12711)
                        )
                        , singletonList(
                                line(xy(2682, 14118), xy(7821, 14118))
                        )),


                //vertical5
                Arguments.of(asList(
                xy(14407,19953),
                xy(14407,17831),
                xy(14407,10367),
                xy(14407,17188),
                xy(15976,9945),
                xy(15976,3370),
                xy(15976,8933),
                xy(15976,4589),
                xy(2088,11500),
                xy(2088,16387),
                xy(2088,6070),
                xy(2088,7091),
                xy(5757,16647),
                xy(5757,20856),
                xy(5757,3426),
                xy(5757,13581),
                xy(8421,15144),
                xy(8421,11344),
                xy(8421,1829),
                xy(8421,18715)
                )
                        , singletonList(
                                line(xy(14407, 10367), xy(14407, 19953))
                        )),


                //inarrow
                Arguments.of(asList(
                xy(5000,0),
                xy(10000,0),
                xy(15000,0),
                xy(20000,0),
                xy(25000,0),
                xy(30000,0),

                xy(1234,5678),

                xy(10000,3100),
                xy(15000,6200),
                xy(20000,9300),
                xy(25000,12400),
                xy(30000,15700),

                xy(27000,7500),
                xy(26000,10000),
                xy(20000,25000),
                xy(19000,27500),
                xy(18000,30000),

                xy(0,0),
                xy(2300,4100),
                xy(4600,8200),
                xy(11500,20500),

                xy(5678,4321),

                xy(0,30000),
                xy(0,25000),
                xy(0,20000),
                xy(0,15000),
                xy(0,11000),
                xy(0,10000),
                xy(0,5000)
                             )
                        , singletonList(
                                line(xy(0, 0), xy(0, 30000))
                        ))

                );
    }


    private static LineSegment line(Point p1, Point p2) {
        return new LineSegment(p1, p2);
    }

    private static Point xy(int x, int y) {
        return new Point(x, y);
    }

    //needs merge sort with comparator implementation
/*
	@Test
	public void sort_invalids() {
		assertThatThrownBy(() -> FastCollinearPoints.MergeSort.sort(null, null))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void sort_singleton() {
		Point[] points = {xy(4,4)};
		Comparator<Point> comparator = Comparator.comparing(Point::toString);
		FastCollinearPoints.MergeSort.sort(points, comparator);

		assertThat(points[0]).isEqualByComparingTo(xy(4, 4));
	}

	@Test
	public void sort_twoElem() {
		Point[] points = {xy(4,4), xy(1,1)};
		Comparator<Point> comparator = Comparator.comparing(Point::toString);
		FastCollinearPoints.MergeSort.sort(points, comparator);

		assertThat(points[0]).isEqualByComparingTo(xy(1,1));
		assertThat(points[1]).isEqualByComparingTo(xy(4, 4));
	}

	@Test
	public void sort_3elem() {
		Point[] points = {xy(4,4), xy(1,1), xy(2,2)};
		Comparator<Point> comparator = Comparator.comparing(Point::toString);
		FastCollinearPoints.MergeSort.sort(points, comparator);

		assertThat(points[0]).isEqualByComparingTo(xy(1,1));
		assertThat(points[1]).isEqualByComparingTo(xy(2,2));
		assertThat(points[2]).isEqualByComparingTo(xy(4, 4));
	}

	@Test
	public void sort_10elem() {
		Point[] points = {
				xy(4,4), xy(1,1), xy(2,2), xy(2,2), xy(4,4),
				xy(8,8), xy(7,7), xy(9,9), xy(6,6), xy(0,0)
		};
		Comparator<Point> comparator = Comparator.comparing(Point::toString);
		FastCollinearPoints.MergeSort.sort(points, comparator);

		assertThat(points[0]).isEqualByComparingTo(xy(0,0));
		assertThat(points[1]).isEqualByComparingTo(xy(1,1));
		assertThat(points[2]).isEqualByComparingTo(xy(2, 2));
		assertThat(points[3]).isEqualByComparingTo(xy(2,2));
		assertThat(points[4]).isEqualByComparingTo(xy(4,4));
		assertThat(points[5]).isEqualByComparingTo(xy(4, 4));
		assertThat(points[6]).isEqualByComparingTo(xy(6,6));
		assertThat(points[7]).isEqualByComparingTo(xy(7,7));
		assertThat(points[8]).isEqualByComparingTo(xy(8, 8));
		assertThat(points[9]).isEqualByComparingTo(xy(9, 9));
	}
*/

}