package com.elim.study.elasticsearch.rest.high;

import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.xpack.XPackInfoRequest;
import org.elasticsearch.client.xpack.XPackInfoResponse;
import org.elasticsearch.client.xpack.XPackUsageRequest;
import org.elasticsearch.client.xpack.XPackUsageResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;

/**
 * 混杂的API测试
 */
public class MiscellaneousAPITest extends BaseRestHighLevelClient {

    @Test
    public void testInfo() throws Exception {
        MainResponse info = this.highLevelClient.info(RequestOptions.DEFAULT);
        System.out.println(this.toJson(info.getVersion()));
        System.out.println(info.getClusterName());
        System.out.println(info.getClusterUuid());
        System.out.println(info.getNodeName());
        System.out.println(this.toJson(info.getBuild()));
    }

    @Test
    public void testPing() throws IOException {
        boolean result = this.highLevelClient.ping(RequestOptions.DEFAULT);
        System.out.println(result);
    }

    @Test
    public void testXPackInfoRequest() throws Exception {
        XPackInfoRequest request = new XPackInfoRequest();
        request.setVerbose(true);
        request.setCategories(EnumSet.of(
                XPackInfoRequest.Category.BUILD,
                XPackInfoRequest.Category.LICENSE,
                XPackInfoRequest.Category.FEATURES));
        XPackInfoResponse response = this.highLevelClient.xpack().info(request, RequestOptions.DEFAULT);
        XPackInfoResponse.BuildInfo buildInfo = response.getBuildInfo();
        XPackInfoResponse.FeatureSetsInfo featureSetsInfo = response.getFeatureSetsInfo();
        XPackInfoResponse.LicenseInfo licenseInfo = response.getLicenseInfo();
        this.printJson(buildInfo);
        System.out.println("========================");
        this.printJson(featureSetsInfo);
        System.out.println("========================");
        this.printJson(licenseInfo);

    }

    @Test
    public void testXPackUsage() throws Exception {
        XPackUsageRequest request = new XPackUsageRequest();
        XPackUsageResponse response = this.highLevelClient.xpack().usage(request, RequestOptions.DEFAULT);
        Map<String, Map<String, Object>> usages = response.getUsages();
        this.printJson(usages);
    }

}
