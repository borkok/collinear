import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {

	@Test
	public void slopeTo() {
		Point testee = new Point(1, 1);
		Point other = new Point(3, 2);
		assertThat(testee.slopeTo(other)).isEqualTo(0.5d);
	}

	@Test
	public void slopeTo_horizontalLine() {
		Point testee = new Point(1, 1);
		Point other = new Point(2, 1);
		assertThat(testee.slopeTo(other)).isEqualTo(0.0d);
	}

	@Test
	public void slopeTo_verticalLine() {
		Point testee = new Point(1, 1);
		Point other = new Point(1, 2);
		assertThat(testee.slopeTo(other)).isEqualTo(Double.POSITIVE_INFINITY);
	}

	@Test
	public void slopeTo_samePoint() {
		Point testee = new Point(1, 1);
		Point other = new Point(1, 1);
		assertThat(testee.slopeTo(other)).isEqualTo(Double.NEGATIVE_INFINITY);
	}

	@ParameterizedTest
	@CsvSource({"1,1,1,1"})
	public void compareTo_whenEqual(int x0, int y0, int x1, int y1) {
		Point testee = new Point(x0, y0);
		Point other = new Point(x1, y1);
		assertThat(testee.compareTo(other)).isZero();
	}

	@ParameterizedTest
	@CsvSource({"1,1,1,2","1,1,0,2","1,1,2,2","1,1,2,1","-1,1,1,1","0,-2,-1,-1","0,-2,-1,1"})
	public void compareTo_whenLess(int x0, int y0, int x1, int y1) {
		Point testee = new Point(x0, y0);
		Point other = new Point(x1, y1);
		assertThat(testee.compareTo(other)).isNegative();
	}

	@ParameterizedTest
	@CsvSource({"1,2,1,1", "0,2,1,1", "2,2,1,1", "2,1,1,1","-3,-3,-2,-5","2,2,1,-2"})
	public void compareTo_whenGreater(int x0, int y0, int x1, int y1) {
		Point testee = new Point(x0, y0);
		Point other = new Point(x1, y1);
		assertThat(testee.compareTo(other)).isPositive();
	}
}
