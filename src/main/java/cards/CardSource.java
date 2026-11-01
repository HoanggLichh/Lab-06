package cards;

/** Nguồn cung cấp lá bài (ví dụ: bộ bài, xấp bài bỏ). */
public interface CardSource {

    /** Rút một lá. Chỉ được gọi khi isEmpty() là false. */
    Card draw();

    /** @return true nếu không còn lá nào để rút */
    boolean isEmpty();
}
