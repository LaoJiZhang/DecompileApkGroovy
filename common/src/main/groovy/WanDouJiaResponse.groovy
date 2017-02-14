import groovy.transform.Canonical
/**
 * 文件名称： WanDouJiaResponse
 * 作   者： GuoMaoJian
 * 创建日期： 2017/01/04-22:16
 * 文件描述：
 * <p>
 */

@Canonical
public class WanDouJiaResponse {
    String nextUrl;
    String preUrl;
    Boolean hasMore;
    Integer pbVersion;
    String sessionId;
    Integer status;
    List<WanDouJiaEntity> entity;
}
