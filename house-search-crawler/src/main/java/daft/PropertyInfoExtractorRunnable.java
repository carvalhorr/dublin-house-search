package daft;

import data.PropertyInfo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PropertyInfoExtractorRunnable implements Runnable {

    private BlockingQueue<String> propertyIdsQueue;
    private BlockingQueue<PropertyInfo> extractedPropertyInfos;
    private AtomicBoolean finishedAddingItems;

    private PropertyInfoExtractor extractor;

    public PropertyInfoExtractorRunnable(BlockingQueue<String> propertyIdsQueue,
                                         AtomicBoolean finishedAddingItems,
                                         BlockingQueue<PropertyInfo> extractedPropertyInfos) {
        this.propertyIdsQueue = propertyIdsQueue;
        this.finishedAddingItems = finishedAddingItems;
        this.extractedPropertyInfos = extractedPropertyInfos;
        this.extractor = new PropertyInfoExtractor();
    }

    @Override
    public void run() {

        while ((propertyIdsQueue.size() > 0) || !finishedAddingItems.get()) {

            String id = "";
            try {
                id = propertyIdsQueue.take();
                PropertyInfo propertyInfo = extractor.extractPropertyInfo(id);

                if (propertyInfo != null) {
                    try {
                        extractedPropertyInfos.put(propertyInfo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } catch (InterruptedException e) {
                // TODO log error and recover
                System.out.println("ERROR loading : https://daft.ie/" + id);
                e.printStackTrace();
            }
            finally {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO log error and recover
                    System.out.println("ERROR loading : https://daft.ie/" + id);
                    e.printStackTrace();
                }
            }

        }

    }

}
