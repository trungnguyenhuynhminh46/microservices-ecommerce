package com.tuber.product.service.domain.helper;

public class SlugGenerator {
    public static String slug(String title) {
        String slug = title.toLowerCase();
        slug = slug.replaceAll("á|à|ả|ạ|ã|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ", "a");
        slug = slug.replaceAll("é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ", "e");
        slug = slug.replaceAll("i|í|ì|ỉ|ĩ|ị", "i");
        slug = slug.replaceAll("ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ", "o");
        slug = slug.replaceAll("ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự", "u");
        slug = slug.replaceAll("ý|ỳ|ỷ|ỹ|ỵ", "y");
        slug = slug.replaceAll("đ", "d");
        slug = slug.replaceAll("\\`|\\~|\\!|\\@|\\#|\\||\\$|\\%|\\^|\\&|\\*|\\(|\\)|\\+|\\=|\\,|\\.|\\/|\\?|\\>|\\<|\\'|\\\"|\\:|\\;|_", "");
        slug = slug.replaceAll(" ", "-");
        slug = slug.replaceAll("-{2,}", "-");
        slug = slug.replaceAll("^-|-$", "");
        return slug;
    }
}
