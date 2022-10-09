package com.cohotz.survey.client.core;

import com.cohotz.survey.client.ApiClient;

import com.cohotz.survey.client.core.model.ApiResponseCultureBlock;
import com.cohotz.survey.client.core.model.ApiResponseErrorDetail;
import com.cohotz.survey.client.core.model.ApiResponseListCultureBlockMin;
import com.cohotz.survey.client.core.model.ApiResponseListQuestion;
import com.cohotz.survey.client.core.model.ApiResponseVoid;
import com.cohotz.survey.client.core.model.CultureBlockDTO;
import com.cohotz.survey.client.core.model.FetchByCode400Response;

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
@Component("com.cohotz.survey.client.core.CultureBlockApi")
public class CultureBlockApi {
    private ApiClient apiClient;

    public CultureBlockApi() {
        this(new ApiClient());
    }

    @Autowired
    public CultureBlockApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Activate Block
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
    public ApiResponseVoid activate1(String tenant, String code) throws RestClientException {
        return activate1WithHttpInfo(tenant, code).getBody();
    }

    /**
     * Activate Block
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
    public ResponseEntity<ApiResponseVoid> activate1WithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling activate1");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling activate1");
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
        return apiClient.invokeAPI("/api/culture/blocks/{code}/activate", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Create Block
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param cultureBlockDTO  (required)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseVoid create4(CultureBlockDTO cultureBlockDTO) throws RestClientException {
        return create4WithHttpInfo(cultureBlockDTO).getBody();
    }

    /**
     * Create Block
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param cultureBlockDTO  (required)
     * @return ResponseEntity&lt;ApiResponseVoid&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseVoid> create4WithHttpInfo(CultureBlockDTO cultureBlockDTO) throws RestClientException {
        Object localVarPostBody = cultureBlockDTO;
        
        // verify the required parameter 'cultureBlockDTO' is set
        if (cultureBlockDTO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'cultureBlockDTO' when calling create4");
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

        ParameterizedTypeReference<ApiResponseVoid> localReturnType = new ParameterizedTypeReference<ApiResponseVoid>() {};
        return apiClient.invokeAPI("/api/culture/blocks", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Add Question to Block
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @param poolQuestionCode  (required)
     * @param position  (required)
     * @param skippable  (optional, default to false)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseVoid createQuestion(String tenant, String code, String poolQuestionCode, Integer position, Boolean skippable) throws RestClientException {
        return createQuestionWithHttpInfo(tenant, code, poolQuestionCode, position, skippable).getBody();
    }

    /**
     * Add Question to Block
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @param poolQuestionCode  (required)
     * @param position  (required)
     * @param skippable  (optional, default to false)
     * @return ResponseEntity&lt;ApiResponseVoid&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseVoid> createQuestionWithHttpInfo(String tenant, String code, String poolQuestionCode, Integer position, Boolean skippable) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling createQuestion");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling createQuestion");
        }
        
        // verify the required parameter 'poolQuestionCode' is set
        if (poolQuestionCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'poolQuestionCode' when calling createQuestion");
        }
        
        // verify the required parameter 'position' is set
        if (position == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'position' when calling createQuestion");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "poolQuestionCode", poolQuestionCode));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "position", position));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "skippable", skippable));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseVoid> localReturnType = new ParameterizedTypeReference<ApiResponseVoid>() {};
        return apiClient.invokeAPI("/api/culture/blocks/{code}/questions", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Deactivate Block
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
    public ApiResponseVoid deactivate1(String tenant, String code) throws RestClientException {
        return deactivate1WithHttpInfo(tenant, code).getBody();
    }

    /**
     * Deactivate Block
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
    public ResponseEntity<ApiResponseVoid> deactivate1WithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling deactivate1");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling deactivate1");
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
        return apiClient.invokeAPI("/api/culture/blocks/{code}/deactivate", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Delete Block
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
    public ApiResponseVoid deleteByCode1(String tenant, String code) throws RestClientException {
        return deleteByCode1WithHttpInfo(tenant, code).getBody();
    }

    /**
     * Delete Block
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
    public ResponseEntity<ApiResponseVoid> deleteByCode1WithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling deleteByCode1");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling deleteByCode1");
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
        return apiClient.invokeAPI("/api/culture/blocks/{code}", HttpMethod.DELETE, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Remove Question from a Block
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @param questionCode  (required)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseVoid deleteQuestion1(String tenant, String code, String questionCode) throws RestClientException {
        return deleteQuestion1WithHttpInfo(tenant, code, questionCode).getBody();
    }

    /**
     * Remove Question from a Block
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @param questionCode  (required)
     * @return ResponseEntity&lt;ApiResponseVoid&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseVoid> deleteQuestion1WithHttpInfo(String tenant, String code, String questionCode) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling deleteQuestion1");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling deleteQuestion1");
        }
        
        // verify the required parameter 'questionCode' is set
        if (questionCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'questionCode' when calling deleteQuestion1");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);
        uriVariables.put("questionCode", questionCode);

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
        return apiClient.invokeAPI("/api/culture/blocks/{code}/questions/{questionCode}", HttpMethod.DELETE, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Search Blocks
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param search  (optional)
     * @param activeOnly  (optional)
     * @return ApiResponseListCultureBlockMin
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseListCultureBlockMin fetchAll4(String tenant, String search, Boolean activeOnly) throws RestClientException {
        return fetchAll4WithHttpInfo(tenant, search, activeOnly).getBody();
    }

    /**
     * Search Blocks
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param search  (optional)
     * @param activeOnly  (optional)
     * @return ResponseEntity&lt;ApiResponseListCultureBlockMin&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseListCultureBlockMin> fetchAll4WithHttpInfo(String tenant, String search, Boolean activeOnly) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling fetchAll4");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "activeOnly", activeOnly));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseListCultureBlockMin> localReturnType = new ParameterizedTypeReference<ApiResponseListCultureBlockMin>() {};
        return apiClient.invokeAPI("/api/culture/blocks", HttpMethod.GET, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Block Details
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return ApiResponseCultureBlock
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseCultureBlock fetchByCode1(String tenant, String code) throws RestClientException {
        return fetchByCode1WithHttpInfo(tenant, code).getBody();
    }

    /**
     * Block Details
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return ResponseEntity&lt;ApiResponseCultureBlock&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseCultureBlock> fetchByCode1WithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling fetchByCode1");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling fetchByCode1");
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

        ParameterizedTypeReference<ApiResponseCultureBlock> localReturnType = new ParameterizedTypeReference<ApiResponseCultureBlock>() {};
        return apiClient.invokeAPI("/api/culture/blocks/{code}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Fetch Block Questions
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return ApiResponseListQuestion
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseListQuestion getQuestions(String tenant, String code) throws RestClientException {
        return getQuestionsWithHttpInfo(tenant, code).getBody();
    }

    /**
     * Fetch Block Questions
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @return ResponseEntity&lt;ApiResponseListQuestion&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseListQuestion> getQuestionsWithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling getQuestions");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling getQuestions");
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

        ParameterizedTypeReference<ApiResponseListQuestion> localReturnType = new ParameterizedTypeReference<ApiResponseListQuestion>() {};
        return apiClient.invokeAPI("/api/culture/blocks/{code}/questions", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Update Block
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @param cultureBlockDTO  (required)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseVoid updateByCode(String tenant, String code, CultureBlockDTO cultureBlockDTO) throws RestClientException {
        return updateByCodeWithHttpInfo(tenant, code, cultureBlockDTO).getBody();
    }

    /**
     * Update Block
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @param cultureBlockDTO  (required)
     * @return ResponseEntity&lt;ApiResponseVoid&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseVoid> updateByCodeWithHttpInfo(String tenant, String code, CultureBlockDTO cultureBlockDTO) throws RestClientException {
        Object localVarPostBody = cultureBlockDTO;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling updateByCode");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling updateByCode");
        }
        
        // verify the required parameter 'cultureBlockDTO' is set
        if (cultureBlockDTO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'cultureBlockDTO' when calling updateByCode");
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

        ParameterizedTypeReference<ApiResponseVoid> localReturnType = new ParameterizedTypeReference<ApiResponseVoid>() {};
        return apiClient.invokeAPI("/api/culture/blocks/{code}", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Update Question in a Block
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @param questionCode  (required)
     * @param position  (required)
     * @param skippable  (optional, default to false)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ApiResponseVoid updateQuestion1(String tenant, String code, String questionCode, Integer position, Boolean skippable) throws RestClientException {
        return updateQuestion1WithHttpInfo(tenant, code, questionCode, position, skippable).getBody();
    }

    /**
     * Update Question in a Block
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param code  (required)
     * @param questionCode  (required)
     * @param position  (required)
     * @param skippable  (optional, default to false)
     * @return ResponseEntity&lt;ApiResponseVoid&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     * @deprecated
     */
    @Deprecated
    public ResponseEntity<ApiResponseVoid> updateQuestion1WithHttpInfo(String tenant, String code, String questionCode, Integer position, Boolean skippable) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling updateQuestion1");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling updateQuestion1");
        }
        
        // verify the required parameter 'questionCode' is set
        if (questionCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'questionCode' when calling updateQuestion1");
        }
        
        // verify the required parameter 'position' is set
        if (position == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'position' when calling updateQuestion1");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);
        uriVariables.put("questionCode", questionCode);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "position", position));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "skippable", skippable));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseVoid> localReturnType = new ParameterizedTypeReference<ApiResponseVoid>() {};
        return apiClient.invokeAPI("/api/culture/blocks/{code}/questions/{questionCode}", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
