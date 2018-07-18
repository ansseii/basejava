package com.mysite.storage;

import com.mysite.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> list = new ArrayList<>();

    @Override
    protected Integer getSearchElement(final String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(final Object element) {
        return element != null;
    }

    @Override
    protected void toUpdate(final Object index, final Resume resume) {
        list.set((Integer) index, resume);
    }

    @Override
    protected void toSave(final Object index, final Resume resume) {
        list.add(resume);
    }

    @Override
    protected void toDelete(final Object index) {
        list.remove(((Integer) index).intValue());
    }

    @Override
    protected Resume toGet(final Object index) {
        return list.get((Integer) index);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected List<Resume> toList() {
        return new ArrayList<>(list);
    }

    @Override
    public int size() {
        return list.size();
    }
}