/**
 * 文件名称： WanDouJiaEntity
 * 作   者： GuoMaoJian
 * 创建日期： 2017/01/04-22:21
 * 文件描述：
 * <p>
 */

public class WanDouJiaEntity {
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

    String getIdString() {
        return idString
    }

    void setIdString(String idString) {
        this.idString = idString
    }

    String getTitle() {
        return title
    }

    void setTitle(String title) {
        this.title = title
    }

    String getIcon() {
        return icon
    }

    void setIcon(String icon) {
        this.icon = icon
    }

    String getTagLine() {
        return tagLine
    }

    void setTagLine(String tagLine) {
        this.tagLine = tagLine
    }

    String getTemplateType() {
        return templateType
    }

    void setTemplateType(String templateType) {
        this.templateType = templateType
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    String getSubTitle() {
        return subTitle
    }

    void setSubTitle(String subTitle) {
        this.subTitle = subTitle
    }

    String getContentType() {
        return contentType
    }

    void setContentType(String contentType) {
        this.contentType = contentType
    }

    Detail getDetail() {
        return detail
    }

    void setDetail(Detail detail) {
        this.detail = detail
    }

    Action getAction() {
        return action
    }

    void setAction(Action action) {
        this.action = action
    }

    Integer getBadge() {
        return badge
    }

    void setBadge(Integer badge) {
        this.badge = badge
    }

    String getSymbol() {
        return symbol
    }

    void setSymbol(String symbol) {
        this.symbol = symbol
    }

    List<?> getTag() {
        return tag
    }

    void setTag(List<?> tag) {
        this.tag = tag
    }

    List<?> getCover() {
        return cover
    }

    void setCover(List<?> cover) {
        this.cover = cover
    }

    IconImage getIconImage() {
        return iconImage
    }

    void setIconImage(IconImage iconImage) {
        this.iconImage = iconImage
    }

    List<?> getSubEntity() {
        return subEntity
    }

    void setSubEntity(List<?> subEntity) {
        this.subEntity = subEntity
    }

    @Override
    public String toString() {
        return "WanDouJiaEntity{" +
                "idString='" + idString + '\'' +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", tagLine='" + tagLine + '\'' +
                ", templateType='" + templateType + '\'' +
                ", description='" + description + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", contentType='" + contentType + '\'' +
                ", detail=" + detail +
                ", action=" + action +
                ", badge=" + badge +
                ", symbol='" + symbol + '\'' +
                ", tag=" + tag +
                ", cover=" + cover +
                ", iconImage=" + iconImage +
                ", subEntity=" + subEntity +
                '}';
    }

    public static class Detail {
        AppDetail appDetail;

        public AppDetail getAppDetail() {
            return appDetail;
        }

        public void setAppDetail(AppDetail appDetail) {
            this.appDetail = appDetail;
        }
    }


    public static class AppDetail {
        String detailParam;
        String installedCountStr;
        String packageName;
        List<Apk> apk;
        Award award;
        List<?> extensionPack;
        String appType;
        List<?> tag;

        String getDetailParam() {
            return detailParam
        }

        void setDetailParam(String detailParam) {
            this.detailParam = detailParam
        }

        String getInstalledCountStr() {
            return installedCountStr
        }

        void setInstalledCountStr(String installedCountStr) {
            this.installedCountStr = installedCountStr
        }

        String getPackageName() {
            return packageName
        }

        void setPackageName(String packageName) {
            this.packageName = packageName
        }

        List<Apk> getApk() {
            return apk
        }

        void setApk(List<Apk> apk) {
            this.apk = apk
        }

        Award getAward() {
            return award
        }

        void setAward(Award award) {
            this.award = award
        }

        List<?> getExtensionPack() {
            return extensionPack
        }

        void setExtensionPack(List<?> extensionPack) {
            this.extensionPack = extensionPack
        }

        String getAppType() {
            return appType
        }

        void setAppType(String appType) {
            this.appType = appType
        }

        List<?> getTag() {
            return tag
        }

        void setTag(List<?> tag) {
            this.tag = tag
        }


        @Override
        public String toString() {
            return "AppDetail{" +
                    "detailParam='" + detailParam + '\'' +
                    ", installedCountStr='" + installedCountStr + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", apk=" + apk +
                    ", award=" + award +
                    ", extensionPack=" + extensionPack +
                    ", appType='" + appType + '\'' +
                    ", tag=" + tag +
                    '}';
        }
    }

    public static class Apk {
        Long bytes;
        Integer compatible;
        DownloadUrl downloadUrl;
        String incompatibleDetail;
        String md5;
        String packageName;
        String signature;
        String size;
        Integer versionCode;

        Long getBytes() {
            return bytes
        }

        void setBytes(Long bytes) {
            this.bytes = bytes
        }

        Integer getCompatible() {
            return compatible
        }

        void setCompatible(Integer compatible) {
            this.compatible = compatible
        }

        DownloadUrl getDownloadUrl() {
            return downloadUrl
        }

        void setDownloadUrl(DownloadUrl downloadUrl) {
            this.downloadUrl = downloadUrl
        }

        String getIncompatibleDetail() {
            return incompatibleDetail
        }

        void setIncompatibleDetail(String incompatibleDetail) {
            this.incompatibleDetail = incompatibleDetail
        }

        String getMd5() {
            return md5
        }

        void setMd5(String md5) {
            this.md5 = md5
        }

        String getPackageName() {
            return packageName
        }

        void setPackageName(String packageName) {
            this.packageName = packageName
        }

        String getSignature() {
            return signature
        }

        void setSignature(String signature) {
            this.signature = signature
        }

        String getSize() {
            return size
        }

        void setSize(String size) {
            this.size = size
        }

        Integer getVersionCode() {
            return versionCode
        }

        void setVersionCode(Integer versionCode) {
            this.versionCode = versionCode
        }


        @Override
        public String toString() {
            return "Apk{" +
                    "bytes=" + bytes +
                    ", compatible=" + compatible +
                    ", downloadUrl=" + downloadUrl +
                    ", incompatibleDetail='" + incompatibleDetail + '\'' +
                    ", md5='" + md5 + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", signature='" + signature + '\'' +
                    ", size='" + size + '\'' +
                    ", versionCode=" + versionCode +
                    '}';
        }
    }

    public static class DownloadUrl {
        Integer aggrWeight;
        String market;
        String referUrl;
        String url;

        Integer getAggrWeight() {
            return aggrWeight
        }

        void setAggrWeight(Integer aggrWeight) {
            this.aggrWeight = aggrWeight
        }

        String getMarket() {
            return market
        }

        void setMarket(String market) {
            this.market = market
        }

        String getReferUrl() {
            return referUrl
        }

        void setReferUrl(String referUrl) {
            this.referUrl = referUrl
        }

        String getUrl() {
            return url
        }

        void setUrl(String url) {
            this.url = url
        }


        @Override
        public String toString() {
            return "DownloadUrl{" +
                    "aggrWeight=" + aggrWeight +
                    ", market='" + market + '\'' +
                    ", referUrl='" + referUrl + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public static class Award {
        Integer issue;

        Integer getIssue() {
            return issue
        }

        void setIssue(Integer issue) {
            this.issue = issue
        }

        @Override
        public String toString() {
            return "Award{" +
                    "issue=" + issue +
                    '}';
        }
    }

    public static class Action {
        String intent;
        String url;

        public String getIntent() {
            return intent;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class IconImage {
        String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
