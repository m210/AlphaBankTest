package ru.m210projects.alphabanktest.entity;

public class GifData {

    private String url;
    private byte[] data;
    private int size;
    public String getUrl() {
        return url;
    }

    public GifData setUrl(String url) {
        this.url = url;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public GifData setData(byte[] data) {
        this.data = data;
        return this;
    }

    public int getSize() {
        return size;
    }

    public GifData setSize(int size) {
        this.size = size;
        return this;
    }
}
