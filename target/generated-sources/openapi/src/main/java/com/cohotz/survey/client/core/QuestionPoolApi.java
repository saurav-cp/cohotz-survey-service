package com.cohotz.survey.client.core;

import com.cohotz.survey.client.ApiClient;

import com.cohotz.survey.client.core.model.ApiResponseChoiceBasedQuestion;
import com.cohotz.survey.client.core.model.ApiResponseErrorDetail;
import com.cohotz.survey.client.core.model.ApiResponseListQuestionMinRes;
import com.cohotz.survey.client.core.model.ApiResponsePoolQuestion;
import com.cohotz.survey.client.core.model.ApiResponseVoid;
import com.cohotz.survey.client.core.model.FetchByCode400Response;
import com.cohotz.survey.client.core.model.PoolQuestionRes;
import com.cohotz.survey.client.core.model.UpdateQuestionRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-10-08T23:22:50.644014200+05:30[Asia/Calcutta]")
@Component("com.cohotz.survey.client.core.QuestionPoolApi")
public class QuestionPoolApi {
    private ApiClient apiClient;

    public QuestionPoolApi() {
        this(new ApiClient());
    }

    @Autowired
    public QuestionPoolApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Activate a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return ApiResponsePoolQuestion
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponsePoolQuestion activeQuestion(String tenant, String code) throws RestClientException {
        return activeQuestionWithHttpInfo(tenant, code).getBody();
    }

    /**
     * Activate a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return ResponseEntity&lt;ApiResponsePoolQuestion&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponsePoolQuestion> activeQuestionWithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling activeQuestion");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling activeQuestion");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponsePoolQuestion> localReturnType = new ParameterizedTypeReference<ApiResponsePoolQuestion>() {};
        return apiClient.invokeAPI("/api/questions/{code}/activate", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Create a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param updateQuestionRequest  (required)
     * @return ApiResponseChoiceBasedQuestion
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseChoiceBasedQuestion create2(UpdateQuestionRequest updateQuestionRequest) throws RestClientException {
        return create2WithHttpInfo(updateQuestionRequest).getBody();
    }

    /**
     * Create a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param updateQuestionRequest  (required)
     * @return ResponseEntity&lt;ApiResponseChoiceBasedQuestion&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseChoiceBasedQuestion> create2WithHttpInfo(UpdateQuestionRequest updateQuestionRequest) throws RestClientException {
        Object localVarPostBody = updateQuestionRequest;
        
        // verify the required parameter 'updateQuestionRequest' is set
        if (updateQuestionRequest == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'updateQuestionRequest' when calling create2");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseChoiceBasedQuestion> localReturnType = new ParameterizedTypeReference<ApiResponseChoiceBasedQuestion>() {};
        return apiClient.invokeAPI("/api/questions", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Deactivate a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return ApiResponsePoolQuestion
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponsePoolQuestion deactivateQuestion(String tenant, String code) throws RestClientException {
        return deactivateQuestionWithHttpInfo(tenant, code).getBody();
    }

    /**
     * Deactivate a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return ResponseEntity&lt;ApiResponsePoolQuestion&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponsePoolQuestion> deactivateQuestionWithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling deactivateQuestion");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling deactivateQuestion");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponsePoolQuestion> localReturnType = new ParameterizedTypeReference<ApiResponsePoolQuestion>() {};
        return apiClient.invokeAPI("/api/questions/{code}/deactivate", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Delete a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseVoid deleteQuestion(String tenant, String code) throws RestClientException {
        return deleteQuestionWithHttpInfo(tenant, code).getBody();
    }

    /**
     * Delete a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return ResponseEntity&lt;ApiResponseVoid&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseVoid> deleteQuestionWithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling deleteQuestion");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling deleteQuestion");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseVoid> localReturnType = new ParameterizedTypeReference<ApiResponseVoid>() {};
        return apiClient.invokeAPI("/api/questions/{code}", HttpMethod.DELETE, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Search Questions
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param search  (optional)
     * @param engine  (optional)
     * @return ApiResponseListQuestionMinRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseListQuestionMinRes fetchAll2(String tenant, String search, String engine) throws RestClientException {
        return fetchAll2WithHttpInfo(tenant, search, engine).getBody();
    }

    /**
     * Search Questions
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param search  (optional)
     * @param engine  (optional)
     * @return ResponseEntity&lt;ApiResponseListQuestionMinRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseListQuestionMinRes> fetchAll2WithHttpInfo(String tenant, String search, String engine) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling fetchAll2");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "engine", engine));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseListQuestionMinRes> localReturnType = new ParameterizedTypeReference<ApiResponseListQuestionMinRes>() {};
        return apiClient.invokeAPI("/api/questions", HttpMethod.GET, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Details of a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return PoolQuestionRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PoolQuestionRes questionDetails(String tenant, String code) throws RestClientException {
        return questionDetailsWithHttpInfo(tenant, code).getBody();
    }

    /**
     * Details of a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return ResponseEntity&lt;PoolQuestionRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PoolQuestionRes> questionDetailsWithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling questionDetails");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling questionDetails");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<PoolQuestionRes> localReturnType = new ParameterizedTypeReference<PoolQuestionRes>() {};
        return apiClient.invokeAPI("/api/questions/{code}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Update a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @param updateQuestionRequest  (required)
     * @return ApiResponsePoolQuestion
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponsePoolQuestion updateQuestion(String tenant, String code, UpdateQuestionRequest updateQuestionRequest) throws RestClientException {
        return updateQuestionWithHttpInfo(tenant, code, updateQuestionRequest).getBody();
    }

    /**
     * Update a Question
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @param updateQuestionRequest  (required)
     * @return ResponseEntity&lt;ApiResponsePoolQuestion&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponsePoolQuestion> updateQuestionWithHttpInfo(String tenant, String code, UpdateQuestionRequest updateQuestionRequest) throws RestClientException {
        Object localVarPostBody = updateQuestionRequest;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling updateQuestion");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling updateQuestion");
        }
        
        // verify the required parameter 'updateQuestionRequest' is set
        if (updateQuestionRequest == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'updateQuestionRequest' when calling updateQuestion");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponsePoolQuestion> localReturnType = new ParameterizedTypeReference<ApiResponsePoolQuestion>() {};
        return apiClient.invokeAPI("/api/questions/{code}", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
