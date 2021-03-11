import java.util.Arrays;
import java.util.Comparator;

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
	private Segments segments;

	/**
	 * Finds all line segments containing 4 or more points
	 *
	 * @param inPoints
	 * @throws IllegalArgumentException if the argument to the constructor is null, if any point in the array is null,
	 * or if the argument to the constructor contains a repeated point.
	 */
	public FastCollinearPoints(Point[] inPoints) {
		if (inPoints == null) {
			throw new IllegalArgumentException();
		}
		points = new Points(inPoints);
		segments = new Segments();
		tryCalculateSegments();
	}

	private void tryCalculateSegments() {
		if (points.size() >= 4) {
			calculateLineSegments();
		}
	}

	private void calculateLineSegments() {
		for (int i = 0; i <= points.size() - 4; i++) {
			calculateLineSegmentsForPoint(i);
		}
	}

	private void calculateLineSegmentsForPoint(int i) {
		Point thePoint = points.get(i);
		Comparator<Point> comparator = thePoint.slopeOrder();
		Points pointsClone = points.cloneWithout(i);
		pointsClone.sort(comparator);
		calculateLineSegmentsFor(thePoint, pointsClone);
	}

	private void calculateLineSegmentsFor(Point thePoint, Points pointsClone) {
		int start = 0;
		int end = start + 2;
		while (start < pointsClone.oneBeforeLastIndex()) {
			if (pointsClone.isSlopeToPointNotEqual(thePoint, start, end)) {
				start++; end++;
				continue;
			}
			int lastGood = end;
			while (end < pointsClone.lastIndex() && pointsClone.isSlopeToPointEqual(thePoint, start, end)) {
				lastGood = end++;
			}
			segments.addSegment(thePoint, pointsClone.get(start), pointsClone.get(lastGood));
			start = lastGood + 1;
			end = start + 2;
		}
	}

	/**
	 * Number of line segments
	 * @return 	the number of line segments
	 */
	public int numberOfSegments() {
		return segments.size();
	}

	/**
	 * should include each maximal line segment containing 4 (or more) points exactly once.
	 * For example, if 5 points appear on a line segment in the order p→q→r→s→t, then do not include the subsegments p→s or q→t.
	 * @return the line segments
	 */
	public LineSegment[] segments() {
		return segments.getSegments();
	}


	/**
	 * Points collection
	 */
	private static class Points {
		private Point[] points;

		Points(Point[] inPoints) {
			points = inPoints;
			validate();
		}

		private Points() {
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

		public Points cloneWithout(int omitMe) {
			Points clone = new Points();
			int newLen = size() - (omitMe + 1);
			clone.points = new Point[newLen];
			System.arraycopy(points,omitMe+1, clone.points, 0, newLen);
			return clone;
		}

		public void sort(Comparator<Point> comparator) {
			MergeSort.sort(points,comparator);
		}

		private double findPointSlopeTo(Point thePoint, int point) {
			return thePoint.slopeTo(get(point));
		}

		private boolean isSlopeToPointNotEqual(Point thePoint, int start, int end) {
			return !isSlopeToPointEqual(thePoint, start, end);
		}

		private boolean isSlopeToPointEqual(Point thePoint, int start, int end) {
			return findPointSlopeTo(thePoint, start) == findPointSlopeTo(thePoint, end);
		}

		private int lastIndex() {
			return size() - 1;
		}

		private int oneBeforeLastIndex() {
			return size() - 2;
		}
	}

	private static class Segments {
		private LineSegment[] segments;
		private double[] slopes;
		private int segmentsSize = 0;

		private Segments() {
			segments = new LineSegment[10];
			slopes = new double[10];
		}

		public LineSegment[] getSegments() {
			if (segmentsSize == segments.length) {
				return segments;
			}
			LineSegment[] segmentsCopy = new LineSegment[segmentsSize];
			System.arraycopy(segments, 0, segmentsCopy,0, segmentsSize);
			return segmentsCopy;
		}

		public int size() {
			return segmentsSize;
		}

		public void addSegment(Point thePoint, Point firstPoint, Point lastPoint) {
			double slope = firstPoint.slopeTo(lastPoint);
			if (slopeExists(slope)) {
				return;
			}
			LineSegment lineSegment = createLineSegment(thePoint, firstPoint, lastPoint);
			addSegment(lineSegment, slope);
		}

		private boolean slopeExists(double slope) {
			for (int i = 0; i < segmentsSize; i++) {
				if (slopes[i] == slope) {
					return true;
				}
			}
			return false;
		}

		private LineSegment createLineSegment(Point thePoint, Point firstPoint, Point lastPoint) {
			if (firstPoint.compareTo(thePoint) < 0) {
				return new LineSegment(firstPoint, lastPoint);
			}
			return new LineSegment(thePoint, lastPoint);
		}

		private void addSegment(LineSegment lineSegment, double slope) {
			if (segmentsSize == segments.length) {
				LineSegment[] dest = new LineSegment[segments.length * 2];
				System.arraycopy(segments, 0, dest, 0, segmentsSize);
				segments = dest;
				double[] destSlopes = new double[slopes.length * 2];
				System.arraycopy(slopes, 0, destSlopes, 0, segmentsSize);
				slopes = destSlopes;
			}
			segments[segmentsSize] = lineSegment;
			slopes[segmentsSize] = slope;
			segmentsSize++;
		}
	}

	/**
	 * Merge sort
	 */
	private static class MergeSort {
		public static void sort(Point[] points, Comparator<Point> comparator) {
			if (points == null || comparator == null || points.length < 1) {
				throw new IllegalArgumentException();
			}
			Point[] aux = new Point[points.length];
			sort(points, aux, 0, points.length-1, comparator);
		}

		private static void sort(Point[] points, Point[] aux, int lo, int hi, Comparator<Point> comparator) {
			if (lo >= hi) {
				return;
			}
			int mid = lo + (hi - lo) / 2;
			sort(points, aux, lo, mid, comparator);
			sort(points, aux, mid+1, hi, comparator);
			System.arraycopy(points, lo, aux, lo, hi - lo + 1);
			merge(points, aux, lo, mid, hi, comparator);
		}

		private static void merge(Point[] points, Point[] aux, int lo, int mid, int hi, Comparator<Point> comparator) {
			int k = lo;
			int i = lo, j = mid+1;
			while (i <= mid && j <= hi) {
				if (comparator.compare(aux[i], aux[j]) <= 0) {
					points[k++] = aux[i++];
				} else {
					points[k++] = aux[j++];
				}
			}
			while (j <= hi) {
				points[k++] = aux[j++];
			}
			while (i <= mid) {
				points[k++] = aux[i++];
			}
		}

	}
}