package com.youle.gamebox.ui.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table GAME_BEAN.
 */
public class GameBean {

    private Long id;
    private String name;
    private String iconUrl;
    private String downloads;
    private String size;
    private String packageName;
    private String version;
    private String explain;
    private String category;
    private String downloadUrl;
    private String downloadPath;
    private Integer score;
    private Integer source;
    private Integer status;
    private Boolean hasSpree;
    private Integer downloadStatus;
    private Long totalSize;
    private Long currentSize;
    public GameBean() {
    }

    public GameBean(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public GameBean(Long id, String name, String iconUrl, String downloads, String size, String packageName, String version, String explain, String category, String downloadUrl, String downloadPath, Integer score, Integer source, Integer status, Boolean hasSpree, Integer downloadStatus, Long totalSize, Long currentSize) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.downloads = downloads;
        this.size = size;
        this.packageName = packageName;
        this.version = version;
        this.explain = explain;
        this.category = category;
        this.downloadUrl = downloadUrl;
        this.downloadPath = downloadPath;
        this.score = score;
        this.source = source;
        this.status = status;
        this.hasSpree = hasSpree;
        this.downloadStatus = downloadStatus;
        this.totalSize = totalSize;
        this.currentSize = currentSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getHasSpree() {
        return hasSpree;
    }

    public void setHasSpree(Boolean hasSpree) {
        this.hasSpree = hasSpree;
    }

    public Integer getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(Integer downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(Long currentSize) {
        this.currentSize = currentSize;
    }

}