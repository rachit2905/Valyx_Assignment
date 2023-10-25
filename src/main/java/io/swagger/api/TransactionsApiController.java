package io.swagger.api;

import org.threeten.bp.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.Service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-10-25T07:07:42.427263843Z[GMT]")
@RestController
public class TransactionsApiController implements TransactionsApi {
    @Autowired
    TransactionService transactionService;
    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> transactionsGet(
            @Parameter(in = ParameterIn.QUERY, description = "Page number", schema = @Schema()) @Valid @RequestParam(value = "page", required = false) Integer page,
            @Parameter(in = ParameterIn.QUERY, description = "Number of transactions per page", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        String accept = request.getHeader("Accept");
        transactionService.getAllTransactions();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> transactionsSearchGet(
            @NotNull @Parameter(in = ParameterIn.QUERY, description = "Start date for the search", required = true, schema = @Schema()) @Valid @RequestParam(value = "startDate", required = true) Date startDate,
            @NotNull @Parameter(in = ParameterIn.QUERY, description = "End date for the search", required = true, schema = @Schema()) @Valid @RequestParam(value = "endDate", required = true) Date endDate) {
        String accept = request.getHeader("Accept");
        transactionService.getTransactionsBetweenDates(startDate, endDate);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> transactionsTransactionIdGet(
            @Parameter(in = ParameterIn.PATH, description = "ID of the transaction to retrieve", required = true, schema = @Schema()) @PathVariable("transactionId") Integer transactionId) {
        String accept = request.getHeader("Accept");
        transactionService.getTransactionById(transactionId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
