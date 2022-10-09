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
import com.cohotz.survey.client.core.model.EngineWeightReq;
import com.cohotz.survey.client.core.model.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * CultureBlockDTO
 */
@JsonPropertyOrder({
  CultureBlockDTO.JSON_PROPERTY_TENANT,
  CultureBlockDTO.JSON_PROPERTY_NAME,
  CultureBlockDTO.JSON_PROPERTY_CODE,
  CultureBlockDTO.JSON_PROPERTY_TYPE,
  CultureBlockDTO.JSON_PROPERTY_DESCRIPTION,
  CultureBlockDTO.JSON_PROPERTY_TARGET,
  CultureBlockDTO.JSON_PROPERTY_ENGINES,
  CultureBlockDTO.JSON_PROPERTY_VERSION,
  CultureBlockDTO.JSON_PROPERTY_LATEST,
  CultureBlockDTO.JSON_PROPERTY_ACTIVE,
  CultureBlockDTO.JSON_PROPERTY_QUESTIONS,
  CultureBlockDTO.JSON_PROPERTY_FORMULA
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-10-08T23:22:50.644014200+05:30[Asia/Calcutta]")
public class CultureBlockDTO {
  public static final String JSON_PROPERTY_TENANT = "tenant";
  private String tenant;

  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_CODE = "code";
  private String code;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    ENPS("ENPS"),
    
    EMPLOYEE_EXPERIENCE("EMPLOYEE_EXPERIENCE"),
    
    PULSE("PULSE"),
    
    RUBICA_CONVERSATION("RUBICA_CONVERSATION"),
    
    DEFAULT("DEFAULT");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_TYPE = "type";
  private TypeEnum type;

  public static final String JSON_PROPERTY_DESCRIPTION = "description";
  private String description;

  public static final String JSON_PROPERTY_TARGET = "target";
  private Integer target;

  public static final String JSON_PROPERTY_ENGINES = "engines";
  private List<EngineWeightReq> engines = null;

  public static final String JSON_PROPERTY_VERSION = "version";
  private Integer version;

  public static final String JSON_PROPERTY_LATEST = "latest";
  private Boolean latest;

  public static final String JSON_PROPERTY_ACTIVE = "active";
  private Boolean active;

  public static final String JSON_PROPERTY_QUESTIONS = "questions";
  private Set<Question> questions = null;

  public static final String JSON_PROPERTY_FORMULA = "formula";
  private String formula;

  public CultureBlockDTO() { 
  }

  public CultureBlockDTO tenant(String tenant) {
    
    this.tenant = tenant;
    return this;
  }

   /**
   * Get tenant
   * @return tenant
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_TENANT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getTenant() {
    return tenant;
  }


  @JsonProperty(JSON_PROPERTY_TENANT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTenant(String tenant) {
    this.tenant = tenant;
  }


  public CultureBlockDTO name(String name) {
    
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


  public CultureBlockDTO code(String code) {
    
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


  public CultureBlockDTO type(TypeEnum type) {
    
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public TypeEnum getType() {
    return type;
  }


  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setType(TypeEnum type) {
    this.type = type;
  }


  public CultureBlockDTO description(String description) {
    
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


  public CultureBlockDTO target(Integer target) {
    
    this.target = target;
    return this;
  }

   /**
   * Get target
   * @return target
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_TARGET)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getTarget() {
    return target;
  }


  @JsonProperty(JSON_PROPERTY_TARGET)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTarget(Integer target) {
    this.target = target;
  }


  public CultureBlockDTO engines(List<EngineWeightReq> engines) {
    
    this.engines = engines;
    return this;
  }

  public CultureBlockDTO addEnginesItem(EngineWeightReq enginesItem) {
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

  public List<EngineWeightReq> getEngines() {
    return engines;
  }


  @JsonProperty(JSON_PROPERTY_ENGINES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEngines(List<EngineWeightReq> engines) {
    this.engines = engines;
  }


  public CultureBlockDTO version(Integer version) {
    
    this.version = version;
    return this;
  }

   /**
   * Get version
   * @return version
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getVersion() {
    return version;
  }


  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVersion(Integer version) {
    this.version = version;
  }


  public CultureBlockDTO latest(Boolean latest) {
    
    this.latest = latest;
    return this;
  }

   /**
   * Get latest
   * @return latest
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_LATEST)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getLatest() {
    return latest;
  }


  @JsonProperty(JSON_PROPERTY_LATEST)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLatest(Boolean latest) {
    this.latest = latest;
  }


  public CultureBlockDTO active(Boolean active) {
    
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


  public CultureBlockDTO questions(Set<Question> questions) {
    
    this.questions = questions;
    return this;
  }

  public CultureBlockDTO addQuestionsItem(Question questionsItem) {
    if (this.questions == null) {
      this.questions = new LinkedHashSet<>();
    }
    this.questions.add(questionsItem);
    return this;
  }

   /**
   * Get questions
   * @return questions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_QUESTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Set<Question> getQuestions() {
    return questions;
  }


  @JsonDeserialize(as = LinkedHashSet.class)
  @JsonProperty(JSON_PROPERTY_QUESTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setQuestions(Set<Question> questions) {
    this.questions = questions;
  }


  public CultureBlockDTO formula(String formula) {
    
    this.formula = formula;
    return this;
  }

   /**
   * Get formula
   * @return formula
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_FORMULA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getFormula() {
    return formula;
  }


  @JsonProperty(JSON_PROPERTY_FORMULA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFormula(String formula) {
    this.formula = formula;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CultureBlockDTO cultureBlockDTO = (CultureBlockDTO) o;
    return Objects.equals(this.tenant, cultureBlockDTO.tenant) &&
        Objects.equals(this.name, cultureBlockDTO.name) &&
        Objects.equals(this.code, cultureBlockDTO.code) &&
        Objects.equals(this.type, cultureBlockDTO.type) &&
        Objects.equals(this.description, cultureBlockDTO.description) &&
        Objects.equals(this.target, cultureBlockDTO.target) &&
        Objects.equals(this.engines, cultureBlockDTO.engines) &&
        Objects.equals(this.version, cultureBlockDTO.version) &&
        Objects.equals(this.latest, cultureBlockDTO.latest) &&
        Objects.equals(this.active, cultureBlockDTO.active) &&
        Objects.equals(this.questions, cultureBlockDTO.questions) &&
        Objects.equals(this.formula, cultureBlockDTO.formula);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tenant, name, code, type, description, target, engines, version, latest, active, questions, formula);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CultureBlockDTO {\n");
    sb.append("    tenant: ").append(toIndentedString(tenant)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    target: ").append(toIndentedString(target)).append("\n");
    sb.append("    engines: ").append(toIndentedString(engines)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    latest: ").append(toIndentedString(latest)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    questions: ").append(toIndentedString(questions)).append("\n");
    sb.append("    formula: ").append(toIndentedString(formula)).append("\n");
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

