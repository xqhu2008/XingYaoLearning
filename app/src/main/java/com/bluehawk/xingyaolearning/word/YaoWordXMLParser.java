package com.bluehawk.xingyaolearning.word;

import android.content.Context;

import com.bluehawk.xingyaolearning.word.YaoResourceManager;
import com.bluehawk.xingyaolearning.word.YaoWordCategory;
import com.bluehawk.xingyaolearning.word.YaoWordVocabulary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Alex on 2016/6/14.
 */
public class YaoWordXMLParser {
    static final String KEY_CATEGORY = "category";
    static final String KEY_CATEGORY_NAME = "name";
    static final String KEY_CATEGORY_IMAGE = "image";
    static final String KEY_CATEGORY_DESC = "description";
    static final String KEY_CATEGORY_ITEM = "item";
    static final String KEY_ITEM_ENGLISH = "english";
    static final String KEY_ITEM_CHINESE = "chinese";


    static final String KEY_ITEM_IMAGE_FILE = "image";
    static final String KEY_ITEM_AUDIO_FILE = "audio";

    public static YaoResourceManager readFromFile(Context context, String sFileName) {
        YaoResourceManager manager = new YaoResourceManager();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            InputStream inStream = context.getAssets().open(sFileName);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(inStream);

            Element root = dom.getDocumentElement();
            NodeList categorys = root.getElementsByTagName(KEY_CATEGORY);
            for (int i = 0; i < categorys.getLength(); i++) {
                Element elements = (Element)categorys.item(i);
                String category = elements.getAttribute(KEY_CATEGORY_NAME);
                YaoWordCategory yaoWordCategory = new YaoWordCategory(category,
                        elements.getAttribute(KEY_CATEGORY_DESC),
                        elements.getAttribute(KEY_CATEGORY_IMAGE));

                NodeList items = elements.getElementsByTagName(KEY_CATEGORY_ITEM);
                for (int k = 0; k < items.getLength(); k++) {
                    Element study = (Element)items.item(k);
                    YaoWordVocabulary vocabulary = new YaoWordVocabulary();
                    NodeList childNodes = study.getChildNodes();

                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node child =  childNodes.item(j);
                        if (child.getNodeType() != Node.ELEMENT_NODE) {
                            continue;
                        }

                        Element childElement = (Element) child;
                        if (KEY_ITEM_ENGLISH.equals(childElement.getNodeName())) {
                            vocabulary.setName(childElement.getTextContent().trim());
                        } else if (KEY_ITEM_CHINESE.equals(childElement.getNodeName())) {
                            vocabulary.setChineseMeaning(childElement.getTextContent().trim());
                        } else if (KEY_ITEM_IMAGE_FILE.equals(childElement.getNodeName())) {
                            vocabulary.setImageFileName(childElement.getTextContent().trim());
                        } else if (KEY_ITEM_AUDIO_FILE.equals(childElement.getNodeName())) {
                            vocabulary.setSoundFileName(childElement.getTextContent().trim());
                        }
                    }

                    yaoWordCategory.addVocabulary(vocabulary);
                }

                manager.addCategory(yaoWordCategory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return manager;
    }

    public void writeToFile(Context context, String sFileName) {

    }
}
