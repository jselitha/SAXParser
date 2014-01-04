//Group 20 CS 122b
//Jesse Selitham 62698383
//David Phan 55492115


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.sql.*;

public class XMLSAXParser extends DefaultHandler
{
    List<Element> myDocs;

    private String tempVal;

    private Element tempElement;

    public XMLSAXParser()
    {
        myDocs = new ArrayList<Element>();
    }

    public void runExample() throws InstantiationException,
            IllegalAccessException, ClassNotFoundException, SQLException
    {
        long startTime = System.nanoTime();
        parseDocument();
        populate();
        long elapsedTime = (System.nanoTime() - startTime) / 1000000000;
        System.out.format("Elapsed time: %,d seconds", elapsedTime);
    }

    private void parseDocument()
    {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try
        {
            SAXParser sp = spf.newSAXParser();
            sp.parse("small.xml", this);

        } catch (SAXException se)
        {
            se.printStackTrace();
        } catch (ParserConfigurationException pce)
        {
            pce.printStackTrace();
        } catch (IOException ie)
        {
            ie.printStackTrace();
        }
    }

    private void printData()
    {
        System.out.println("No. of Docs '" + myDocs.size() + "'.");
        Iterator it = myDocs.iterator();
        while (it.hasNext())
        {
            System.out.println(it.next().toString());
        }
    }

    private void populate() throws InstantiationException,
            IllegalAccessException, ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:mysql://",
                "root", "trueno");
        Statement st = connection.createStatement();
        ResultSet rs = null;
        st.executeUpdate("use dblp;");
        Statement st2 = connection.createStatement();

        Iterator it = myDocs.iterator();
        for (Element e : myDocs)
        {
            // Insert genres
            rs = st.executeQuery("select count(*) from tbl_genres where genre_name = '"
                    + e.getDocumentType() + "';");
            while (rs.next())
            {
                if (rs.getInt(1) < 1)
                    st2.executeUpdate("insert into tbl_genres(genre_name) values ('"
                            + e.getDocumentType() + "'); ");
            }

            // Insert people(author and editors)
            if (e.getEditor() != null && !e.getEditor().equals(""))
            {
                String r = e.getEditor().replace("'", "\\'");
                rs = st.executeQuery("select count(*) from tbl_people where name = '"
                        + r + "';");
                while (rs.next())
                {
                    if (rs.getInt(1) < 1)
                        st2.executeUpdate("insert into tbl_people(name) values ('"
                                + r + "'); ");
                }
            }
            if (!e.getAuthor().isEmpty())
            {
                Iterator authorIt = e.getAuthor().iterator();
                while (authorIt.hasNext())
                {
                    String auth = (String) authorIt.next();
                    rs = st.executeQuery("select count(*) from tbl_people where name = '"
                            + auth + "';");
                    while (rs.next())
                    {
                        if (rs.getInt(1) < 1)
                            st2.executeUpdate("insert into tbl_people(name) values ('"
                                    + auth + "'); ");
                    }
                }
            }
            
            // Insert book titles
            if (e.getBookTitle() != null)
            {
                String te = e.getBookTitle().replace("'", "\\'");
                rs = st.executeQuery("select count(*) from tbl_booktitle where title = '"
                        + te + "';");
                while (rs.next())
                {
                    if (rs.getInt(1) < 1)
                        st2.executeUpdate("insert into tbl_booktitle(title) values ('"
                                + te + "'); ");
                }
            }
            
            // Insert publisher
            if (e.getPublisher() != null)
            {
                String pe = e.getPublisher().replace("'", "\\'");
                rs = st.executeQuery("select count(*) from tbl_publisher where publisher_name = '"
                        + pe + "';");
                while (rs.next())
                {
                    if (rs.getInt(1) < 1)
                        st2.executeUpdate("insert into tbl_publisher(publisher_name) values ('"
                                + pe + "'); ");
                }
            }

            // Insert dblp document
            int peoID = -1, bookID = -1, genreID = -1, pubID = -1;
            int start = -1, end = -1;
            if (e.getPages() != null)
            {
                String[] x;
                x = e.getPages().split("-");
                if (x.length > 0)
                {
                    if (isNum(x[0])) start = Integer.parseInt(x[0]);
                }
                if (x.length > 1)
                {
                    if (isNum(x[1])) end = Integer.parseInt(x[1]);
                }
            }

            if (e.getEditor() != null)
            {
                String re = e.getEditor().replace("'", "\\'");
                rs = st.executeQuery("select id from tbl_people where name = '"
                        + re + "';");
                while (rs.next())
                {
                    peoID = rs.getInt(1);
                }
            }

            if (e.getBookTitle() != null)
            {
                String te = e.getBookTitle().replace("'", "\\'");
                rs = st.executeQuery("select id from tbl_booktitle where title = '"
                        + te + "';");
                while (rs.next())
                {
                    bookID = rs.getInt(1);
                }
            }

            if (e.getDocumentType() != null)
            {
                rs = st.executeQuery("select id from tbl_genres where genre_name = '"
                        + e.getDocumentType() + "';");
                while (rs.next())
                {
                    genreID = rs.getInt(1);
                }
            }

            if (e.getBookTitle() != null)
            {
                String bt = e.getBookTitle().replace("'", "\\'");
                rs = st.executeQuery("select id from tbl_publisher where publisher_name = '"
                        + bt + "';");
                while (rs.next())
                {
                    pubID = rs.getInt(1);
                }
            }

            String rTitle = null;
            if (e.getTitle() != null)
                rTitle = e.getTitle().replace("'", "\\'");

            st.executeUpdate(
                    "insert into tbl_dblp_document(title,start_page,end_page,year,volume,number,url,ee,cdrom,cite,crossref,isbn,series,editor_id,booktitle_id,genre_id,publisher_id) "
                            + " values ("
                            + ((e.getTitle() != null && !e.getTitle()
                                    .equals("")) ? ("'" + rTitle + "'")
                                    : ("null"))
                            + ","
                            + (start != -1 ? start : "null")
                            + ","
                            + (end != -1 ? end : "null")
                            + ","
                            + ((e.getYear() != null && !e.getYear().equals("")) ? e
                                    .getYear() : ("null"))
                            + ","
                            + (e.getVolume() != null && isNum(e.getVolume()) ? e
                                    .getVolume() : ("null"))
                            + ","
                            + (e.getNumber() != null ? e.getNumber() : ("null"))
                            + ","
                            + (e.getUrl() != null ? ("'" + e.getUrl() + "'")
                                    : ("null"))
                            + ","
                            + (e.getEe() != null ? ("'" + e.getEe() + "'")
                                    : ("null"))
                            + ","
                            + (e.getCdrom() != null ? ("'" + e.getCdrom() + "'")
                                    : ("null"))
                            + ","
                            + (e.getCite() != null ? ("'" + e.getCite() + "'")
                                    : ("null"))
                            + ","
                            + (e.getCrossref() != null ? ("'" + e.getCrossref() + "'")
                                    : ("null"))
                            + ","
                            + (e.getIsbn() != null ? ("'" + e.getIsbn() + "'")
                                    : ("null"))
                            + ","
                            + (e.getSeries() != null ? ("'" + e.getSeries() + "'")
                                    : ("null"))
                            + ","
                            + (peoID != -1 ? peoID : "null")
                            + ","
                            + (bookID != -1 ? bookID : "null")
                            + ","
                            + (genreID != -1 ? genreID : "null")
                            + ","
                            + (pubID != -1 ? pubID : "null") + ")",
                    Statement.RETURN_GENERATED_KEYS);

            int docID = -1;
            rs = st.getGeneratedKeys();
            while (rs.next())
            {
                docID = rs.getInt(1);
            }
            System.out.println(docID);

            Iterator<String> aIt = e.getAuthor().iterator();
            while (aIt.hasNext())
            {
                String auth = aIt.next();
                rs = st.executeQuery("select id from tbl_people where name = '"
                        + auth + "';");
                while (rs.next())
                {
                    st2.executeUpdate("insert into tbl_author_document_mapping(doc_id, author_id) values ("
                            + docID + "," + rs.getInt(1) + "); ");
                }
            }

        }
        connection.close();
    }

    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException
    {
        tempVal = "";

        if (qName.equalsIgnoreCase("book"))
        {
            tempElement = new Element("book");
            tempElement.setKey(attributes.getValue("key"));
            tempElement.setMdate(attributes.getValue("mdate"));
        }

        if (qName.equalsIgnoreCase("article"))
        {
            tempElement = new Element("article");
            tempElement.setKey(attributes.getValue("key"));
            tempElement.setMdate(attributes.getValue("mdate"));
            tempElement.setReviewId(attributes.getValue("reviewid"));
            tempElement.setRating(attributes.getValue("rating"));
        }

        if (qName.equalsIgnoreCase("inproceedings"))
        {
            tempElement = new Element("inproceedings");
            tempElement.setKey(attributes.getValue("key"));
            tempElement.setMdate(attributes.getValue("mdate"));
        }

        if (qName.equalsIgnoreCase("proceedings"))
        {
            tempElement = new Element("proceedings");
            tempElement.setKey(attributes.getValue("key"));
            tempElement.setMdate(attributes.getValue("mdate"));
        }

        if (qName.equalsIgnoreCase("incollection"))
        {
            tempElement = new Element("incollection");
            tempElement.setKey(attributes.getValue("key"));
            tempElement.setMdate(attributes.getValue("mdate"));
        }

        if (qName.equalsIgnoreCase("phdthesis"))
        {
            tempElement = new Element("phdthesis");
            tempElement.setKey(attributes.getValue("key"));
            tempElement.setMdate(attributes.getValue("mdate"));
        }

        if (qName.equalsIgnoreCase("mastersthesis"))
        {
            tempElement = new Element("masterthesis");
            tempElement.setKey(attributes.getValue("key"));
            tempElement.setMdate(attributes.getValue("mdate"));
        }

        if (qName.equalsIgnoreCase("www"))
        {
            tempElement = new Element("www");
            tempElement.setKey(attributes.getValue("key"));
            tempElement.setMdate(attributes.getValue("mdate"));
        }

        if (qName.equalsIgnoreCase("publisher"))
        {
            if (attributes.getLength() != 0)
                tempElement.setPublisherHref(attributes.getValue("href"));
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException
    {
        tempVal = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException
    {
        tempVal = tempVal.trim();
        if (qName.equalsIgnoreCase("book"))
        {
            myDocs.add(tempElement);
        }

        else if (qName.equalsIgnoreCase("article"))
        {
            myDocs.add(tempElement);
        }

        else if (qName.equalsIgnoreCase("inproceedings"))
        {
            myDocs.add(tempElement);
        }

        else if (qName.equalsIgnoreCase("proceedings"))
        {
            myDocs.add(tempElement);
        }

        else if (qName.equalsIgnoreCase("incollection"))
        {
            myDocs.add(tempElement);
        }

        else if (qName.equalsIgnoreCase("phdthesis"))
        {
            myDocs.add(tempElement);
        }

        else if (qName.equalsIgnoreCase("masterthesis"))
        {
            myDocs.add(tempElement);
        }

        else if (qName.equalsIgnoreCase("www"))
        {
            myDocs.add(tempElement);
        }

        else if (qName.equalsIgnoreCase("editor"))
        {
            tempElement.setEditor(tempVal);
        }

        else if (qName.equalsIgnoreCase("title"))
        {
            tempElement.setTitle(tempVal);
        }

        else if (qName.equalsIgnoreCase("booktitle"))
        {
            tempElement.setBookTitle(tempVal);
        }

        else if (qName.equalsIgnoreCase("publisher"))
        {
            tempElement.setPublisher(tempVal);
        }

        else if (qName.equalsIgnoreCase("year"))
        {
            tempElement.setYear(tempVal);
        }

        else if (qName.equalsIgnoreCase("isbn"))
        {
            tempElement.setIsbn(tempVal);
        }

        else if (qName.equalsIgnoreCase("url"))
        {
            tempElement.setUrl(tempVal);
        }

        else if (qName.equalsIgnoreCase("pages"))
        {
            tempElement.setPages(tempVal);
        }

        else if (qName.equalsIgnoreCase("address"))
        {
            tempElement.setAddress(tempVal);
        }

        else if (qName.equalsIgnoreCase("journal"))
        {
            tempElement.setJournal(tempVal);
        }

        else if (qName.equalsIgnoreCase("volume"))
        {
            tempElement.setVolume(tempVal);
        }
        else if (qName.equalsIgnoreCase("number"))
        {
            tempElement.setNumber(tempVal);
        }
        else if (qName.equalsIgnoreCase("Ee"))
        {
            tempElement.setEe(tempVal);
        }
        else if (qName.equalsIgnoreCase("Cdrom"))
        {
            tempElement.setCdrom(tempVal);
        }
        else if (qName.equalsIgnoreCase("cite"))
        {
            tempElement.setCite(tempVal);
        }
        else if (qName.equalsIgnoreCase("crossref"))
        {
            tempElement.setCrossref(tempVal);
        }
        else if (qName.equalsIgnoreCase("series"))
        {
            tempElement.setSeries(tempVal);
        }
    }

    public static boolean isNum(String s)
    {
        try
        {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws InstantiationException,
            IllegalAccessException, ClassNotFoundException, SQLException
    {
        XMLSAXParser spe = new XMLSAXParser();
        spe.runExample();
    }

}
