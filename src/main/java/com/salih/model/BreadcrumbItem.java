package com.salih.model;

public class BreadcrumbItem {
    private String label;
    private String url;

    public BreadcrumbItem(String label, String url) {
        this.label = label;
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "BreadcrumbItem{" +
                "label='" + label + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
