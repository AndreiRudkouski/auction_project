package by.rudkouski.auction.entity;

import java.util.Iterator;
import java.util.List;

public class EntityListTag<T extends AbstractEntity> {
    private List<T> listTag;
    private Iterator<T> iterator;

    public EntityListTag(List<T> listTag) {
        this.listTag = listTag;
        if (listTag != null) {
            iterator = listTag.iterator();
        }
    }

    public int getSize() {
        return listTag.size();
    }

    public T getElement() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }
}
