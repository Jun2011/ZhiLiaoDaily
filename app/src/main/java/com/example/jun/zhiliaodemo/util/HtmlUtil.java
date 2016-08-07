package com.example.jun.zhiliaodemo.util;

import com.example.jun.zhiliaodemo.model.DailyDetailBean;

import java.util.List;

public class HtmlUtil {
    // css样式、隐藏header
    private static final String HIDE_HEADER_STYLE = "<style>div.headline{display:none;}</style>";
    // css、style、tag需要格式化
    private static final String NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";
    // js、script、tag需要格式化
    private static final String NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>";

    private HtmlUtil() {

    }

    // 根据css链接生成Link标签
    public static String createCssTag(String url) {

        return String.format(NEEDED_FORMAT_CSS_TAG, url);
    }

    // 根据多个css链接生成Link标签
    public static String createCssTag(List<String> urls) {

        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createCssTag(url));
        }
        return sb.toString();
    }

    // 根据js链接生成Script标签
    public static String createJsTag(String url) {

        return String.format(NEEDED_FORMAT_JS_TAG, url);
    }


    // 根据多个js链接生成Script标签
    public static String createJsTag(List<String> urls) {

        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createJsTag(url));
        }
        return sb.toString();
    }

    // 根据样式标签、html字符串、js标签生成完整的HTML文档
    private static String createHtmlData(String html, String css, String js) {

        return css.concat(HIDE_HEADER_STYLE).concat(html).concat(js);
    }


    // 生成完整的HTML文档
    public static String createHtmlData(DailyDetailBean detail) {

        final String css = HtmlUtil.createCssTag(detail.getCss());
        final String js = HtmlUtil.createJsTag((List<String>) detail.getJs());
        return createHtmlData(detail.getBody(), css, js);
    }
}
