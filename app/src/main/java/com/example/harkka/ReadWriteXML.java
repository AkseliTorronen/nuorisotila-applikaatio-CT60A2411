package com.example.harkka;

import android.content.Context;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//Handles all the writing, reading and modifying XML files (feedback and eventdata).
public class ReadWriteXML {

    public static ArrayList read(Context context){
        ArrayList<Event> eventList = new ArrayList<>();
        try{
            InputStream is = context.openFileInput("eventdata.xml");
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document XMLDocument = db.parse(is);
            NodeList nList = XMLDocument.getElementsByTagName("event");
            for (int i = 0; i < nList.getLength(); i++){
                String name = XMLDocument.getElementsByTagName("name").item(i).getTextContent();
                String venue = XMLDocument.getElementsByTagName("venue").item(i).getTextContent();
                String ageGroup = XMLDocument.getElementsByTagName("agegroup").item(i).getTextContent();
                int ageGroupID = Integer.parseInt(XMLDocument.getElementsByTagName("ageGroupID").item(i).getTextContent());
                String datetime = XMLDocument.getElementsByTagName("datetime").item(i).getTextContent();
                String description = XMLDocument.getElementsByTagName("description").item(i).getTextContent();
                String datetimeEND = XMLDocument.getElementsByTagName("datetimeEND").item(i).getTextContent();
                String onGoing = XMLDocument.getElementsByTagName("onGoing").item(i).getTextContent();
                int participants = Integer.parseInt(XMLDocument.getElementsByTagName("participants").item(i).getTextContent());
                String past = XMLDocument.getElementsByTagName("past").item(i).getTextContent();
                Event event = new Event(name, venue, ageGroup, description, datetime, ageGroupID, datetimeEND, onGoing, participants, past);
                eventList.add(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return eventList;

    }
    public static void write(Event event, Context context) {
        File fileWrite = context.getFileStreamPath("eventdata.xml");
        if (!fileWrite.exists()) {
            try {
                XmlSerializer serializer = Xml.newSerializer();
                StringWriter stringWriter = new StringWriter();
                serializer.setOutput(stringWriter);
                FileOutputStream fileOutputStream = context.openFileOutput("eventdata.xml", Context.MODE_APPEND);
                serializer.setOutput(stringWriter);
                serializer.startDocument("UTF-8", true);
                serializer.startTag(null, "events");
                serializer.endTag(null, "events");
                serializer.endDocument();
                serializer.flush();
                fileOutputStream.write(stringWriter.toString().getBytes());
                fileOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(fileWrite);
                Element root = document.getDocumentElement();
                Element newEvent = document.createElement("event");

                Element name = document.createElement("name");
                name.appendChild(document.createTextNode(event.name));
                newEvent.appendChild(name);

                Element location = document.createElement("venue");
                location.appendChild(document.createTextNode(event.venue));
                newEvent.appendChild(location);

                Element agegroup = document.createElement("agegroup");
                agegroup.appendChild(document.createTextNode(event.ageGroup));
                newEvent.appendChild(agegroup);

                Element ageID = document.createElement("ageGroupID");
                ageID.appendChild(document.createTextNode(String.valueOf(event.ageGroupID)));
                newEvent.appendChild(ageID);

                Element datetime = document.createElement("datetime");
                datetime.appendChild(document.createTextNode(event.datetime));
                newEvent.appendChild(datetime);

                Element description = document.createElement("description");
                description.appendChild(document.createTextNode(event.description));
                newEvent.appendChild(description);

                Element datetimeEND = document.createElement("datetimeEND");
                datetimeEND.appendChild(document.createTextNode(event.datetimeEND));
                newEvent.appendChild(datetimeEND);

                Element onGoing = document.createElement("onGoing");
                onGoing.appendChild(document.createTextNode(event.onGoing));
                newEvent.appendChild(onGoing);

                Element participants = document.createElement("participants");
                participants.appendChild(document.createTextNode(String.valueOf(event.participants)));
                newEvent.appendChild(participants);

                Element past = document.createElement("past");
                past.appendChild(document.createTextNode(event.past));
                newEvent.appendChild(past);

                root.appendChild(newEvent);

                DOMSource source = new DOMSource(document);

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                StreamResult sr = new StreamResult(fileWrite.getPath());
                transformer.transform(source, sr);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
            e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
            e.printStackTrace();
            }
        }
    }

    public static void writeFeedback(Feedback feedback, Context context){
        File fileWrite = context.getFileStreamPath("feedback.xml");
        if (!fileWrite.exists()) {
            try {
                XmlSerializer serializer = Xml.newSerializer();
                StringWriter stringWriter = new StringWriter();
                serializer.setOutput(stringWriter);
                FileOutputStream fileOutputStream = context.openFileOutput("feedback.xml", Context.MODE_APPEND);
                serializer.setOutput(stringWriter);
                serializer.startDocument("UTF-8", true);
                serializer.startTag(null, "feedbacks");
                serializer.endTag(null, "feedbacks");
                serializer.endDocument();
                serializer.flush();
                fileOutputStream.write(stringWriter.toString().getBytes());
                fileOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(context.openFileInput("feedback.xml"));
            Element root = document.getDocumentElement();
            Element newFeedback = document.createElement("feedback");

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(feedback.event));
            newFeedback.appendChild(name);

            Element feed = document.createElement("note");
            feed.appendChild(document.createTextNode(feedback.feed));
            newFeedback.appendChild(feed);

            root.appendChild(newFeedback);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(fileWrite.getPath());
            transformer.transform(source, result);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Feedback> readFeedback(Context context){
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        try{
            InputStream is = context.openFileInput("feedback.xml");
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document XMLDocument = db.parse(is);
            NodeList nList = XMLDocument.getElementsByTagName("feedback");
            for (int i = 0; i < nList.getLength(); i++){
                String name = XMLDocument.getElementsByTagName("name").item(i).getTextContent();
                String feed = XMLDocument.getElementsByTagName("note").item(i).getTextContent();
                Feedback feedbackE = new Feedback(feed, name);
                feedbackList.add(feedbackE);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    public static void delete(Context context, int pos) {
        File file = context.getFileStreamPath("eventdata.xml");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(context.openFileInput("eventdata.xml"));

            NodeList nodeList = document.getElementsByTagName("event");
            Element element = null;

            element = (Element) nodeList.item(pos);
            element.getParentNode().removeChild(element);

            DOMSource domSource = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file.getPath());
            transformer.transform(domSource, result);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }

    public static void modifyXML(Context context, int pos, Event event) {
        File file = context.getFileStreamPath("eventdata.xml");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(context.openFileInput("eventdata.xml"));

            NodeList nodeList = document.getElementsByTagName("event");

            Element element = (Element) nodeList.item(pos);
            Node name = element.getElementsByTagName("name").item(0).getFirstChild();
            name.setNodeValue(event.name);

            Node location = element.getElementsByTagName("venue").item(0).getFirstChild();
            location.setNodeValue(event.venue);

            Node ageGroup = element.getElementsByTagName("agegroup").item(0).getFirstChild();
            ageGroup.setNodeValue(event.ageGroup);

            Node ageID = element.getElementsByTagName("ageGroupID").item(0).getFirstChild();
            ageID.setNodeValue(String.valueOf(event.ageGroupID));

            Node datetime = element.getElementsByTagName("datetime").item(0).getFirstChild();
            datetime.setNodeValue(event.datetime);

            Node description = element.getElementsByTagName("description").item(0).getFirstChild();
            description.setNodeValue(event.description);

            Node datetimeEND = element.getElementsByTagName("datetimeEND").item(0).getFirstChild();
            datetimeEND.setNodeValue(event.datetimeEND);

            Node onGoing = element.getElementsByTagName("onGoing").item(0).getFirstChild();
            onGoing.setNodeValue(event.onGoing);

            Node part = element.getElementsByTagName("participants").item(0).getFirstChild();
            part.setNodeValue(String.valueOf(event.participants));

            Node past = element.getElementsByTagName("past").item(0).getFirstChild();
            past.setNodeValue(event.past);


            DOMSource domSource = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file.getPath());
            transformer.transform(domSource, result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}