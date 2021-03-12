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
	private final Segments segments;

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
		MyPoint thePoint = points.get(i);
		Points pointsSubset = points.subsetFromIncluding(i+1);
		pointsSubset.sort(thePoint.comparator());
		calculateLineSegmentsFor(thePoint, pointsSubset);
	}

	private void calculateLineSegmentsFor(MyPoint thePoint, Points pointsToCheck) {
		int start = 0;
		int end = start + 2;
		while (start < pointsToCheck.oneBeforeLastIndex()) {
			if (pointsToCheck.isSlopeToPointNotEqual(thePoint, start, end)) {
				start++; end++;
				continue;
			}
			int lastGood = end;
			while (end <= pointsToCheck.lastIndex() && pointsToCheck.isSlopeToPointEqual(thePoint, start, end)) {
				lastGood = end++;
			}
			segments.addSegment(thePoint, pointsToCheck.getAllBetween(start, lastGood));
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


	/****************************************************************************
	 * Points collection
	 ****************************************************************************/
	private static class Points {
		private MyPoint[] myPointsArray;

		private Points(Point[] inPoints) {
			Point[] pointsArray = new PointsValidator(inPoints).validate();
			myPointsArray = new MyPoint[pointsArray.length];
			for (int i = 0; i < pointsArray.length; i++) {
				myPointsArray[i] = new MyPoint(pointsArray[i]);
			}
		}

		private Points() {
		}

		private int size() {
			return myPointsArray.length;
		}

		private MyPoint get(int i) {
			return myPointsArray[i];
		}

		private Points subsetFromIncluding(int from) {
			Points clone = new Points();
			int newLen = size() - from;
			clone.myPointsArray = new MyPoint[newLen];
			System.arraycopy(myPointsArray, from, clone.myPointsArray, 0, newLen);
			return clone;
		}

		private void sort(Comparator<MyPoint> comparator) {
			MergeSort.sort(myPointsArray, comparator);
		}

		private boolean isSlopeToPointNotEqual(MyPoint thePoint, int start, int end) {
			return !isSlopeToPointEqual(thePoint, start, end);
		}

		private boolean isSlopeToPointEqual(MyPoint thePoint, int start, int end) {
			return findPointSlopeTo(thePoint, start) == findPointSlopeTo(thePoint, end);
		}

		private double findPointSlopeTo(MyPoint thePoint, int point) {
			return thePoint.slopeTo(get(point));
		}

		private int lastIndex() {
			return size() - 1;
		}

		private int oneBeforeLastIndex() {
			return size() - 2;
		}

		public MyPoint[] getAllBetween(int start, int lastGood) {
			MyPoint[] result = new MyPoint[lastGood - start + 1];
			for (int i = start, j = 0; i <= lastGood; i++) {
				result[j++] = myPointsArray[i];
			}
			return result;
		}
	}

	private static class PointsValidator {
		private final Point[] pointsArray;

		private PointsValidator(Point[] inPoints) {
			pointsArray = new Point[inPoints.length];
			System.arraycopy(inPoints, 0, pointsArray, 0, inPoints.length);
		}

		private Point[] validate() {
			sort();
			for (int i = 0; i < pointsArray.length; i++) {
				if (pointsArray[i] == null) {
					throw new IllegalArgumentException();
				}
				if (i == 0) {
					continue;
				}
				checkAllPointsAreDistinct(i);
			}
			return pointsArray;
		}

		private void sort() {
			try {
				Arrays.sort(pointsArray);
			}
			catch (NullPointerException e) {
				throw new IllegalArgumentException();
			}
		}

		private void checkAllPointsAreDistinct(int i) {
			Point current = pointsArray[i];
			Point previous = pointsArray[i - 1];
			if (previous.compareTo(current) == 0) {
				throw new IllegalArgumentException();
			}
		}
	}

	/**
	 * Point with info about segments that contain this point
	 */
	private static class MyPoint {
		private final Point point;
		private Point[] lastPointsInMySegments = new Point[1];
		private int lastPointsSize = 0;

		private MyPoint(Point point) {
			this.point = point;
		}

		/**
		 * Adds info about segment that contains this point.
		 * @param lastPoint
		 * @return true if segment already existed in collection and was not added, false if segment added
		 */
		private boolean addNewSegment(Point lastPoint) {
			for (int i = 0; i < lastPointsSize; i++) {
				if (lastPointsInMySegments[i].compareTo(lastPoint) == 0) {
					return true;
				}
			}
			if (lastPointsSize == lastPointsInMySegments.length) {
				Point[] dest = new Point[lastPointsInMySegments.length * 2];
				System.arraycopy(lastPointsInMySegments, 0, dest, 0, lastPointsSize);
				lastPointsInMySegments = dest;
			}
			lastPointsInMySegments[lastPointsSize++] = lastPoint;
			return false;
		}

		private Comparator<MyPoint> comparator() {
			return (a,b) -> point.slopeOrder().compare(a.point, b.point);
		}

		private double slopeTo(MyPoint thatPoint) {
			return this.point.slopeTo(thatPoint.point);
		}

		public String toString() {
			return point.toString();
		}
	}

	/****************************************************************************
	 * Segments collections
	 *****************************************************************************/
	private static class Segments {
		private LineSegment[] segmentsArray;
		private int segmentsSize = 0;

		private Segments() {
			segmentsArray = new LineSegment[10];
		}

		private LineSegment[] getSegments() {
			if (segmentsSize == segmentsArray.length) {
				return segmentsArray;
			}
			LineSegment[] segmentsCopy = new LineSegment[segmentsSize];
			System.arraycopy(segmentsArray, 0, segmentsCopy,0, segmentsSize);
			return segmentsCopy;
		}

		private int size() {
			return segmentsSize;
		}

		private void addSegment(MyPoint firstPoint, MyPoint[] segmentPoints) {
			MyPoint lastPoint = segmentPoints[segmentPoints.length-1];
			if(firstPoint.addNewSegment(lastPoint.point)) {
				return;
			}
			for (int i = 0; i < segmentPoints.length-1; i++) {
				if(segmentPoints[i].addNewSegment(lastPoint.point)) {
					return;
				}
			}
			LineSegment lineSegment = new LineSegment(firstPoint.point, lastPoint.point);
			addSegment(lineSegment);
		}

		private void addSegment(LineSegment lineSegment) {
			if (segmentsSize == segmentsArray.length) {
				LineSegment[] dest = new LineSegment[segmentsArray.length * 2];
				System.arraycopy(segmentsArray, 0, dest, 0, segmentsSize);
				segmentsArray = dest;
			}
			segmentsArray[segmentsSize++] = lineSegment;
		}
	}

	/****************************************************************************
	 * Merge Sort
	 *****************************************************************************/
	private static class MergeSort {
		private static void sort(MyPoint[] points, Comparator<MyPoint> comparator) {
			if (points == null || comparator == null || points.length < 1) {
				throw new IllegalArgumentException();
			}
			MyPoint[] aux = new MyPoint[points.length];
			sort(points, aux, 0, points.length-1, comparator);
		}

		private static void sort(MyPoint[] points, MyPoint[] aux, int lo, int hi, Comparator<MyPoint> comparator) {
			if (lo >= hi) {
				return;
			}
			int mid = lo + (hi - lo) / 2;
			sort(points, aux, lo, mid, comparator);
			sort(points, aux, mid+1, hi, comparator);
			System.arraycopy(points, lo, aux, lo, hi - lo + 1);
			merge(points, aux, lo, mid, hi, comparator);
		}

		private static void merge(MyPoint[] points, MyPoint[] aux, int lo, int mid, int hi, Comparator<MyPoint> comparator) {
			int k = lo;
			int i = lo;
			int j = mid+1;
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