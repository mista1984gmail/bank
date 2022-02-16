package com.mista1984.bank.io;

import com.mista1984.bank.app.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class SerializationUtils {
    private static final Logger logger = LogManager.getLogger(SerializationUtils.class);
    private static final String FILENAME = "db.txt";

    public static void serialize(Object objectToSerialize) {
        try (OutputStream out = new FileOutputStream(FILENAME);
             ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(objectToSerialize);
            logger.info("Data was saved.");
        } catch (IOException e) {
            logger.error("Data was not saved.", e);
        }
    }

    public static Object deserialize() {
        Object object = null;
        try (InputStream in = new FileInputStream(FILENAME);
             ObjectInputStream ois = new ObjectInputStream(in)) {
            object = ois.readObject();
            logger.info("Data is loaded.");
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Data can not be loaded.", e);
        }
        return object;
    }
}
