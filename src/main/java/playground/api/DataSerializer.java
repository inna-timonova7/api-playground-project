package playground.api;
import playground.models.Data;

import java.io.*;

public class DataSerializer {

    public boolean serialize(Data data) throws IOException {
        boolean flag = false;
        File file = new File("C:/Users/Inna/api-playground/test");
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if (fos != null) {
                oos = new ObjectOutputStream(fos);
                oos.writeObject(data);
                flag = true;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
        return flag;
    }

    public Data deserialize() throws InvalidObjectException {
        File file = new File("C:/Users/Inna/api-playground/test");
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            if (fis != null) {
                ois = new ObjectInputStream(fis);
                Data data = (Data) ois.readObject();
                return data;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
            }
        }
        throw new InvalidObjectException("Object failed");
    }
}
