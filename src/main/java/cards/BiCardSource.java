package cards;

/**
 * Ghép hai CardSource: rút từ nguồn 1 nếu nó còn lá, ngược lại rút từ nguồn 2.
 *
 * Đây là bản đã SỬA lỗi so với đề bài: bản gốc (thư mục original/) dùng
 * "if (!aSource1.isEmpty()) return aSource2.draw();" tức là ngược với comment.
 */
class BiCardSource implements CardSource {

	private final CardSource aSource1;
	private final CardSource aSource2;

	public BiCardSource(CardSource pSource1, CardSource pSource2) {
		aSource1 = pSource1;
		aSource2 = pSource2;
	}

	/*
	 * Draws from source 1 if not empty, and from source 2 if source 1 is empty.
	 */
	@Override
	public Card draw() {
		if (!aSource1.isEmpty()) {
			return aSource1.draw();
		}
		else {
			return aSource2.draw();
		}
	}

	@Override
	public boolean isEmpty() {
		return aSource1.isEmpty() && aSource2.isEmpty();
	}
}
