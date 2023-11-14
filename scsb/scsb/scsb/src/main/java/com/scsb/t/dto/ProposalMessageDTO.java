package com.scsb.t.dto;

import lombok.Data;

@Data
public class ProposalMessageDTO {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    // Getters, setters, and default constructor

    public ProposalMessageDTO() {
    }

    @Override
    public String toString() {
        return "ProposalMessageDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
