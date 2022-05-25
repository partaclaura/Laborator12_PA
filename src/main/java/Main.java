import SampleClasses.RandomClass;
import com.sun.jdi.InvocationException;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {
    public static void main(String[] args)
    {
        String driverName = "SampleClasses.RandomClass";
        try {
            /*
            URL classURL;
            classURL = new URL("file:///C://Users//elena//Desktop//Laborator12//target//classes");
            URL[] urls = {classURL};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class aClass = urlClassLoader.loadClass("SampleClasses.RandomClass");*/
            Class aClass = Class.forName(driverName);
            Analysator inspect = new Analysator(aClass);
            inspect.extractInfo();
            inspect.invokeNoArgsMethods();
        }catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
