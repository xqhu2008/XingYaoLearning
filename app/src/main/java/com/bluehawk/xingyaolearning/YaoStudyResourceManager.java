package com.bluehawk.xingyaolearning;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Alex on 2016/5/30.
 */
public class YaoStudyResourceManager {
    static final String KEY_CATEGORY = "category";
    static final String KEY_CATEGORY_NAME = "name";
    static final String KEY_CATEGORY_IMAGE = "image";
    static final String KEY_CATEGORY_DESC = "description";
    static final String KEY_CATEGORY_ITEM = "item";
    static final String KEY_ITEM_ENGLISH = "english";
    static final String KEY_ITEM_CHINESE = "chinese";


    static final String KEY_ITEM_IMAGE_FILE = "image";
    static final String KEY_ITEM_AUDIO_FILE = "audio";

    private Vector<YaoStudyResource> mYaoStudyResources;

    public Vector<YaoStudyResource> getYaoStudyResources() {
        return mYaoStudyResources;
    }

    public int getLength() {
        return mYaoStudyResources.size();
    }

    YaoStudyResource getStudyResource(int index) {
        return mYaoStudyResources.get(index);
    }

    public void readYaoStudySites(InputStream inStream) {
        mYaoStudyResources = new Vector<YaoStudyResource>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(inStream);

            Element root = dom.getDocumentElement();
            NodeList categorys = root.getElementsByTagName(KEY_CATEGORY);
            for (int i = 0; i < categorys.getLength(); i++) {
                Element elements = (Element)categorys.item(i);
                String category = elements.getAttribute(KEY_CATEGORY_NAME);
                YaoStudyResource yaoStudyResource = new YaoStudyResource(category,
                        elements.getAttribute(KEY_CATEGORY_IMAGE),
                        elements.getAttribute(KEY_CATEGORY_DESC));

                NodeList items = elements.getElementsByTagName(KEY_CATEGORY_ITEM);
                for (int k = 0; k < items.getLength(); k++) {
                    Element study = (Element)items.item(k);
                    YaoStudyItem word = new YaoStudyItem(category);
                    NodeList childNodes = study.getChildNodes();

                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node child =  childNodes.item(j);
                        if (child.getNodeType() != Node.ELEMENT_NODE) {
                            continue;
                        }

                        Element childElement = (Element) child;
                        if (KEY_ITEM_ENGLISH.equals(childElement.getNodeName())) {
                            word.setEnglishName(childElement.getTextContent().trim());
                        } else if (KEY_ITEM_CHINESE.equals(childElement.getNodeName())) {
                            word.setChineseName(childElement.getTextContent().trim());
                        } else if (KEY_ITEM_IMAGE_FILE.equals(childElement.getNodeName())) {
                            word.setImageFileName(childElement.getTextContent().trim());
                        } else if (KEY_ITEM_AUDIO_FILE.equals(childElement.getNodeName())) {
                            word.setAudioFileName(childElement.getTextContent().trim());
                        }
                    }

                    yaoStudyResource.addResource(word);
                }

                mYaoStudyResources.add(yaoStudyResource);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
