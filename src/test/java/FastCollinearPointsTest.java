import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FastCollinearPointsTest {
	@Test
	public void init_invalidInputs() {
		assertThatThrownBy(() -> new FastCollinearPoints(null))
				.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new FastCollinearPoints(new Point[]{xy(1,1), null}))
				.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new FastCollinearPoints(new Point[]{xy(1,1), xy(1,1)}))
				.isInstanceOf(IllegalArgumentException.class);
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

	private static Point xy(int x, int y) {
		return new Point(x, y);
	}
}