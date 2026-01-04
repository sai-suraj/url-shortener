package demo.url_shortener.util;

public class Base62 {
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = BASE62_CHARS.length();

    public static String encode(long number){
        if(number == 0){
            return String.valueOf(BASE62_CHARS.charAt(0));
        }
        StringBuilder res = new StringBuilder();
        while(number > 0){
         res.append(String.valueOf(BASE62_CHARS.charAt(Math.toIntExact(number % BASE))));
         number = number/BASE;
        }
        return res.reverse().toString();
    }

}
