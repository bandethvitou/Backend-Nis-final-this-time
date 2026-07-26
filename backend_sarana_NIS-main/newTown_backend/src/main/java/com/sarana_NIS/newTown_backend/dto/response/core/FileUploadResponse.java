package com.sarana_NIS.newTown_backend.dto.response.core;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileUploadResponse {

    private String fileName;
    private String fileUrl;
    private String thumbnailUrl;
    private Long fileSize;
    private String fileType;
    private String uploadStatus;
    private String message;
    private String publicId;

    // Constructors
    public FileUploadResponse() {}

    public FileUploadResponse(String fileName, String fileUrl, String uploadStatus) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.uploadStatus = uploadStatus;
    }

    // Getters and Setters
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public String getUploadStatus() { return uploadStatus; }
    public void setUploadStatus(String uploadStatus) { this.uploadStatus = uploadStatus; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPublicId() { return publicId; }
    public void setPublicId(String publicId) { this.publicId = publicId; }
}