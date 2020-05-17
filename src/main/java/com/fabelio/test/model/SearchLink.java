package com.fabelio.test.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SearchLink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String link;

    @Column(name = "submitted_date")
    private Date submittedDate;

    public SearchLink(String link, Date submittedDate) {
        this.link = link;
        this.submittedDate = submittedDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }
}
