package daft;

import data.PropertyInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

public class PropertyInfoExtractor {

    public PropertyInfo extractPropertyInfo(String id) {
        PropertyInfo propertyInfo = null;
        String url = null;
        Document doc = null;
        try {
            url = "http://www.daft.ie/" + id;
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .timeout(15000)
                    .get();

            Elements errorMessages = doc.select(".alertMessage");
            boolean removed = errorMessages.size() > 0 && errorMessages.text().contains("is no longer available");

            String shortUrl = url;
            String additionalData = "";
            String advertiserName = "";
            List<String> phones = new LinkedList<String>();

            if (!removed) {
                // String shortUrl = doc.select(".description_extras > a").text();
                additionalData = findData(doc);
                //String id = shortUrl.replace("http://www.daft.ie/", "").replace("https://www.daft.ie/", "");
                try {
                    advertiserName = doc.select("#smi-tab-negotiator h2").text();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                org.jsoup.select.Elements base64encodedPhoneNumbers = doc.select(".smi-contact-numbers button");
                for (Element base64encodedPhoneNumber : base64encodedPhoneNumbers) {
                    try {
                        if ("Show Number".equals(base64encodedPhoneNumbers.select("strong").text().trim())) {
                            String phone = new String(Base64.getDecoder().decode(base64encodedPhoneNumbers.attr("data-p")));
                            phones.add(phone);
                        } else {
                            phones.add(base64encodedPhoneNumbers.attr("data-p"));
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(shortUrl);
                    }
                }
            }

            propertyInfo = new PropertyInfo(id,
                    url,
                    additionalData);
            if (removed) {
                propertyInfo.setRemoved(removed);
            } else {
                propertyInfo.setAdvertiserName(advertiserName);
                propertyInfo.setAdvertiserPhoneNumbers(phones);
            }
        } catch (IOException e) {
            // TODO log error and recover
            System.out.println("ERROR loading : " + url);
            e.printStackTrace();
        }
        finally {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO log error and recover
                System.out.println("ERROR loading : " + url);
                e.printStackTrace();
            }
        }
        return propertyInfo;
    }


    private String findData(Document doc) {
        org.jsoup.select.Elements scripts = doc.select( "script");
        for (Element script : scripts) {

            String starting = "var trackingParam = ";
            if (script.toString().contains(starting)) {
                String data = script.toString()
                        .substring(script.toString().indexOf(starting) + starting.length(),
                                script.toString().indexOf("};") + 1);
                return data;
            }
        }
        return "";
    }


}
