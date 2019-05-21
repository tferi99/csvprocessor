package org.ftoth.cvsproc.general;

import java.util.ArrayList;
import java.util.List;

public class CsvGroup<T>
{
    private Object id;
    private List<T> items = new ArrayList<T>();

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public List<T> getItems() {
        return items;
    }

    public void add(T item)
    {
        items.add(item);
    }

    @Override
    public String toString() {
        return "Group[" + id.toString() + "]";
    }
}
