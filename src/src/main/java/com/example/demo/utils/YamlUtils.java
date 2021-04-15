/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.example.demo.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

/**
 * @author Administrator
 * @date 2020/8/13 15:42
 */
public class YamlUtils {

    public static void main(String[] args) {

        String string = "{\"metadata\":{\"finalizers\":[],\"resourceVersion\":\"31359842\",\"labels\":{\"displayName\":\"test-hpa-hpa\"},\"ownerReferences\":[],\"selfLink\":\"/apis/autoscaling/v2beta1/namespaces/devprojectdev/horizontalpodautoscalers/test-hpa-hpa\",\"uid\":\"3eac9605-dd2b-11ea-a800-005056b232e2\",\"managedFields\":[],\"creationTimestamp\":\"2020-08-13T06:07:19Z\",\"name\":\"test-hpa-hpa\",\"namespace\":\"devprojectdev\",\"additionalProperties\":{}},\"apiVersion\":\"autoscaling/v2beta1\",\"kind\":\"HorizontalPodAutoscaler\",\"additionalProperties\":{},\"spec\":{\"maxReplicas\":3,\"minReplicas\":1,\"additionalProperties\":{},\"metrics\":[{\"resource\":{\"targetAverageUtilization\":90,\"name\":\"memory\",\"additionalProperties\":{}},\"additionalProperties\":{},\"type\":\"Resource\"},{\"resource\":{\"targetAverageUtilization\":90,\"name\":\"cpu\",\"additionalProperties\":{}},\"additionalProperties\":{},\"type\":\"Resource\"}],\"scaleTargetRef\":{\"apiVersion\":\"apps/v1\",\"kind\":\"Deployment\",\"name\":\"test-hpa\",\"additionalProperties\":{}}},\"status\":{\"desiredReplicas\":1,\"currentReplicas\":1,\"additionalProperties\":{},\"conditions\":[{\"reason\":\"ReadyForNewScale\",\"additionalProperties\":{},\"lastTransitionTime\":\"2020-08-13T06:07:49Z\",\"message\":\"the last scale time was sufficiently old as to warrant a new scale\",\"type\":\"AbleToScale\",\"status\":\"True\"},{\"reason\":\"ValidMetricFound\",\"additionalProperties\":{},\"lastTransitionTime\":\"2020-08-13T06:08:19Z\",\"message\":\"the HPA was able to successfully calculate a replica count from memory resource utilization (percentage of request)\",\"type\":\"ScalingActive\",\"status\":\"True\"},{\"reason\":\"DesiredWithinRange\",\"additionalProperties\":{},\"lastTransitionTime\":\"2020-08-13T06:08:19Z\",\"message\":\"the desired count is within the acceptable range\",\"type\":\"ScalingLimited\",\"status\":\"False\"}],\"currentMetrics\":[{\"resource\":{\"currentAverageValue\":{\"amount\":\"7532544\",\"format\":\"\",\"additionalProperties\":{}},\"name\":\"memory\",\"additionalProperties\":{},\"currentAverageUtilization\":1},\"additionalProperties\":{},\"type\":\"Resource\"},{\"resource\":{\"currentAverageValue\":{\"amount\":\"2\",\"format\":\"m\",\"additionalProperties\":{}},\"name\":\"cpu\",\"additionalProperties\":{},\"currentAverageUtilization\":1},\"additionalProperties\":{},\"type\":\"Resource\"}]}}";

            String jsonAsYaml = "";
        try {
            JsonNode jsonNodeTree = new ObjectMapper().readTree(string);
            jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonAsYaml);
    }

}