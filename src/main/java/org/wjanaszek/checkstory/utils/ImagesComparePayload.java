package org.wjanaszek.checkstory.utils;

public class ImagesComparePayload {

    private Long originalImageId;
    private Long modifiedImageId;

    public ImagesComparePayload() {
    }

    public ImagesComparePayload(Long originalImageId, Long modifiedImageId) {
        this.originalImageId = originalImageId;
        this.modifiedImageId = modifiedImageId;
    }

    public Long getOriginalImageId() {
        return originalImageId;
    }

    public void setOriginalImageId(Long originalImageId) {
        this.originalImageId = originalImageId;
    }

    public Long getModifiedImageId() {
        return modifiedImageId;
    }

    public void setModifiedImageId(Long modifiedImageId) {
        this.modifiedImageId = modifiedImageId;
    }
}
