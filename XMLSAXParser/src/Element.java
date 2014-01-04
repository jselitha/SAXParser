//Group 20 CS 122b
//Jesse Selitham 62698383
//David Phan 55492115

import java.util.ArrayList;
import java.util.List;

public class Element
{
    private String documentType;

    private String key;
    private String mdate;
    private String reviewId;
    private String rating;

    private List<String> authors;
    private String editor;
    private String title;
    private String bookTitle;
    private String publisher;
    private String publisherHref;
    private String year;
    private String isbn;
    private String url;

    private String pages;
    private String address;
    private String journal;
    private String volume;
    private String number;
    private String ee;
    private String cdrom;
    private String cite;
    private String crossref;
    private String series;

    public Element(String documentType)
    {
        this.documentType = documentType;
        authors = new ArrayList<String>();
    }

    public String getDocumentType()
    {
        return documentType;
    }

    public String getVolume()
    {
        return volume;
    }

    public void setVolume(String volume)
    {
        this.volume = volume;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getEe()
    {
        return ee;
    }

    public void setEe(String ee)
    {
        this.ee = ee;
    }

    public String getCdrom()
    {
        return cdrom;
    }

    public void setCdrom(String cdrom)
    {
        this.cdrom = cdrom;
    }

    public String getCrossref()
    {
        return crossref;
    }

    public void setCrossref(String crossref)
    {
        this.crossref = crossref;
    }

    public String getSeries()
    {
        return series;
    }

    public void setSeries(String series)
    {
        this.series = series;
    }

    public String getCite()
    {
        return cite;
    }

    public void setCite(String cite)
    {
        this.cite = cite;
    }

    public String getJournal()
    {
        return journal;
    }

    public void setJournal(String journal)
    {
        this.journal = journal;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPages()
    {
        return pages;
    }

    public void setPages(String pages)
    {
        this.pages = pages;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public String getReviewId()
    {
        return reviewId;
    }

    public void setReviewId(String reviewId)
    {
        this.reviewId = reviewId;
    }

    public List<String> getAuthor()
    {
        return authors;
    }

    public void addAuthor(String author)
    {
        authors.add(author);
    }

    public String getEditor()
    {
        return editor;
    }

    public void setEditor(String editor)
    {
        this.editor = editor;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getBookTitle()
    {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle)
    {
        this.bookTitle = bookTitle;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getMdate()
    {
        return mdate;
    }

    public void setMdate(String mdate)
    {
        this.mdate = mdate;
    }

    public String getPublisherHref()
    {
        return publisherHref;
    }

    public void setPublisherHref(String publisherHref)
    {
        this.publisherHref = publisherHref;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(documentType + " Details - ");
        for (int i = 0; i < authors.size(); i++)
        {
            sb.append("Author: " + authors.get(i));
            sb.append(", ");
        }

        if (editor != null)
        {
            sb.append("Editor: " + getEditor());
            sb.append(", ");
        }

        if (title != null)
        {
            sb.append("Title: " + getTitle());
            sb.append(", ");
        }
        if (publisher != null)
        {
            sb.append("Publisher: " + getPublisher());
            sb.append(", ");
        }
        if (year != null)
        {
            sb.append("Year: " + getYear());
            sb.append(", ");
        }
        if (isbn != null)
        {
            sb.append("ISBN: " + getIsbn());
            sb.append(", ");
        }
        if (url != null)
        {
            sb.append("URL: " + getUrl());
            sb.append(".");
        }

        return sb.toString();
    }
}
