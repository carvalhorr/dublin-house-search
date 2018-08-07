package daft;

import data.PropertyInfo;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PropertyCrawlerCommand {

    private static BlockingQueue<String> propertyIds = new ArrayBlockingQueue<String>(100);
    private static BlockingQueue<PropertyInfo> extractedPropertyInfos = new ArrayBlockingQueue<>(100);
    private static AtomicBoolean finishedAddingItems = new AtomicBoolean(false);
    private static AtomicBoolean finishedExtracting = new AtomicBoolean(false);

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, InterruptedException {

        Thread shareReader = new Thread(new PropertyListReaderRunnable(propertyIds,
                "https://www.daft.ie/dublin-city/rooms-to-share/?s%5Broom_type%5D=either&s%5Badvanced%5D=1&s%5Bgender%5D=on&searchSource=sharing&offset=",
                "3"));

        Thread rentReader = new Thread(new PropertyListReaderRunnable(propertyIds,
                "http://www.daft.ie/dublin-city/residential-property-for-rent/?s%5Badvanced%5D=1&s%5Bignored_agents%5D%5B0%5D=428&s%5Bignored_agents%5D%5B1%5D=1551&searchSource=rental&offset=",
                "2"));

        Thread saleReader = new Thread(new PropertyListReaderRunnable(propertyIds,
                "http://www.daft.ie/dublin-city/property-for-sale/?s%5Badvanced%5D=1&searchSource=sale&offset=",
                "1"));
        Thread[] propertyListReaderThreads = {saleReader, rentReader, shareReader};
        for (Thread propertyListReaderThread : propertyListReaderThreads) {
            propertyListReaderThread.start();
        }

        // TODO make number of threads a parameter

        Thread[] infoExtractors = {
                new Thread(new PropertyInfoExtractorRunnable(propertyIds, finishedAddingItems, extractedPropertyInfos)),
                new Thread(new PropertyInfoExtractorRunnable(propertyIds, finishedAddingItems, extractedPropertyInfos)),
                new Thread(new PropertyInfoExtractorRunnable(propertyIds, finishedAddingItems, extractedPropertyInfos)),
                new Thread(new PropertyInfoExtractorRunnable(propertyIds, finishedAddingItems, extractedPropertyInfos)),
                new Thread(new PropertyInfoExtractorRunnable(propertyIds, finishedAddingItems, extractedPropertyInfos)),
                new Thread(new PropertyInfoExtractorRunnable(propertyIds, finishedAddingItems, extractedPropertyInfos)),
                new Thread(new PropertyInfoExtractorRunnable(propertyIds, finishedAddingItems, extractedPropertyInfos)),
                new Thread(new PropertyInfoExtractorRunnable(propertyIds, finishedAddingItems, extractedPropertyInfos)),
                new Thread(new PropertyInfoExtractorRunnable(propertyIds, finishedAddingItems, extractedPropertyInfos)),
                new Thread(new PropertyInfoExtractorRunnable(propertyIds, finishedAddingItems, extractedPropertyInfos))};
        for (Thread thread : infoExtractors) {
            thread.start();
        }

        ExtractedPropertyInfoConsumer[] propertyInfoConsumers = {
                new ExtractedPropertyInfoConsumer(propertyIds, extractedPropertyInfos, finishedAddingItems),
                new ExtractedPropertyInfoConsumer(propertyIds, extractedPropertyInfos, finishedAddingItems),
                new ExtractedPropertyInfoConsumer(propertyIds, extractedPropertyInfos, finishedAddingItems),
                new ExtractedPropertyInfoConsumer(propertyIds, extractedPropertyInfos, finishedAddingItems),
                new ExtractedPropertyInfoConsumer(propertyIds, extractedPropertyInfos, finishedAddingItems)
        };

        for(ExtractedPropertyInfoConsumer consumer : propertyInfoConsumers) {
            consumer.start();
        }

        List<Thread> consumerThreads = new ArrayList<Thread>();
        consumerThreads.add(new Thread(propertyInfoConsumers[0]));
        consumerThreads.add(new Thread(propertyInfoConsumers[1]));
        consumerThreads.add(new Thread(propertyInfoConsumers[2]));
        consumerThreads.add(new Thread(propertyInfoConsumers[3]));
        consumerThreads.add(new Thread(propertyInfoConsumers[4]));

        for (Thread thread : consumerThreads) {
            thread.start();
        }


        // Synchronization
        for (Thread thread : propertyListReaderThreads) {
            thread.join();
        }
        finishedAddingItems.set(true);

        for (Thread thread : infoExtractors) {
            thread.join();
        }
        finishedExtracting.set(true);
        for (Thread thread : consumerThreads) {
            thread.join();
        }
        for(ExtractedPropertyInfoConsumer consumer : propertyInfoConsumers) {
            consumer.end();
        }

    }

}
