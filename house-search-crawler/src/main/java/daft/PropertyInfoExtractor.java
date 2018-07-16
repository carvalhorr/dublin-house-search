package daft;

import data.PropertyInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PropertyInfoExtractor implements Runnable {

    private BlockingQueue<String> propertyIdsQueue;
    private BlockingQueue<PropertyInfo> extractedPropertyInfos;
    private AtomicBoolean finishedAddingItems;

    public PropertyInfoExtractor(BlockingQueue<String> propertyIdsQueue,
                                 AtomicBoolean finishedAddingItems,
                                 BlockingQueue<PropertyInfo> extractedPropertyInfos) {
        this.propertyIdsQueue = propertyIdsQueue;
        this.finishedAddingItems = finishedAddingItems;
        this.extractedPropertyInfos = extractedPropertyInfos;
    }

    @Override
    public void run() {

        while ((propertyIdsQueue.size() > 0) || !finishedAddingItems.get()) {

            String url = null;
            Document doc = null;
            try {
                url = "http://www.daft.ie/" + propertyIdsQueue.take();
                doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                        .timeout(15000)
                        .get();

                String shortUrl = doc.select(".description_extras > a").text();
                String additionalData = findData(doc);
                String id = shortUrl
                        .replace("http://www.daft.ie/", "")
                        .replace("https://www.daft.ie/", "");
                String advertiserName = "";
                try {
                    advertiserName = doc.select("#smi-tab-negotiator h2").text();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                org.jsoup.select.Elements base64encodedPhoneNumbers = doc.select(".smi-contact-numbers button");
                List<String> phones = new LinkedList<String>();
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

                // TODO deal with properties that are not available (ex.: http://www.daft.ie/21849103 )

                PropertyInfo propertyInfo = new PropertyInfo(id,
                        url,
                        additionalData);
                propertyInfo.setAdvertiserName(advertiserName);
                propertyInfo.setAdvertiserPhoneNumbers(phones);

                try {
                    extractedPropertyInfos.put(propertyInfo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                // TODO log error and recover
                System.out.println("ERROR loading : " + url);
                e.printStackTrace();

            } catch (InterruptedException e) {
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
            // System.out.println("    " + propertyInfo);
        }

    }

    private String findData(Document doc) {
        org.jsoup.select.Elements scripts = doc.select("script");
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
