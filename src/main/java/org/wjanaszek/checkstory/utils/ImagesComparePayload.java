package org.wjanaszek.checkstory.utils;

public class ImagesComparePayload {

    private Long originalImageId;
    private Long modifiedImageId;
    private Boolean resize;
    private Boolean boundingRectangles;

    public ImagesComparePayload() {
    }

    public ImagesComparePayload(Long originalImageId, Long modifiedImageId, Boolean resize, Boolean boundingRectangles) {
        this.originalImageId = originalImageId;
        this.modifiedImageId = modifiedImageId;
        this.resize = resize;
        this.boundingRectangles = boundingRectangles;
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

    public Boolean getResize() {
        return resize;
    }

    public void setResize(Boolean resize) {
        this.resize = resize;
    }

    public Boolean getBoundingRectangles() {
        return boundingRectangles;
    }

    public void setBoundingRectangles(Boolean boundingRectangles) {
        this.boundingRectangles = boundingRectangles;
    }
}
