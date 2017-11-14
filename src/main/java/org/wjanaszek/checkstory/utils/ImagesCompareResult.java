package org.wjanaszek.checkstory.utils;

public class ImagesCompareResult {

    private String content;
    private String imageType;

    public ImagesCompareResult() {
    }

    public ImagesCompareResult(String content, String imageType) {
        this.content = content;
        this.imageType = imageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
