package playground.api;

import playground.models.ServiceSchema;

import java.io.*;

public class ServiceSchemaSerializer {

    public boolean serialize(ServiceSchema serviceSchema) throws IOException {
        boolean flag = false;
        File file = new File("C:/Users/Inna/api-playground/test");
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if (fos != null) {
                oos = new ObjectOutputStream(fos);
                oos.writeObject(serviceSchema);
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

    public ServiceSchema deserialize() throws InvalidObjectException {
        File file = new File("C:/Users/Inna/api-playground/test");
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            if (fis != null) {
                ois = new ObjectInputStream(fis);
                ServiceSchema serviceSchema = (ServiceSchema) ois.readObject();
                return serviceSchema;
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
