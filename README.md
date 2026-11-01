# Lab 06 – Kiểm thử BiCardSource bằng stub (DesignBook, Exercise 5.11)

Bộ test cho `BiCardSource` đạt **100% branch coverage** với **5 test**, dùng stub tự viết để cô lập UUT.

## Cấu trúc
```
src/main/java/cards/   Suit, Rank, Card, CardSource, BiCardSource (đã sửa lỗi)
src/test/java/cards/   TestBiCardSource.java (JUnit 5, có StubCardSource)
original/              BiCardSource bản gốc của đề (có lỗi ngược điều kiện trong draw())
results/               coverage_summary.txt, mutation_experiment.txt, jacoco-html/
pom.xml                JUnit 5.10.2 + JaCoCo 0.8.12
```

## Chạy
```
mvn test
# báo cáo coverage: target/site/jacoco/index.html
```
Hoặc IntelliJ: chuột phải `TestBiCardSource` → *Run with Coverage*.

## Bộ test
| Test | Nhánh phủ |
|---|---|
| testDrawFromSource1WhenNotEmpty | draw(): if true |
| testDrawFromSource2WhenSource1Empty | draw(): if false |
| testIsEmptyWhenSource1NotEmpty | isEmpty(): vế 1 false |
| testIsEmptyWhenOnlySource1Empty | isEmpty(): vế 1 true, vế 2 false |
| testIsEmptyWhenBothEmpty | isEmpty(): vế 1 true, vế 2 true |

Hai test bổ sung theo lời giải mẫu (không tăng coverage): `testDrawWhenNeitherEmpty`, `testIsEmptyWhenNeitherEmpty` → bộ nộp có 7 test.

Kết quả JaCoCo: 8/8 dòng, 6/6 nhánh, 33/33 lệnh. Bỏ bất kỳ test nào thì còn 5/6 nhánh.

## Lưu ý
- Code gốc của đề có lỗi: `draw()` rút từ nguồn 2 khi nguồn 1 còn lá (ngược với comment). Hai test của `draw()` sẽ fail trên bản gốc; bản trong `src/main` đã sửa.
- Số liệu trong `results/` đo bằng JaCoCo 0.8.12 trên Java 21 với bộ chạy nhỏ thay JUnit 5 (môi trường làm bài không tải được JUnit). Hãy chạy lại bằng Maven/IntelliJ một lần để xác nhận.
