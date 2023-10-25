package io.swagger.model;

import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.threeten.bp.LocalDate;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */
@Entity(name = "Transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Getter
@Setter
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-10-25T07:07:42.427263843Z[GMT]")

public class Transaction {
  @JsonProperty("id")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("amount")
  private Double amount = null;

  @JsonProperty("date")
  private Date date = null;

  @JsonProperty("type")
  private String type = null;

  public Transaction id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * The unique identifier of the transaction.
   * 
   * @return id
   **/
  @Schema(description = "The unique identifier of the transaction.")

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Transaction description(String description) {
    this.description = description;
    return this;
  }

  /**
   * A description of the transaction.
   * 
   * @return description
   **/
  @Schema(description = "A description of the transaction.")

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Transaction amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * The transaction amount as a non-negative integer.
   * 
   * @return amount
   **/
  @Schema(description = "The transaction amount as a non-negative integer.")

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Transaction date(Date date) {
    this.date = date;
    return this;
  }

  /**
   * The date of the transaction.
   * 
   * @return date
   **/
  @Schema(description = "The date of the transaction.")

  @Valid
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Transaction type(String type) {
    this.type = type;
    return this;
  }

  /**
   * The type of transaction (credit or debit).
   * 
   * @return type
   **/
  @Schema(description = "The type of transaction (credit or debit).")

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.id, transaction.id) &&
        Objects.equals(this.description, transaction.description) &&
        Objects.equals(this.amount, transaction.amount) &&
        Objects.equals(this.date, transaction.date) &&
        Objects.equals(this.type, transaction.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, amount, date, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  public Transaction(String date2, String type2, double amount2, String description2) {
  }
}
