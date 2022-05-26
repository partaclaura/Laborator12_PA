package SampleClasses;


import Annotations.Test;

public class RandomClass {
    private String message;
    public RandomClass()
    {

    }

    @Test
    public static void getInfo()
    {
        System.out.println("Compulsory for Lab12.");
    }

    @Test
    public void showMessage(String text)
    {
        System.out.println("Text in non static method: " + text);
    }
}
