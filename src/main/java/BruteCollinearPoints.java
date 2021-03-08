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

	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException();
		}
		this.points = new Points(points);
	}

	// the number of line segments
	public int numberOfSegments() {
		return 0;
	}

	// the line segments
	public LineSegment[] segments() {
		return null;
	}

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
	}
}