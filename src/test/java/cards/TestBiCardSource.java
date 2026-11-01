package cards;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Bộ test cho các phương thức public của BiCardSource (Exercise 5.11),
 * đạt 100% branch coverage với 5 test cốt lõi (+2 test bổ sung theo lời giải mẫu), dùng stub để cô lập BiCardSource.
 *
 * Số test tối thiểu:
 *  - draw():    2 test (một nhánh if cho true, một cho false; loại trừ nhau).
 *  - isEmpty(): 3 test (JaCoCo đếm mỗi vế của && là một điểm quyết định:
 *               s1 không rỗng; s1 rỗng + s2 không rỗng; s1 rỗng + s2 rỗng).
 */
public class TestBiCardSource {

	// ---------------------------------------------------------------- stub

	/**
	 * Stub của CardSource: isEmpty() trả về giá trị cố định, draw() trả về lá
	 * đã cấu hình. Nếu cấu hình là "không được rút" thì draw() ném lỗi, nhờ đó
	 * test phát hiện được khi BiCardSource rút nhầm nguồn.
	 */
	private static class StubCardSource implements CardSource {
		private final boolean aEmpty;
		private final Card aCard;
		private final boolean aDrawAllowed;

		StubCardSource(boolean pEmpty, Card pCard, boolean pDrawAllowed) {
			aEmpty = pEmpty;
			aCard = pCard;
			aDrawAllowed = pDrawAllowed;
		}

		/** Nguồn còn lá và sẽ trả về pCard khi rút. */
		static StubCardSource withCard(Card pCard) {
			return new StubCardSource(false, pCard, true);
		}

		/** Nguồn rỗng, rút thì báo lỗi (đúng hợp đồng của CardSource). */
		static StubCardSource empty() {
			return new StubCardSource(true, null, false);
		}

		/** Nguồn còn lá nhưng test khẳng định KHÔNG được rút từ nó. */
		static StubCardSource mustNotDraw() {
			return new StubCardSource(false, null, false);
		}

		@Override
		public Card draw() {
			if (!aDrawAllowed) {
				throw new AssertionError("Không được rút từ nguồn này");
			}
			return aCard;
		}

		@Override
		public boolean isEmpty() {
			return aEmpty;
		}
	}

	private static final Card CARD_1 = new Card(Rank.ACE, Suit.SPADES);
	private static final Card CARD_2 = new Card(Rank.KING, Suit.HEARTS);

	// ---------------------------------------------------------------- draw()

	/** Nhánh true của if: nguồn 1 còn lá thì rút từ nguồn 1, không đụng tới nguồn 2. */
	@Test
	public void testDrawFromSource1WhenNotEmpty() {
		BiCardSource source = new BiCardSource(StubCardSource.withCard(CARD_1), StubCardSource.mustNotDraw());
		assertSame(CARD_1, source.draw());
	}

	/** Nhánh false của if: nguồn 1 rỗng thì rút từ nguồn 2. */
	@Test
	public void testDrawFromSource2WhenSource1Empty() {
		BiCardSource source = new BiCardSource(StubCardSource.empty(), StubCardSource.withCard(CARD_2));
		assertSame(CARD_2, source.draw());
	}

	/** Bổ sung theo lời giải mẫu: cả hai nguồn còn lá thì vẫn rút từ nguồn 1 (không tăng thêm nhánh). */
	@Test
	public void testDrawWhenNeitherEmpty() {
		BiCardSource source = new BiCardSource(StubCardSource.withCard(CARD_1), StubCardSource.withCard(CARD_2));
		assertSame(CARD_1, source.draw());
	}

	// ------------------------------------------------------------- isEmpty()

	/** Vế đầu của && là false: kết quả false (nguồn 2 rỗng để bắt lỗi dùng || thay cho &&). */
	@Test
	public void testIsEmptyWhenSource1NotEmpty() {
		BiCardSource source = new BiCardSource(StubCardSource.withCard(CARD_1), StubCardSource.empty());
		assertFalse(source.isEmpty());
	}

	/** Vế đầu true, vế sau false: kết quả false. */
	@Test
	public void testIsEmptyWhenOnlySource1Empty() {
		BiCardSource source = new BiCardSource(StubCardSource.empty(), StubCardSource.withCard(CARD_2));
		assertFalse(source.isEmpty());
	}

	/** Cả hai vế true: kết quả true. */
	@Test
	public void testIsEmptyWhenBothEmpty() {
		BiCardSource source = new BiCardSource(StubCardSource.empty(), StubCardSource.empty());
		assertTrue(source.isEmpty());
	}

	/** Bổ sung theo lời giải mẫu: cả hai nguồn còn lá thì isEmpty() là false (không tăng thêm nhánh). */
	@Test
	public void testIsEmptyWhenNeitherEmpty() {
		BiCardSource source = new BiCardSource(StubCardSource.withCard(CARD_1), StubCardSource.withCard(CARD_2));
		assertFalse(source.isEmpty());
	}
}
