import groovy.transform.Canonical
/**
 * 文件名称： WanDouJiaEntity
 * 作   者： GuoMaoJian
 * 创建日期： 2017/01/04-22:21
 * 文件描述：
 * <p>
 */

@Canonical
class WanDouJiaEntity {
    String idString;
    String title;
    String icon;
    String tagLine;
    String templateType;
    String description;
    String subTitle;
    String contentType;
    Detail detail;
    Action action;
    Integer badge;
    String symbol;
    List<?> tag;
    List<?> cover;
    IconImage iconImage;
    List<?> subEntity;

    @Canonical
    static class Detail {
        AppDetail appDetail;
    }

    @Canonical
    static class AppDetail {
        String detailParam;
        String installedCountStr;
        String packageName;
        List<Apk> apk;
        Award award;
        List<?> extensionPack;
        String appType;
        List<?> tag;
    }

    @Canonical
    static class Apk {
        Long bytes;
        Integer compatible;
        DownloadUrl downloadUrl;
        String incompatibleDetail;
        String md5;
        String packageName;
        String signature;
        String size;
        Integer versionCode;
    }

    @Canonical
    static class DownloadUrl {
        Integer aggrWeight;
        String market;
        String referUrl;
        String url;
    }

    @Canonical
    static class Award {
        Integer issue;
    }

    @Canonical
    static class Action {
        String intent;
        String url;
    }

    @Canonical
    static class IconImage {
        String url;
    }
}
