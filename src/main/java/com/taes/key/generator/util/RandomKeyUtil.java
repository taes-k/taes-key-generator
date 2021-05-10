package com.taes.key.generator.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class RandomKeyUtil
{
    private RandomKeyUtil()
    {
        throw new IllegalStateException("Utility class");
    }

    public static String generateRandomStringKey()
    {
        return String.format(
            "%s-%s-%s-%s"
            , getRandomLetterToken()
            , getRandomLetterToken()
            , getRandomLetterToken()
            , getRandomLetterToken());
    }

    public static String getRandomLetterToken()
    {
        return StringUtils.upperCase(RandomStringUtils.random(4, true, false));
    }
}
