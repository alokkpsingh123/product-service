package org.alok.projectservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = Category.TABLE_NAME)
public class Category {

    public static final String TABLE_NAME = "CATEGORY";
    public static final String SEQ_GEN_ALIAS = "seq_gen_altas";
    public static final String SEQ_GEN_STRATEGY = "uuid2";

    @Id
    private String categoryName;
    private int categoryOffer;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> productList = new ArrayList<>();

}

