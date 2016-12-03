package com.amazonaws.mobile;

import com.amazonaws.regions.Regions;

/**
 * This class defines constants for the developer's resource
 * identifiers and API keys. This configuration should not
 * be shared or posted to any public source code repository.
 */
public class AWSConfiguration {
    // AWS MobileHub user agent string
    public static final String AWS_MOBILEHUB_USER_AGENT =
            "MobileHub a6d53093-8884-4f13-9f8f-26e355ff4297 aws-my-sample-app-android-v0.11";
    // AMAZON COGNITO
    public static final Regions AMAZON_COGNITO_REGION =
            Regions.fromName("us-east-1");
    public static final String  AMAZON_COGNITO_IDENTITY_POOL_ID =
            "us-east-1:af846312-d9d1-4007-8825-869fdfc2a3ae";
    // Google Client ID for Web application
    public static final String GOOGLE_CLIENT_ID =
            "7059389209-r36vg011j4d0jvm5u4dcg21f1t5q60fh.apps.googleusercontent.com";
    public static final Regions AMAZON_DYNAMODB_REGION =
                   Regions.fromName("us-east-1");

    // GOOGLE CLOUD MESSAGING SENDER ID
    public static final String GOOGLE_CLOUD_MESSAGING_SENDER_ID =
            "117998035004";
    // SNS PLATFORM APPLICATION ARN
    public static final String AMAZON_SNS_PLATFORM_APPLICATION_ARN =
            "arn:aws:sns:eu-central-1:068793508628:app/GCM/tsgorange_MOBILEHUB_1232536703";
    public static final Regions AMAZON_SNS_REGION =
            Regions.fromName("eu-central-1");
    // SNS DEFAULT TOPIC ARN
    public static final String AMAZON_SNS_DEFAULT_TOPIC_ARN =
            "arn:aws:sns:eu-central-1:068793508628:tsgorange_alldevices_MOBILEHUB_1232536703";
    // SNS PLATFORM TOPIC ARNS
    public static final String[] AMAZON_SNS_TOPIC_ARNS =
            {};

}