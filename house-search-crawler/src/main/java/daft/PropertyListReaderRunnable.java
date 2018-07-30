package daft;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;

public class PropertyListReaderRunnable implements Runnable {

    private BlockingQueue<String> propertyIdsQueue;
    private String url;
    private String idPrefix;

    public PropertyListReaderRunnable(BlockingQueue<String> propertyIdsQueue, String url, String idPrefix) {
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
                doc = Jsoup.connect(url + offset)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                        .timeout(15000)
                        .get();
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
                }
                offset += 20;
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
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
        } while (boxes != null && boxes.size() > 0);

    }
}
