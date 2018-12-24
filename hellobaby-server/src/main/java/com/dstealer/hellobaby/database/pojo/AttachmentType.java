package com.dstealer.hellobaby.database.pojo;

/**
 * Created by LiShiwu on 03/01/2017.
 */
public enum AttachmentType {
    CSV(".csv"), XLSX(".xlsx");
    private String suffix;

    AttachmentType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}
