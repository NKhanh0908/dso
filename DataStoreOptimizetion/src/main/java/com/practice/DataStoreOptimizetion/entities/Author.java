package com.practice.DataStoreOptimizetion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable { // Marker Interface (giao diện đánh dấu). biến đối tượng Author này thành một chuỗi các byte (0101...). phục vụ việc cache
    @Serial
    private static final long serialVersionUID = 1L; // Đánh dấu đây không phải là đối tượng mới khi thêm bớt atttribute,
    // dùng trong HttpSession

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;
    private Integer age;

    private String gender;

    @OneToMany(mappedBy = "author",
            cascade = CascadeType.ALL,// Thiet lap mapperBy o phia cha, dam bao dung ve mat ngu nghia khi co cha thi moi co con
            orphanRemoval = true)
    private List<Book> books;

    // Giữ Cho Cả Hai Phía Của Mối Quan Hệ Được Đồng Bộ

    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        book.setAuthor(null);
        this.books.remove(book);
    }

    // Trong Hibernate/JPA để xóa sạch danh sách sử dụng iterator thay vì for-each vì trong qusa trình duyệt nó k thể xóa phần tử của nó. đảm bảo sạch
    public void removeBooks() {
        Iterator<Book> iterator = this.books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            book.setAuthor(null);
            iterator.remove();
        }
    }

    // Chỉ toString thuộc tính, nếu không Lazy sẽ kích hoạt tiến hành truy vấn tải dữ liệu.
    @Override
    public String toString() {
        return "Author{" + "id=" + id + ", name=" + name
                + ", genre=" + gender + ", age=" + age + '}';
    }
}
