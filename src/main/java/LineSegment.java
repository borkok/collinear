import java.util.Objects;

/*************************************************************************
 *  Compilation:  javac LineSegment.java
 *  Execution:    none
 *  Dependencies: Point.java
 *
 *  An immutable data type for Line segments in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 *  DO NOT MODIFY THIS CODE.
 *
 *************************************************************************/

public class LineSegment {
	private final Point p;   // one endpoint of this line segment
	private final Point q;   // the other endpoint of this line segment

	/**
	 * Initializes a new line segment.
	 *
	 * @param  p one endpoint
	 * @param  q the other endpoint
	 * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
	 *         is <tt>null</tt>
	 */
	public LineSegment(Point p, Point q) {
		if (p == null || q == null) {
			throw new NullPointerException("argument is null");
		}
		this.p = p;
		this.q = q;
	}


	/**
	 * Draws this line segment to standard draw.
	 */
	public void draw() {
		p.drawTo(q);
	}

	/**
	 * Returns a string representation of this line segment
	 * This method is provide for debugging;
	 * your program should not rely on the format of the string representation.
	 *
	 * @return a string representation of this line segment
	 */
	public String toString() {
		return p + " -> " + q;
	}

	public int hashCode() {
		return Objects.hash(p.toString(), q.toString());
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LineSegment that = (LineSegment) o;
		return p.compareTo(that.p) == 0 &&
				q.compareTo(that.q) == 0;
	}
}