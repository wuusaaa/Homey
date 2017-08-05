package app.logic.managers;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created by Raz on 12/20/2016
 */

public class Services {
    private Services() {
    }

    private static final HashMap<Class, ManagerBase> services = new HashMap<>();

    public static void AddService(ManagerBase manager) {
        services.put(manager.getClass(), manager);
    }

    public static ManagerBase GetService(Class type) {
        //checks if the type is a subclass of ManagerBase and if the map doesnt contain the key.
        if ((ManagerBase.class.isAssignableFrom(type)) && !services.containsKey(type)) {
            Constructor ctor = type.getDeclaredConstructors()[0];
            try {
                ctor.newInstance();
            } catch (Exception e) { //TODO: Handle the exception?
                e.printStackTrace();
            }
        }

        return services.get(type);
    }

    public static byte[] GetBytes(Uri image, Context context)
    {
        try
        {
            InputStream inputStream = context.getContentResolver().openInputStream(image);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
