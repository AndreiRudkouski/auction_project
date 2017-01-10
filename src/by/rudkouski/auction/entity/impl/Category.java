package by.rudkouski.auction.entity.impl;

import by.rudkouski.auction.entity.AbstractEntity;

public class Category extends AbstractEntity {
    private String nameEn;
    private String nameBe;

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameBe() {
        return nameBe;
    }

    public void setNameBe(String nameBe) {
        this.nameBe = nameBe;
    }
}
