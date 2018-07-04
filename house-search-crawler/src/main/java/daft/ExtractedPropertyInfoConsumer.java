package daft;

import daft.handler.*;
import data.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExtractedPropertyInfoConsumer implements Runnable, IPropertyInfoExtractedHandler {

    private BlockingQueue<PropertyInfo> extractedPropertyInfos;
    private BlockingQueue<String> ids;
    private AtomicBoolean finishedExtracting;

    private List<IPropertyInfoExtractedHandler> handlers = new ArrayList<IPropertyInfoExtractedHandler>();

    public ExtractedPropertyInfoConsumer(BlockingQueue<String> ids,
                                         BlockingQueue<PropertyInfo> extractedPropertyInfos,
                                         AtomicBoolean finishedExtracting) {

        this.extractedPropertyInfos = extractedPropertyInfos;
        this.finishedExtracting = finishedExtracting;
        this.ids = ids;

        handlers.add(new ConsolePrinterPropertyInfoHandler());
        handlers.add(new PropertyInfoHandlerService());
        handlers.add(new FileWriterPropertyInfoHandler());
        handlers.add(new DatabasePropertyInfoHandler());
        handlers.add(new FieldExtractorPropertyInfoHandler());

    }

    @Override
    public void run() {
        while ((ids.size() > 0) || extractedPropertyInfos.size() > 0 || !finishedExtracting.get()) {
            try {
                PropertyInfo propertyInfo = extractedPropertyInfos.take();
                for (IPropertyInfoExtractedHandler handler : handlers) {
                    handler.handle(propertyInfo);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start() {
        for (IPropertyInfoExtractedHandler handler : handlers) {
            handler.start();
        }
    }

    @Override
    public void handle(PropertyInfo propertyInfo) {

    }

    @Override
    public void end() {
        for (IPropertyInfoExtractedHandler handler : handlers) {
            handler.end();
        }
    }
}
