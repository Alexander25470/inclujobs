package com.example.inclujobs.helpers;

import java.util.regex.Pattern;

public class REGEX {
    public static final Pattern EMAIL = Pattern.compile("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}");
}
