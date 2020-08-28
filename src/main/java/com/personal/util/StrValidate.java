package com.personal.util;

/**http://www.mamicode.com/info-detail-2903489.html
 * @author 李箎
 */
public class StrValidate {
    // 纯数字
    private static String DIGIT_REGEX = "[0-9]+";
    // 含有数字
    private static String CONTAIN_DIGIT_REGEX = ".*[0-9].*";
    // 纯字母
    private static String LETTER_REGEX = "[a-zA-Z]+";
    // 包含字母
    private static String CONTAIN_LETTER_REGEX = ".*[a-zA-z].*";
    // 纯中文
    private static String CHINESE_REGEX = "[\u4e00-\u9fa5]";
    // 仅仅包含字母和数字
    private static String LETTER_DIGIT_REGEX = "^[a-z0-9A-Z]+$";
    private static String CHINESE_LETTER_REGEX = "([\u4e00-\u9fa5]+|[a-zA-Z]+)";
    private static String CHINESE_LETTER_DIGIT_REGEX = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
    //同时包含字母和数字
    private static String LETTER_DIGIT = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]{8,20})$";
    /**
     * 判断字符串是否仅含有数字和字母
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        return str.matches(LETTER_DIGIT_REGEX);
    }

    /**
     * 同时包含字母和数字
     * @param str
     * @return
     */
    public static boolean letterAndDigit(String str) {
        return str.matches(LETTER_DIGIT);
    }

    public static void main(String[] args) {
        System.out.println(StrValidate.letterAndDigit("lp124554"));
    }
}
