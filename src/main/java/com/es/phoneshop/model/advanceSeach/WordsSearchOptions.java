package com.es.phoneshop.model.advanceSeach;

public enum WordsSearchOptions {

    ALL("All words"), ANY("Any words");

    private String frontName;

    WordsSearchOptions(String frontName) {
        this.frontName = frontName;
    }

    public String getFrontName() {
        return frontName;
    }

    public void setFrontName(String frontName) {
        this.frontName = frontName;
    }

}
