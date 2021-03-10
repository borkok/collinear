import java.util.Arrays;

/**
 * Remarkably, it is possible to solve the problem much faster than the brute-force solution described above. Given a point p,
 * the following method determines whether p participates in a set of 4 or more collinear points.
 *
 * Think of p as the origin.
 * For each other point q, determine the slope it makes with p.
 * Sort the points according to the slopes they makes with p.
 * Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points,
 * together with p, are collinear.
 *
 * Applying this method for each of the n points in turn yields an efficient algorithm to the problem. The algorithm solves the problem
 * because points that have equal slopes with respect to p are collinear, and sorting brings such points together.
 * The algorithm is fast because the bottleneck operation is sorting.
 *
 * Performance requirement. The order of growth of the running time of your program should be n2 log n in the worst case and it should
 * use space proportional to n plus the number of line segments returned. FastCollinearPoints should work properly even
 * if the input has 5 or more collinear points.
 */
public class FastCollinearPoints {
	private final Points points;

	/**
	 * Finds all line segments containing 4 or more points
	 *
	 * @param points
	 * @throws IllegalArgumentException if the argument to the constructor is null, if any point in the array is null,
	 * or if the argument to the constructor contains a repeated point.
	 */
	public FastCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException();
		}
		this.points = new Points(points);
	}

	/**
	 * Number of line segments
	 * @return 	the number of line segments
	 */
	public int numberOfSegments() {
		return 0;
	}

	/**
	 * should include each maximal line segment containing 4 (or more) points exactly once.
	 * For example, if 5 points appear on a line segment in the order p→q→r→s→t, then do not include the subsegments p→s or q→t.
	 * @return the line segments
	 */
	public LineSegment[] segments() {
		return null;
	}


	/**
	 * Points collection
	 */
	private static class Points {
		private final Point[] points;

		Points(Point[] inPoints) {
			points = inPoints;
			validate();
		}

		private void validate() {
			sort();
			for (int i = 0; i < points.length; i++) {
				if (i == 0) {
					continue;
				}
				checkAllPointsAreDistinct(i);
			}
		}

		private void sort() {
			try {
				Arrays.sort(points);
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		}

		private void checkAllPointsAreDistinct(int i) {
			Point current = points[i];
			Point previous = points[i - 1];
			if (previous.compareTo(current) == 0) {
				throw new IllegalArgumentException();
			}
		}

		public int size() {
			return points.length;
		}

		public Point get(int i) {
			return points[i];
		}
	}
}