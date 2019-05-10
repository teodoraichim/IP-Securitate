package com;

import com.database.SQL_func;
import com.security.HelpFunctions;
import com.security.Register;
import com.security.User;

import javax.mail.MessagingException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws MessagingException {
//        User user1=new User("silviu.mariuta","password","silviu.mariuta@info.uaic.ro");
////        System.out.println(user1.register());
//        user1.activate("a1a909e12cf45811002dd97f9bec14a2");
//        System.out.println(user1.login());
//        HelpFunctions func=new HelpFunctions();
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//        System.out.println(salt);
//        System.out.println(func.byteToString(salt));
//        System.out.println(salt == func.byteToString(salt).getBytes());

        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            //conversie string->date
            Date date=sd.parse("1999-09-02 10:22");
            Date cur=new Date();
            //conversia date->string
            String format=sd.format(cur);
            System.out.println(format);
            //diferenta in ore dintre doua date
            long diff=Math.abs(cur.getTime()-date.getTime());
            long hours= TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);

            //
            System.out.println(hours);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
