package lib;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class DataGenerator {
    public static String getRandomEmail(){
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa"+timestamp+"@example.com";
    }

    public static String getRandomEmailWithoutAtSign(){
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa"+timestamp+"example.com";
    }


    public static  Map<String, String>  getRegistrationData() {

        Map<String, String> data = new HashMap<>();
        data.put("email", getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");
        return  data;
    }

    public static  Map<String, String>  getRegistrationAnotherData() {

        Map<String, String> data = new HashMap<>();
        data.put("email", getRandomEmail());
        data.put("password", "123456");
        data.put("username", "AnotherName");
        data.put("firstName", "AnotherFirstName");
        data.put("lastName", "AnotherLastName");
        return  data;
    }

    public static  Map<String, String>  getRegistrationData(Map<String, String> noneDefaultValues) {

        Map<String, String> defaultValues = getRegistrationData();
        Map<String, String> userData = new HashMap<>();
        String[] keys = {"username", "password", "firstName", "lastName", "email"};

        for (String key : keys){
            if (noneDefaultValues.containsKey(key)){
                userData.put(key, noneDefaultValues.get(key));
            } else {
                userData.put(key, defaultValues.get(key));
            }
        }
        return userData;
    }

    public static  Map<String, String>  getRegistrationAnotherData(Map<String, String> noneDefaultValues) {

        Map<String, String> defaultValues = getRegistrationAnotherData();
        Map<String, String> userData = new HashMap<>();
        String[] keys = {"username", "password", "firstName", "lastName", "email"};

        for (String key : keys){
            if (noneDefaultValues.containsKey(key)){
                userData.put(key, noneDefaultValues.get(key));
            } else {
                userData.put(key, defaultValues.get(key));
            }
        }
        return userData;
    }

    public static  String  getRandomString(int size) {

        String symbols = "abcdefghijklmnopqrstuvwxyz";
        String randomString = new Random().ints(size, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        return  randomString;
    }

}
