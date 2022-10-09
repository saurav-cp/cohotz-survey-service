package com.cohotz.survey.client.profile;

import com.cohotz.survey.client.ApiClient;

import com.cohotz.survey.client.profile.model.ApiResponseErrorDetail;
import com.cohotz.survey.client.profile.model.ApiResponseListString;
import com.cohotz.survey.client.profile.model.ApiResponseListUserMinRes;
import com.cohotz.survey.client.profile.model.ApiResponseListUserRes;
import com.cohotz.survey.client.profile.model.ApiResponseUserRes;
import com.cohotz.survey.client.profile.model.ApiResponseVoid;
import com.cohotz.survey.client.profile.model.GetOne400Response;
import com.cohotz.survey.client.profile.model.UserDTO;

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

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-10-08T23:22:51.924706700+05:30[Asia/Calcutta]")
@Component("com.cohotz.survey.client.profile.UserApi")
public class UserApi {
    private ApiClient apiClient;

    public UserApi() {
        this(new ApiClient());
    }

    @Autowired
    public UserApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an existing User of a tenant
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param id  (required)
     * @param tenant  (required)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseVoid delete(String id, String tenant) throws RestClientException {
        return deleteWithHttpInfo(id, tenant).getBody();
    }

    /**
     * Delete an existing User of a tenant
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param id  (required)
     * @param tenant  (required)
     * @return ResponseEntity&lt;ApiResponseVoid&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseVoid> deleteWithHttpInfo(String id, String tenant) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling delete");
        }
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling delete");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);

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
        return apiClient.invokeAPI("/api/users/{id}", HttpMethod.DELETE, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Delete all Users of a tenant
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @return ApiResponseVoid
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseVoid deleteByTenant(String tenant) throws RestClientException {
        return deleteByTenantWithHttpInfo(tenant).getBody();
    }

    /**
     * Delete all Users of a tenant
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @return ResponseEntity&lt;ApiResponseVoid&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseVoid> deleteByTenantWithHttpInfo(String tenant) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling deleteByTenant");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("tenant", tenant);

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

        ParameterizedTypeReference<ApiResponseVoid> localReturnType = new ParameterizedTypeReference<ApiResponseVoid>() {};
        return apiClient.invokeAPI("/api/users/tenant/{tenant}", HttpMethod.DELETE, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * User Details from tenant and email
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param email  (required)
     * @param tenant  (required)
     * @return ApiResponseUserRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseUserRes findByEmailAndTenant(String email, String tenant) throws RestClientException {
        return findByEmailAndTenantWithHttpInfo(email, tenant).getBody();
    }

    /**
     * User Details from tenant and email
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param email  (required)
     * @param tenant  (required)
     * @return ResponseEntity&lt;ApiResponseUserRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseUserRes> findByEmailAndTenantWithHttpInfo(String email, String tenant) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'email' is set
        if (email == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'email' when calling findByEmailAndTenant");
        }
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling findByEmailAndTenant");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "email", email));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseUserRes> localReturnType = new ParameterizedTypeReference<ApiResponseUserRes>() {};
        return apiClient.invokeAPI("/api/users/details", HttpMethod.GET, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * User Reporting Hierarchy
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param email  (required)
     * @param tenant  (required)
     * @return ApiResponseListString
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseListString findHierarchyByEmailAndTenant(String email, String tenant) throws RestClientException {
        return findHierarchyByEmailAndTenantWithHttpInfo(email, tenant).getBody();
    }

    /**
     * User Reporting Hierarchy
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param email  (required)
     * @param tenant  (required)
     * @return ResponseEntity&lt;ApiResponseListString&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseListString> findHierarchyByEmailAndTenantWithHttpInfo(String email, String tenant) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'email' is set
        if (email == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'email' when calling findHierarchyByEmailAndTenant");
        }
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling findHierarchyByEmailAndTenant");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "email", email));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tenant", tenant));

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ApiResponseListString> localReturnType = new ParameterizedTypeReference<ApiResponseListString>() {};
        return apiClient.invokeAPI("/api/users/hierarchy", HttpMethod.GET, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * User Details
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param id  (required)
     * @param tenant  (required)
     * @return ApiResponseUserRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseUserRes getOne(String id, String tenant) throws RestClientException {
        return getOneWithHttpInfo(id, tenant).getBody();
    }

    /**
     * User Details
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param id  (required)
     * @param tenant  (required)
     * @return ResponseEntity&lt;ApiResponseUserRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseUserRes> getOneWithHttpInfo(String id, String tenant) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getOne");
        }
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling getOne");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);

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

        ParameterizedTypeReference<ApiResponseUserRes> localReturnType = new ParameterizedTypeReference<ApiResponseUserRes>() {};
        return apiClient.invokeAPI("/api/users/{id}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Fetch All Users
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param search  (optional)
     * @return ApiResponseListUserMinRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseListUserMinRes listUser(String tenant, String search) throws RestClientException {
        return listUserWithHttpInfo(tenant, search).getBody();
    }

    /**
     * Fetch All Users
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param tenant  (required)
     * @param search  (optional)
     * @return ResponseEntity&lt;ApiResponseListUserMinRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseListUserMinRes> listUserWithHttpInfo(String tenant, String search) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling listUser");
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

        ParameterizedTypeReference<ApiResponseListUserMinRes> localReturnType = new ParameterizedTypeReference<ApiResponseListUserMinRes>() {};
        return apiClient.invokeAPI("/api/users", HttpMethod.GET, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Fetch all users with param reportingToEmail
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param reportingToEmail  (required)
     * @param tenant  (required)
     * @return ApiResponseListUserRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseListUserRes listUsersReportingTo(String reportingToEmail, String tenant) throws RestClientException {
        return listUsersReportingToWithHttpInfo(reportingToEmail, tenant).getBody();
    }

    /**
     * Fetch all users with param reportingToEmail
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param reportingToEmail  (required)
     * @param tenant  (required)
     * @return ResponseEntity&lt;ApiResponseListUserRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseListUserRes> listUsersReportingToWithHttpInfo(String reportingToEmail, String tenant) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'reportingToEmail' is set
        if (reportingToEmail == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'reportingToEmail' when calling listUsersReportingTo");
        }
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling listUsersReportingTo");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("reportingToEmail", reportingToEmail);

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

        ParameterizedTypeReference<ApiResponseListUserRes> localReturnType = new ParameterizedTypeReference<ApiResponseListUserRes>() {};
        return apiClient.invokeAPI("/api/users/reporting/{reportingToEmail}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Create a new user/Account
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param userDTO  (required)
     * @return ApiResponseUserRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseUserRes saveUser(UserDTO userDTO) throws RestClientException {
        return saveUserWithHttpInfo(userDTO).getBody();
    }

    /**
     * Create a new user/Account
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param userDTO  (required)
     * @return ResponseEntity&lt;ApiResponseUserRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseUserRes> saveUserWithHttpInfo(UserDTO userDTO) throws RestClientException {
        Object localVarPostBody = userDTO;
        
        // verify the required parameter 'userDTO' is set
        if (userDTO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userDTO' when calling saveUser");
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

        ParameterizedTypeReference<ApiResponseUserRes> localReturnType = new ParameterizedTypeReference<ApiResponseUserRes>() {};
        return apiClient.invokeAPI("/api/users", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Update an User
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param id  (required)
     * @param tenant  (required)
     * @param userDTO  (required)
     * @return ApiResponseUserRes
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ApiResponseUserRes update(String id, String tenant, UserDTO userDTO) throws RestClientException {
        return updateWithHttpInfo(id, tenant, userDTO).getBody();
    }

    /**
     * Update an User
     * 
     * <p><b>500</b> - Internal Server Error
     * <p><b>400</b> - Bad Request
     * <p><b>403</b> - Forbidden
     * <p><b>401</b> - Unauthorized
     * <p><b>200</b> - OK
     * @param id  (required)
     * @param tenant  (required)
     * @param userDTO  (required)
     * @return ResponseEntity&lt;ApiResponseUserRes&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ApiResponseUserRes> updateWithHttpInfo(String id, String tenant, UserDTO userDTO) throws RestClientException {
        Object localVarPostBody = userDTO;
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling update");
        }
        
        // verify the required parameter 'tenant' is set
        if (tenant == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tenant' when calling update");
        }
        
        // verify the required parameter 'userDTO' is set
        if (userDTO == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userDTO' when calling update");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);

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

        ParameterizedTypeReference<ApiResponseUserRes> localReturnType = new ParameterizedTypeReference<ApiResponseUserRes>() {};
        return apiClient.invokeAPI("/api/users/{id}", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
