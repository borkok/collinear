import org.junit.jupiter.api.Test;

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
}