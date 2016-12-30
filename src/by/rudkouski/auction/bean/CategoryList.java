package by.rudkouski.auction.bean;

import by.rudkouski.auction.bean.impl.Category;

import java.util.Iterator;
import java.util.List;

public class CategoryList {
    private List<Category> categoryList;
    private Iterator<Category> iterator;

    public CategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public int getSize() {
        return categoryList.size();
    }

    public String getNameEn(long categoryId) {
        for (Category category : categoryList) {
            if (category.getId() == categoryId) {
                return category.getNameEn();
            }
        }
        return null;
    }

    public String getNameBe(long categoryId) {
        for (Category category : categoryList) {
            if (category.getId() == categoryId) {
                return category.getNameBe();
            }
        }
        return null;
    }

    public Category getCategory() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    public void setupIterator() {
        iterator = categoryList.iterator();
    }
}
