package org.example.pastebinrestapi.service;

import org.springframework.stereotype.Service;

@Service
public class BaseConversion {
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final char[] allowedCharacters = allowedString.toCharArray();
    private final int base = allowedCharacters.length;

    public String encode(long input) {
        var encodedString = new StringBuilder();

        if (input == 0) {
            return String.valueOf(allowedCharacters[0]).repeat(4);
        }

        while(input > 0) {
            encodedString.append(allowedCharacters[(int)(input % base)]);
            input = input / base;
        }

        while (encodedString.length() < 4){
            encodedString.append(allowedCharacters[0]);
        }

        return encodedString.reverse().toString();
    }

    public long decode(String input){
        var characters = input.toCharArray();
        var length = characters.length;

        var decoded = 0;

        var counter = 1;
        for (char character : characters){
            decoded += (int) (allowedString.indexOf(character) * Math.pow(base, length - counter));
            counter++;
        }

        return decoded;
    }
}

