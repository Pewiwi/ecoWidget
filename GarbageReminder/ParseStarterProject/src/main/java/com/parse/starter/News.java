package com.parse.starter;

/**
 * Created by TF4 on 09/02/2017.
 */

public class News {
    enum newsType{
        news,
        advertisement,
        notification,
    }
    String title;
    String subtitle;
    String content;
    String link;
    newsType type;

    public News(String title, String subtitle, String content, String link, newsType type){
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.link = link;
        this.type = type;
    }
}
