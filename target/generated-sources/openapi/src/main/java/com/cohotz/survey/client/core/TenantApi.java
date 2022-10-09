package com.cohotz.survey.client.core;

import com.cohotz.survey.client.ApiClient;

import com.cohotz.survey.client.core.model.ApiResponseErrorDetail;
import com.cohotz.survey.client.core.model.ApiResponseListTenantCultureEngine;
import com.cohotz.survey.client.core.model.ApiResponseSetTenantRes;
import com.cohotz.survey.client.core.model.ApiResponseTenantRes;
import com.cohotz.survey.client.core.model.ApiResponseVoid;
import com.cohotz.survey.client.core.model.FetchByCode400Response;
import com.cohotz.survey.client.core.model.TenantDTO;

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
@Component("com.cohotz.survey.client.core.TenantApi")
public class TenantApi {
    private ApiClient apiClient;

    public TenantApi() {
        this(new ApiClient());
    }

    @Autowired
    public TenantApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenantDTO  (required)
     * @return ApiResponseTenantRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseTenantRes create(TenantDTO tenantDTO) throws RestClientException {
        return createWithHttpInfo(tenantDTO).getBody();
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenantDTO  (required)
     * @return ResponseEntity&lt;ApiResponseTenantRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseTenantRes> createWithHttpInfo(TenantDTO tenantDTO) throws RestClientException {
        Object localVarPostBody = tenantDTO;
        
        // verify the required parameter 'tenantDTO' is set
        if (tenantDTO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenantDTO' when calling create");
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

        ParameterizedTypeReference<ApiResponseTenantRes> localReturnType = new ParameterizedTypeReference<ApiResponseTenantRes>() {};
        return apiClient.invokeAPI("/api/tenants", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ApiResponseTenantRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseTenantRes deleteByCode(String code) throws RestClientException {
        return deleteByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;ApiResponseTenantRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseTenantRes> deleteByCodeWithHttpInfo(String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling deleteByCode");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseTenantRes> localReturnType = new ParameterizedTypeReference<ApiResponseTenantRes>() {};
        return apiClient.invokeAPI("/api/tenants/{code}", HttpMethod.DELETE, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param search  (optional)
     * @return ApiResponseSetTenantRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseSetTenantRes fetchAll(String search) throws RestClientException {
        return fetchAllWithHttpInfo(search).getBody();
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param search  (optional)
     * @return ResponseEntity&lt;ApiResponseSetTenantRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseSetTenantRes> fetchAllWithHttpInfo(String search) throws RestClientException {
        Object localVarPostBody = null;
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseSetTenantRes> localReturnType = new ParameterizedTypeReference<ApiResponseSetTenantRes>() {};
        return apiClient.invokeAPI("/api/tenants", HttpMethod.GET, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ApiResponseTenantRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseTenantRes fetchByCode(String code) throws RestClientException {
        return fetchByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;ApiResponseTenantRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseTenantRes> fetchByCodeWithHttpInfo(String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling fetchByCode");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseTenantRes> localReturnType = new ParameterizedTypeReference<ApiResponseTenantRes>() {};
        return apiClient.invokeAPI("/api/tenants/{code}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param name  (required)
     * @return ApiResponseTenantRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseTenantRes fetchByName(String name) throws RestClientException {
        return fetchByNameWithHttpInfo(name).getBody();
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param name  (required)
     * @return ResponseEntity&lt;ApiResponseTenantRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseTenantRes> fetchByNameWithHttpInfo(String name) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'name' when calling fetchByName");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseTenantRes> localReturnType = new ParameterizedTypeReference<ApiResponseTenantRes>() {};
        return apiClient.invokeAPI("/api/tenants/details", HttpMethod.GET, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ApiResponseListTenantCultureEngine
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseListTenantCultureEngine fetchEngines(String code) throws RestClientException {
        return fetchEnginesWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;ApiResponseListTenantCultureEngine&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseListTenantCultureEngine> fetchEnginesWithHttpInfo(String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling fetchEngines");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseListTenantCultureEngine> localReturnType = new ParameterizedTypeReference<ApiResponseListTenantCultureEngine>() {};
        return apiClient.invokeAPI("/api/tenants/{code}/engines", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Initialize a new tenant from existing tenant
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param source  (required)
     * @param destination  (required)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseVoid initialize(String source, String destination) throws RestClientException {
        return initializeWithHttpInfo(source, destination).getBody();
    }

    /**
     * Initialize a new tenant from existing tenant
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param source  (required)
     * @param destination  (required)
     * @return ResponseEntity&lt;ApiResponseVoid&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseVoid> initializeWithHttpInfo(String source, String destination) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'source' is set
        if (source == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'source' when calling initialize");
        }
        
        // verify the required parameter 'destination' is set
        if (destination == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'destination' when calling initialize");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source", source));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "destination", destination));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseVoid> localReturnType = new ParameterizedTypeReference<ApiResponseVoid>() {};
        return apiClient.invokeAPI("/api/tenants/initialize", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @param tenantDTO  (required)
     * @return ApiResponseTenantRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseTenantRes update(String code, TenantDTO tenantDTO) throws RestClientException {
        return updateWithHttpInfo(code, tenantDTO).getBody();
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @param tenantDTO  (required)
     * @return ResponseEntity&lt;ApiResponseTenantRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseTenantRes> updateWithHttpInfo(String code, TenantDTO tenantDTO) throws RestClientException {
        Object localVarPostBody = tenantDTO;
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling update");
        }
        
        // verify the required parameter 'tenantDTO' is set
        if (tenantDTO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenantDTO' when calling update");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);

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

        ParameterizedTypeReference<ApiResponseTenantRes> localReturnType = new ParameterizedTypeReference<ApiResponseTenantRes>() {};
        return apiClient.invokeAPI("/api/tenants/{code}", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @param engine  (required)
     * @param target  (required)
     * @return ApiResponseTenantRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseTenantRes updateEngineTarget(String code, String engine, Integer target) throws RestClientException {
        return updateEngineTargetWithHttpInfo(code, engine, target).getBody();
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @param engine  (required)
     * @param target  (required)
     * @return ResponseEntity&lt;ApiResponseTenantRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseTenantRes> updateEngineTargetWithHttpInfo(String code, String engine, Integer target) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling updateEngineTarget");
        }
        
        // verify the required parameter 'engine' is set
        if (engine == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'engine' when calling updateEngineTarget");
        }
        
        // verify the required parameter 'target' is set
        if (target == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'target' when calling updateEngineTarget");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);
        uriVariables.put("engine", engine);
        uriVariables.put("target", target);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseTenantRes> localReturnType = new ParameterizedTypeReference<ApiResponseTenantRes>() {};
        return apiClient.invokeAPI("/api/tenants/{code}/engines/{engine}/target/{target}", HttpMethod.PATCH, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @param block  (required)
     * @param target  (required)
     * @return ApiResponseTenantRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseTenantRes updateExperienceTarget(String code, String block, Integer target) throws RestClientException {
        return updateExperienceTargetWithHttpInfo(code, block, target).getBody();
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param code  (required)
     * @param block  (required)
     * @param target  (required)
     * @return ResponseEntity&lt;ApiResponseTenantRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseTenantRes> updateExperienceTargetWithHttpInfo(String code, String block, Integer target) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling updateExperienceTarget");
        }
        
        // verify the required parameter 'block' is set
        if (block == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'block' when calling updateExperienceTarget");
        }
        
        // verify the required parameter 'target' is set
        if (target == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'target' when calling updateExperienceTarget");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("code", code);
        uriVariables.put("block", block);
        uriVariables.put("target", target);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseTenantRes> localReturnType = new ParameterizedTypeReference<ApiResponseTenantRes>() {};
        return apiClient.invokeAPI("/api/tenants/{code}/experiences/{block}/target/{target}", HttpMethod.PATCH, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
