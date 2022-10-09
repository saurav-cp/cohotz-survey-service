package com.cohotz.survey.client.core;

import com.cohotz.survey.client.ApiClient;

import com.cohotz.survey.client.core.model.ApiResponseErrorDetail;
import com.cohotz.survey.client.core.model.ApiResponseListCultureEngine;
import com.cohotz.survey.client.core.model.ApiResponseVoid;
import com.cohotz.survey.client.core.model.CultureEngineDTO;
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
@Component("com.cohotz.survey.client.core.CultureEngineApi")
public class CultureEngineApi {
    private ApiClient apiClient;

    public CultureEngineApi() {
        this(new ApiClient());
    }

    @Autowired
    public CultureEngineApi(ApiClient apiClient) {
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
     * @param tenant  (required)
     * @param code  (required)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseVoid activate(String tenant, String code) throws RestClientException {
        return activateWithHttpInfo(tenant, code).getBody();
    }

    /**
     * 
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
    public ResponseEntity<ApiResponseVoid> activateWithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling activate");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling activate");
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
        return apiClient.invokeAPI("/api/culture/engines/{code}/hide", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param cultureEngineDTO  (required)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseVoid create3(CultureEngineDTO cultureEngineDTO) throws RestClientException {
        return create3WithHttpInfo(cultureEngineDTO).getBody();
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param cultureEngineDTO  (required)
     * @return ResponseEntity&lt;ApiResponseVoid&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseVoid> create3WithHttpInfo(CultureEngineDTO cultureEngineDTO) throws RestClientException {
        Object localVarPostBody = cultureEngineDTO;
        
        // verify the required parameter 'cultureEngineDTO' is set
        if (cultureEngineDTO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'cultureEngineDTO' when calling create3");
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
        return apiClient.invokeAPI("/api/culture/engines", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
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
    public ApiResponseVoid deactivate(String tenant, String code) throws RestClientException {
        return deactivateWithHttpInfo(tenant, code).getBody();
    }

    /**
     * 
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
    public ResponseEntity<ApiResponseVoid> deactivateWithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling deactivate");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling deactivate");
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
        return apiClient.invokeAPI("/api/culture/engines/{code}/unhide", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
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
    public ApiResponseVoid delete1(String tenant, String code) throws RestClientException {
        return delete1WithHttpInfo(tenant, code).getBody();
    }

    /**
     * 
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
    public ResponseEntity<ApiResponseVoid> delete1WithHttpInfo(String tenant, String code) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling delete1");
        }
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling delete1");
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
        return apiClient.invokeAPI("/api/culture/engines/{code}", HttpMethod.DELETE, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param search  (optional)
     * @return ApiResponseListCultureEngine
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseListCultureEngine fetchAll3(String tenant, String search) throws RestClientException {
        return fetchAll3WithHttpInfo(tenant, search).getBody();
    }

    /**
     * 
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param search  (optional)
     * @return ResponseEntity&lt;ApiResponseListCultureEngine&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseListCultureEngine> fetchAll3WithHttpInfo(String tenant, String search) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling fetchAll3");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseListCultureEngine> localReturnType = new ParameterizedTypeReference<ApiResponseListCultureEngine>() {};
        return apiClient.invokeAPI("/api/culture/engines", HttpMethod.GET, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
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
     * @param cultureEngineDTO  (required)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseVoid update2(String code, CultureEngineDTO cultureEngineDTO) throws RestClientException {
        return update2WithHttpInfo(code, cultureEngineDTO).getBody();
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
     * @param cultureEngineDTO  (required)
     * @return ResponseEntity&lt;ApiResponseVoid&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseVoid> update2WithHttpInfo(String code, CultureEngineDTO cultureEngineDTO) throws RestClientException {
        Object localVarPostBody = cultureEngineDTO;
        
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling update2");
        }
        
        // verify the required parameter 'cultureEngineDTO' is set
        if (cultureEngineDTO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'cultureEngineDTO' when calling update2");
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

        ParameterizedTypeReference<ApiResponseVoid> localReturnType = new ParameterizedTypeReference<ApiResponseVoid>() {};
        return apiClient.invokeAPI("/api/culture/engines/{code}", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
