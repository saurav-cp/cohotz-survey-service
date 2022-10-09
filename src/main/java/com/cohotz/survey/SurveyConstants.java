package com.cohotz.survey;

public class SurveyConstants {
    private SurveyConstants() {}
    public static final String USER_ENDPOINT = "/api/users";
    public static final String ROLE_ENDPOINT = "/api/roles";
    public static final String DASHBOARD_ENDPOINT = "/api/dashboard";

    public static final String CULTR_ENGINE_ENDPOINT ="/api/culture/engines";
    public static final String QUESTION_POOL_ENDPOINT = "/api/questions";
    public static final String CULTR_BLOCK_ENDPOINT = "/api/culture/blocks";

    public static final String COHOTZ_SURVEY_ENDPOINT = "/api/surveys";

    /**
     *
     * REST TEMPLATE
     *
     */
    public static final String SURVEY_SERVICE_CLIENT = "cohotz-survey-service";
    public static final String CORE_SERVICE_CLIENT = "cohotz-core-service";
    public static final String USER_SERVICE_CLIENT = "cohotz-profile-service";

    public static final String CORE_SERVICE_API_CLIENT_BEAN = "cohotz-core-service-api-client-bean";
    public static final String CULTURE_BLOCK_API_BEAN = "culture-block-api-bean";
    public static final String QUESTION_POOL_API_BEAN = "question-pool-api-bean";

    public static final String USER_SERVICE_API_CLIENT_BEAN = "cohotz-profile-service-api-client-bean";
    public static final String USER_API_BEAN = "user-api-bean";

    /**
     * Kafka
     */
    public static final String ENGINE_SCORE_RECORD_TOPIC = "cohotz-engine-score-record";
}
