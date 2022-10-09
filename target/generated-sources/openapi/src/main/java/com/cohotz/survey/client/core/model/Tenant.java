/*
 * Cohotz Core Service
 * Cohotz Core Service
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.cohotz.survey.client.core.model;

import java.util.Objects;
import java.util.Arrays;
import com.cohotz.survey.client.core.model.Subscription;
import com.cohotz.survey.client.core.model.TenantCultureBlock;
import com.cohotz.survey.client.core.model.TenantCultureEngine;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Tenant
 */
@JsonPropertyOrder({
  Tenant.JSON_PROPERTY_ID,
  Tenant.JSON_PROPERTY_NAME,
  Tenant.JSON_PROPERTY_CODE,
  Tenant.JSON_PROPERTY_DESCRIPTION,
  Tenant.JSON_PROPERTY_ENGINES,
  Tenant.JSON_PROPERTY_BLOCKS,
  Tenant.JSON_PROPERTY_SUBSCRIPTION,
  Tenant.JSON_PROPERTY_CREATED_TS,
  Tenant.JSON_PROPERTY_ACTIVE
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-10-08T23:22:50.644014200+05:30[Asia/Calcutta]")
public class Tenant {
  public static final String JSON_PROPERTY_ID = "id";
  private String id;

  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_CODE = "code";
  private String code;

  public static final String JSON_PROPERTY_DESCRIPTION = "description";
  private String description;

  public static final String JSON_PROPERTY_ENGINES = "engines";
  private List<TenantCultureEngine> engines = null;

  public static final String JSON_PROPERTY_BLOCKS = "blocks";
  private List<TenantCultureBlock> blocks = null;

  public static final String JSON_PROPERTY_SUBSCRIPTION = "subscription";
  private Subscription subscription;

  public static final String JSON_PROPERTY_CREATED_TS = "created_ts";
  private LocalDateTime createdTs;

  public static final String JSON_PROPERTY_ACTIVE = "active";
  private Boolean active;

  public Tenant() { 
  }

  public Tenant id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getId() {
    return id;
  }


  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(String id) {
    this.id = id;
  }


  public Tenant name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getName() {
    return name;
  }


  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setName(String name) {
    this.name = name;
  }


  public Tenant code(String code) {
    
    this.code = code;
    return this;
  }

   /**
   * Get code
   * @return code
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getCode() {
    return code;
  }


  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCode(String code) {
    this.code = code;
  }


  public Tenant description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_DESCRIPTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getDescription() {
    return description;
  }


  @JsonProperty(JSON_PROPERTY_DESCRIPTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDescription(String description) {
    this.description = description;
  }


  public Tenant engines(List<TenantCultureEngine> engines) {
    
    this.engines = engines;
    return this;
  }

  public Tenant addEnginesItem(TenantCultureEngine enginesItem) {
    if (this.engines == null) {
      this.engines = new ArrayList<>();
    }
    this.engines.add(enginesItem);
    return this;
  }

   /**
   * Get engines
   * @return engines
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_ENGINES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<TenantCultureEngine> getEngines() {
    return engines;
  }


  @JsonProperty(JSON_PROPERTY_ENGINES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEngines(List<TenantCultureEngine> engines) {
    this.engines = engines;
  }


  public Tenant blocks(List<TenantCultureBlock> blocks) {
    
    this.blocks = blocks;
    return this;
  }

  public Tenant addBlocksItem(TenantCultureBlock blocksItem) {
    if (this.blocks == null) {
      this.blocks = new ArrayList<>();
    }
    this.blocks.add(blocksItem);
    return this;
  }

   /**
   * Get blocks
   * @return blocks
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_BLOCKS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<TenantCultureBlock> getBlocks() {
    return blocks;
  }


  @JsonProperty(JSON_PROPERTY_BLOCKS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBlocks(List<TenantCultureBlock> blocks) {
    this.blocks = blocks;
  }


  public Tenant subscription(Subscription subscription) {
    
    this.subscription = subscription;
    return this;
  }

   /**
   * Get subscription
   * @return subscription
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_SUBSCRIPTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Subscription getSubscription() {
    return subscription;
  }


  @JsonProperty(JSON_PROPERTY_SUBSCRIPTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSubscription(Subscription subscription) {
    this.subscription = subscription;
  }


  public Tenant createdTs(LocalDateTime createdTs) {
    
    this.createdTs = createdTs;
    return this;
  }

   /**
   * Get createdTs
   * @return createdTs
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_CREATED_TS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public LocalDateTime getCreatedTs() {
    return createdTs;
  }


  @JsonProperty(JSON_PROPERTY_CREATED_TS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreatedTs(LocalDateTime createdTs) {
    this.createdTs = createdTs;
  }


  public Tenant active(Boolean active) {
    
    this.active = active;
    return this;
  }

   /**
   * Get active
   * @return active
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_ACTIVE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getActive() {
    return active;
  }


  @JsonProperty(JSON_PROPERTY_ACTIVE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setActive(Boolean active) {
    this.active = active;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tenant tenant = (Tenant) o;
    return Objects.equals(this.id, tenant.id) &&
        Objects.equals(this.name, tenant.name) &&
        Objects.equals(this.code, tenant.code) &&
        Objects.equals(this.description, tenant.description) &&
        Objects.equals(this.engines, tenant.engines) &&
        Objects.equals(this.blocks, tenant.blocks) &&
        Objects.equals(this.subscription, tenant.subscription) &&
        Objects.equals(this.createdTs, tenant.createdTs) &&
        Objects.equals(this.active, tenant.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, code, description, engines, blocks, subscription, createdTs, active);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Tenant {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    engines: ").append(toIndentedString(engines)).append("\n");
    sb.append("    blocks: ").append(toIndentedString(blocks)).append("\n");
    sb.append("    subscription: ").append(toIndentedString(subscription)).append("\n");
    sb.append("    createdTs: ").append(toIndentedString(createdTs)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

