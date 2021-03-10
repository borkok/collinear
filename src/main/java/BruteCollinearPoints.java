import java.util.Arrays;

/**
 * Brute force. Write a program BruteCollinearPoints.java that examines 4 points at a time and checks whether they all lie on the same line
 * segment, returning all such line segments. To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes
 * between p and q, between p and r, and between p and s are all equal.
 *
 * The method segments() should include each line segment containing 4 points exactly once. If 4 points appear on a line segment in the
 * order p→q→r→s, then you should include either the line segment p→s or s→p (but not both) and you should not include subsegments
 * such as p→r or q→r. For simplicity, we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.
 *
 * Performance requirement. The order of growth of the running time of your program should be n4 in the worst case and it should use
 * space proportional to n plus the number of line segments returned.
 *
 */
public class BruteCollinearPoints {

	private final Points points;
	private LineSegments segments;

	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException();
		}
		this.points = new Points(points);
		calculateLineSegments();
	}

	private void calculateLineSegments() {
		segments = new LineSegments();
		if (points.size() < 4) {
			return;
		}
		//N4 complexity
		for (int i = 0; i < points.size(); i++) {
			for (int j = i+1; j < points.size(); j++) {
				for (int k = j+1; k < points.size(); k++) {
					for (int l = k+1; l < points.size(); l++) {
						segments.tryAdding(points.get(i), points.get(j), points.get(k), points.get(l));
					}
				}
			}
		}
	}

	private LineSegment line(Point p1, Point p2) {
		return new LineSegment(p1, p2);
	}

	// the number of line segments
	public int numberOfSegments() {
		return segments.getSize();
	}

	// the line segments
	public LineSegment[] segments() {
		return segments.getSegments();
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

	/**
	 * Line segments collection
	 */
	private static class LineSegments {
		private LineSegment[] segments = new LineSegment[10];
		private int size = 0;

		public LineSegment[] getSegments() {
			return copySegments();
		}

		public int getSize() {
			return size;
		}

		private LineSegment[] copySegments() {
			LineSegment[] lineSegments = new LineSegment[size];
			for (int i = 0; i < size; i++) {
				lineSegments[i] = segments[i];
			}
			return lineSegments;
		}

		public void tryAdding(Point p, Point q, Point r, Point s) {
			if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
				add(new LineSegment(p,s));
			}
		}

		private void add(LineSegment lineSegment) {
			if (segments.length == size) {
				increaseSize();
			}
			segments[size++] = lineSegment;
		}

		private void increaseSize() {
			LineSegment[] lineSegments = new LineSegment[size * 2];
			for (int i = 0; i < segments.length; i++) {
				lineSegments[i] = segments[i];
			}
			segments = lineSegments;
		}
	}
}