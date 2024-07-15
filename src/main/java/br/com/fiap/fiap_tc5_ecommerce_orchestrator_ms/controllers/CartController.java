package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.controllers;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_cart.CartItemModel;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_cart.CartModel;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
@Tag(name = "Cart Controller", description = "Customer can manage cart through API resources")
public class CartController {

  private final CartService cartService;

  public CartController(
          CartService cartService
  ) {
    
    this.cartService = cartService;

  }

  @Operation(summary = "Add cart item")
  @ApiResponse(
          responseCode = "201",
          description = "Returns a created Cart"
  )
  @PostMapping
  public ResponseEntity<CartModel> addCartItem(
          @RequestBody CartItemModel cartItemModel,
          @RequestHeader("session-id") String sessionId
  ) {

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(cartService.addCartItem(cartItemModel, sessionId));

  }

  @Operation(summary = "Remove cart item by id")
  @ApiResponse(responseCode = "200", description = "Returns a updated cart")
  @DeleteMapping(value = "{item_id}")
  public ResponseEntity<CartModel> removeCartItem(
          @PathVariable("item_id") Long id,
          @RequestHeader("session-id") String sessionId
  ) {

    return  ResponseEntity
            .status(HttpStatus.OK)
            .body(cartService.removeCartItem(id, sessionId));

  }

}