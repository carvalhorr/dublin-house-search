package daft.handler;

import data.PropertyInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class FileWriterPropertyInfoHandler implements IPropertyInfoExtractedHandler {

    private static FileWriter fileWriter;
    private static AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void start() {
        synchronized (this) {
            if (fileWriter == null) {
                try {
                    counter.incrementAndGet();
                    fileWriter = new FileWriter(new File("daft.import"));
                } catch (IOException e) {
                    fileWriter = null;
                }
            }
        }
    }

    @Override
    public void handle(PropertyInfo propertyInfo) {
        synchronized (this) {
            if (fileWriter != null) {
                try {
                    fileWriter.append(propertyInfo.toString());
                    fileWriter.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void end() {
        synchronized (counter) {
            if (fileWriter != null) {
                try {
                    counter.decrementAndGet();
                    if (counter.get() == 0) {
                        fileWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
