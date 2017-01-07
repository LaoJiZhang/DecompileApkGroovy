/**
 * 文件名称： WanDouJiaResponse
 * 作   者： GuoMaoJian
 * 创建日期： 2017/01/04-22:16
 * 文件描述：
 * <p>
 */

public class WanDouJiaResponse {
    String nextUrl;
    String preUrl;
    Boolean hasMore;
    Integer pbVersion;
    String sessionId;
    Integer status;
    List<WanDouJiaEntity> entity;

    String getNextUrl() {
        return nextUrl
    }

    void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl
    }

    String getPreUrl() {
        return preUrl
    }

    void setPreUrl(String preUrl) {
        this.preUrl = preUrl
    }

    Boolean getHasMore() {
        return hasMore
    }

    void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore
    }

    Integer getPbVersion() {
        return pbVersion
    }

    void setPbVersion(Integer pbVersion) {
        this.pbVersion = pbVersion
    }

    String getSessionId() {
        return sessionId
    }

    void setSessionId(String sessionId) {
        this.sessionId = sessionId
    }

    Integer getStatus() {
        return status
    }

    void setStatus(Integer status) {
        this.status = status
    }

    List<WanDouJiaEntity> getEntity() {
        return entity
    }

    void setEntity(List<WanDouJiaEntity> entity) {
        this.entity = entity
    }

    @Override
    public String toString() {
        return "WanDouJiaResponse{" +
                "nextUrl='" + nextUrl + '\'' +
                ", preUrl='" + preUrl + '\'' +
                ", hasMore=" + hasMore +
                ", pbVersion=" + pbVersion +
                ", sessionId='" + sessionId + '\'' +
                ", status=" + status +
                ", entity=" + entity +
                '}';
    }
}
