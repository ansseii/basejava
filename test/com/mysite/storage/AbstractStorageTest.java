package com.mysite.storage;

import com.mysite.exception.ExistStorageException;
import com.mysite.exception.NotExistStorageException;
import com.mysite.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume resume1 = new Resume(UUID_1, "Ivanov Ivan Ivanovich");
    private static final String UUID_2 = "uuid2";
    private static final Resume resume2 = new Resume(UUID_2, "Petrov Petr Petrovich");
    private static final String UUID_3 = "uuid3";
    private static final Resume resume3 = new Resume(UUID_3, "Ivanov Ivan Ivanovich");
    private static final String UUID_4 = "uuid4";
    private static final Resume resume4 = new Resume(UUID_4, "Sidorov Andrei Andreevich");

    private static final String UUID_5 = "uuid5";
    private static final Resume resume5 = new Resume(UUID_5, "Sidorov Andrei Andreevich");

    public AbstractStorageTest(final Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
        storage.save(resume4);
    }

    @Test
    public void size() {
        sizeForTests(4);
    }

    @Test
    public void clear() {
        storage.clear();
        sizeForTests(0);
    }

    @Test
    public void update() {
        storage.update(resume1);
        Assert.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateIfResumeNotExist() {
        storage.get(UUID_5);
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = storage.getAllSorted();
        List< Resume> expectedResumes = Arrays.asList(resume1, resume2, resume3, resume4);
        Collections.sort(expectedResumes);
        Assert.assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void save() {
        storage.save(resume5);
        sizeForTests(5);
        Assert.assertEquals(resume5, storage.get(UUID_5));
    }

    @Test(expected = ExistStorageException.class)
    public void saveIfResumeAlreadyExist() {
        storage.save(resume4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        sizeForTests(3);
        storage.get(UUID_2);
    }

    @Test
    public void get() {
        Assert.assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getIfResumeNotExist() {
        storage.get(UUID_5);
    }

    private void sizeForTests(final int size) {
        Assert.assertEquals(size, storage.size());
    }
}