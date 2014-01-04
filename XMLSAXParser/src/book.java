
public class book
{
    private String key;
    private String mdate;

    
    private String author;
    private String editor;
    private String title;
    private String bookTitle;
    private String publisher;
        private String publisherHref;
    private String year;
    private String isbn;
    private String url;
    

    
    public book(){
    }
    
    public String getAuthor(){return author;}    
    public void setAuthor(String author){this.author = author;}
    
    public String getEditor(){return editor;}
    public void setEditor(String editor){this.editor  = editor;}
    
    public String getTitle(){return title;}    
    public void setTitle(String title){this.title  = title;}
    
    public String getPublisher(){return publisher;}  
    public void setPublisher(String publisher){this.publisher  = publisher;}
    
    public String getYear(){return year;}
    public void setYear(String year){this.year  = year;}
    
    public String getIsbn(){return isbn;}
    public void setIsbn(String isbn){this.isbn  = isbn;}
    
    public String getUrl(){return url;}
    public void setUrl(String url){this.url  = url;}
     
    public String getBookTitle(){return bookTitle;}
    public void setBookTitle(String bookTitle){this.bookTitle  = bookTitle;}
    
    public String getKey() {return key;}
    public void setKey(String key) {this.key = key;}  
    
    public String getMdate() {return mdate;}
    public void setMdate(String mdate) {this.mdate = mdate;}
    
    public String getPublisherHref() {return publisherHref;}
    public void setPublisherHref(String publisherHref) {this.publisherHref = publisherHref;}
    
    public String toString() 
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Book Details - ");
        sb.append("Editor: " + getEditor());
        sb.append(", ");
        sb.append("Title: " + getTitle());
        sb.append(", ");
        sb.append("Publisher: " + getPublisher());
        sb.append(", ");
        sb.append("Year: " + getYear());
        sb.append(", ");
        sb.append("ISBN: " + getIsbn());
        sb.append(", ");
        sb.append("URL: " + getUrl());
        sb.append(".");
        
        return sb.toString();
    }
}
