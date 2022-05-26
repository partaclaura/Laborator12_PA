import Annotations.Test;

import java.io.File;
import java.lang.reflect.*;
import java.util.Objects;

public class Analysator {
    Class aClass;
    //stats
    private int testMethods;
    private int nonTestMethods;
    private int passedTest;
    private int failedTest;
    private int averageInvokeTime;
    public Analysator(Class aClass)
    {
        this.aClass = aClass;
    }

    public void extractInfo()
    {
        System.out.println("**** Extracting info ****");
        System.out.println("Class name: " + aClass.getName());
        //package
        if(aClass.getPackage() != null)
            System.out.println("Package: " + aClass.getPackage());
        else System.out.println("Package: none" );
        //constructors
        if(aClass.getDeclaredConstructors().length != 0)
        {
            System.out.println("Class constructors: ");
            for(Constructor constructor : aClass.getDeclaredConstructors())
                System.out.println(" - " + constructor.toString());
        }
        else System.out.println("Class constructors: none");
        //methods
        if(aClass.getDeclaredMethods().length != 0)
        {
            System.out.println("Class methods: ");
            for(Method method : aClass.getDeclaredMethods())
                System.out.println(" - " + method.toString());
        }
        else System.out.println("Class methods: none");
        //fields
        if(aClass.getDeclaredFields().length != 0)
        {
            System.out.println("Class fields: ");
            for(Field field : aClass.getDeclaredFields())
                System.out.println(" - " + field.toString());
        }
        else System.out.println("Class fields: none");
        //inner classes
        if(aClass.getDeclaredClasses().length != 0)
        {
            System.out.println("Inner classes: ");
            for(Class c : aClass.getDeclaredClasses())
                System.out.println(" - " + c.toString());
        }
        else System.out.println("Inner classes: none");

    }

    public void invokeNoArgsMethods()
    {
        System.out.println("**** Invoking static methods with no arguments ****");
        for(Method method : this.aClass.getDeclaredMethods())
        {
            if(method.isAnnotationPresent(Test.class) &&
                    Modifier.isStatic(method.getModifiers()))
            {
                Parameter[] parameters = method.getParameters();
                if(parameters.length == 0)
                {
                    try {
                        System.out.println(" - Invoking method " + method.getName());
                        method.invoke(null);
                    } catch (IllegalAccessException | InvocationTargetException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void invokeAllTestMethods()
    {
        System.out.println("**** Invoking @Test methods ****");
        for(Method method : aClass.getDeclaredMethods())
        {
            if(method.isAnnotationPresent(Test.class))
            {
                Parameter[] parameters = method.getParameters();
                Object[] objects = new Object[parameters.length];
                for(int i = 0; i < parameters.length; i++)
                {
                    if(parameters[i].getType() == Integer.class)
                        objects[i] = 12;
                    else if(parameters[i].getType() == String.class)
                        objects[i] = "whatever";
                }
                try {
                    System.out.println(" - Invoking method ");
                    method.invoke(aClass.getDeclaredConstructor().newInstance(), objects);
                }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void exploreDirectory(File file)
    {
        System.out.println("Exploring directoy: " + file.getName());
        for(File subfile : Objects.requireNonNull(file.listFiles()))
            exploreFile(subfile);
    }

    private String getClassName(String name)
    {
        String[] names = name.split("\\\\");
        String foundName = names[names.length - 1];
        foundName = foundName.substring(0, foundName.length() - 6);
        return foundName;
    }

    public void exploreClass(File file)
    {
        String name = file.getName();
        System.out.println(name);
        System.out.println(getClassName(name));
        try {
            Class c = Class.forName(getClassName(name));
            this.aClass = c;
            extractInfo();
            invokeAllTestMethods();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public void exploreFile(File file)
    {
        if(file.isDirectory())
        {
            exploreDirectory(file);
        }
        else if(file.getName().endsWith(".class"))
            exploreClass(file);

    }
}
