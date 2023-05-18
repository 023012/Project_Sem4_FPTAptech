package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String title;
    private String subject;
    private String publisher;
    private String language;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String author;
    private int amount;
    private int price;
    private int borrowPrice;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private Date createdAt;
    private Date updatedAt;
    private Date publishedAt;

    @ManyToOne(
            //cascade = CascadeType.
    )
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "categoryId"
    )
    private Category category;

    public enum BookStatus {
        OUT_OF_STOCK,
        AVAILABLE,
        UNAVAILABLE,
        UPDATING,
        HOT
    }

    /* Status:
    * HẾT HÀNG,
         CÓ SẴN,
         KHÔNG CÓ SẴN,
         CẬP NHẬT,
         NÓNG
    * */
}
