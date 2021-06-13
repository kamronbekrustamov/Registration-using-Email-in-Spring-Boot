package com.kamronbek.auth.util;

public class EmailBuilder {
    public static String buildEmail(String activationLink) {
        String email = "    <div style=\"background-color: #ddd;\">\n" +
                "      <div style=\"width: 40%; margin: 0 auto; background-color: #fff; padding: 10px 30px;\">\n" +
                "        <h1 style=\"text-align: center;\">Confirm your email</h1>\n" +
                "        <h2>You are just one step away</h2>\n" +
                "        <a href=\"" + activationLink + "\" style=\"background-color: #666; text-decoration: none;color: #eee; padding: 8px;\">Confirm Email</a>\n" +
                "        <p>If you received this email by mistake, simply delete it. You won't be subscribed if you don't click the confirmation link above.</p>\n" +
                "      </div>\n" +
                "    </div>";
        return email;
    }
}
