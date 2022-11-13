package com.example.inclujobs.helpers;

import java.util.regex.Pattern;

public class REGEX {
    public static final Pattern EMAIL = Pattern.compile("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}");
    public static final Pattern CUIT = Pattern.compile("^\\d{2}\\-\\d{8}\\-\\d{1}$");


}
