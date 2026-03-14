package com.practice.DataStoreOptimizetion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY) //
    @JoinColumn(name = "author_id")
    private Author author;

    // Giúp việc so sánh 2 cuốn sách mới có id là null không được xem là giống nhau
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id != null && id.equals(((Book) obj).id);
    }

    // Tìm hiểu Java HashCode Contract
    @Override
    public int hashCode() {
        return 2021;
    }

}
