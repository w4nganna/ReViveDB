package com.example.cms.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "retailers")
@NoArgsConstructor
@Getter
@Setter
public class Retailer extends Person {
    private String lat;
    private String lng;
    private String logo;
    private String banner;
    private String description;

    @OneToMany
    private List<Product> products = new ArrayList<>();
}
