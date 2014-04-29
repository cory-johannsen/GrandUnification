package main.java.unification.entity;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: cjohannsen
 * Date: 4/29/14
 * Time: 2:29 PM
 */
@XmlRootElement
public class ServerStatus {

    private Integer mRequestCount;
    private Long mUptime;
    private String mHostname;

    public ServerStatus() {
    }

    public ServerStatus(Integer requestCount, Long uptime, String hostname) {
        mRequestCount = requestCount;
        mUptime = uptime;
        mHostname = hostname;
    }

    @JsonProperty
    public Integer getRequestCount() {
        return mRequestCount;
    }

    public void setRequestCount(Integer requestCount) {
        mRequestCount = requestCount;
    }

    @JsonProperty
    public Long getUptime() {
        return mUptime;
    }

    public void setUptime(Long uptime) {
        mUptime = uptime;
    }

    @JsonProperty
    public String getHostname() {
        return mHostname;
    }

    public void setHostname(String hostname) {
        mHostname = hostname;
    }
}
