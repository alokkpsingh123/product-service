package org.alok.projectservice.entity;


import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = Product.TABLE_NAME)
public class Product {
    public static final String TABLE_NAME = "PRODUCT";
    public static final String SEQ_GEN_ALIAS = "seq_gen_altas";
    public static final String SEQ_GEN_STRATEGY = "uuid2";

    @Id
    @GeneratedValue(generator = Category.SEQ_GEN_ALIAS)
    @GenericGenerator(name = Category.SEQ_GEN_ALIAS, strategy = Category.SEQ_GEN_STRATEGY)
    private String productId;
    private String productName;
    private String productDescription;
    private String productImageUrl;
    private String productBrand;


    @com.fasterxml.jackson.annotation.JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_name_fk", nullable = false)
    private Category category;

}
