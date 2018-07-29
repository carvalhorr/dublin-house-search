package daft.consistency.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import daft.consistency.data.FieldConfig;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class FieldConfigPersistence implements IFieldConfigPersistence {

    private static final String CONFIG_FOLDER = "config";

    private File baseDirectory;

    public FieldConfigPersistence() {
        this(new File(CONFIG_FOLDER));
    }

    public FieldConfigPersistence(File baseDirectory) {
        this.baseDirectory = baseDirectory;
        if (!this.baseDirectory.exists()) {
            this.baseDirectory.mkdir();
        }
    }


    @Override
    public Map<String, FieldConfig> loadFields(String fieldSet) {
        try {
            File fieldsDataFile = getFileUri(fieldSet + ".json");
            if (!fieldsDataFile.exists()) {
                return new HashMap<String, FieldConfig>();
            }
            Reader reader = new FileReader(fieldsDataFile);
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, FieldConfig>>() {
            }.getType();
            Map<String, FieldConfig> fieldsConfig = gson.fromJson(reader, type);
            if (fieldsConfig == null) {
                fieldsConfig = new HashMap<String, FieldConfig>();
            }
            return fieldsConfig;
        } catch (TypeNotPresentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<String, FieldConfig>();
    }

    @Override
    public void saveFields(String fieldSet, Map<String, FieldConfig> fields) {
        try {
            File file = getFileUri(fieldSet + ".json");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(fields, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFileUri(String fileName) throws IOException {
        File f = new File(baseDirectory, fileName);
        return f;
    }

}
