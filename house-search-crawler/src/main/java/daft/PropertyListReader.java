package daft;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class PropertyListReader implements Runnable {

    private BlockingQueue<String> propertyIdsQueue;
    private String url;
    private String idPrefix;

    public PropertyListReader(BlockingQueue<String> propertyIdsQueue, String url, String idPrefix) {
        this.propertyIdsQueue = propertyIdsQueue;
        this.url = url;
        this.idPrefix = idPrefix;
    }

    @Override
    public void run() {

        int offset = 0;

        Elements boxes = null;
        do {
            Document doc = null;
            try {
                doc = Jsoup.connect(url + offset).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            boxes = doc.select(".box");
            for (Element box : boxes) {
                String href = box.select("h2 a").attr("href");
                // System.out.println(box.select("h2 span").text() + " " + href);
                String[] parts = href.split("-");
                String id = idPrefix + parts[parts.length - 1].replace("/", "").trim();
                try {
                    propertyIdsQueue.put(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread.yield();
            }
            offset += 20;
        } while (boxes != null && boxes.size() > 0);

    }
}
