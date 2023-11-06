package com.roomster.roomsterbackend.util.validator;
import java.util.regex.Pattern;


public class PhoneNumberValidator {
    private static final String PHONE_NUMBER_PATTERN = "^\\+84\\s?\\d{9}$|^\\+84\\s?0\\d{9}$|^\\+84\\s?\\d{8}$";
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the provided phone number matches the pattern
        return Pattern.matches(PHONE_NUMBER_PATTERN, phoneNumber);
    }

    public static String normalizePhoneNumber(String phoneNumber) {

        // Loại bỏ tất cả dấu cách
        phoneNumber = phoneNumber.replaceAll("\\s", "");

        // Loại bỏ số 0 đầu tiên sau +84 nếu tồn tại
        if (phoneNumber.startsWith("+840")) {
            phoneNumber = phoneNumber.replace("+840", "+84");
        }

        // Thêm tiền tố +84 nếu thiếu
        if (!phoneNumber.startsWith("+84")) {
            phoneNumber = "+84" + phoneNumber;
        }

        return phoneNumber;
    }
}
