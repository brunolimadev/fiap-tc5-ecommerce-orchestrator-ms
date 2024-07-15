package br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.controllers;

import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.models.ms_item.ItemModel;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.ItemService;
import br.com.fiap.fiap_tc5_ecommerce_orchestrator_ms.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@Tag(name = "Item Controller", description = "Customer can manage items through API resources")
public class ItemController {

        private final ItemService itemService;

        private final JwtService jwtService;

        public ItemController(ItemService itemService, JwtService jwtService) {
                this.itemService = itemService;
                this.jwtService = jwtService;
        }

        @Operation(summary = "Create item")
        @ApiResponse(responseCode = "201", description = "Returns a created item")
        @PostMapping
        public ResponseEntity<ItemModel> createItem(
                        @RequestBody ItemModel itemModel,
                        @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {

                var sessionId = jwtService.extractSessionId(jwt);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(itemService.createItem(itemModel, sessionId));

        }

        @Operation(summary = "List all items")
        @ApiResponse(responseCode = "200", description = "Gets list of all items")
        @GetMapping
        public ResponseEntity<List<ItemModel>> getAllItems(
                @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {

                var sessionId = jwtService.extractSessionId(jwt);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(itemService.getItems(sessionId));

        }

        @Operation(summary = "Returns a item by id")
        @ApiResponse(responseCode = "200", description = "Gets a specific item")
        @GetMapping(value = "{item_id}")
        public ResponseEntity<ItemModel> getItem(
                        @PathVariable("item_id") Long id,
                        @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {

                var sessionId = jwtService.extractSessionId(jwt);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(itemService.getItem(id, sessionId));

        }

        @Operation(summary = "Remove a item by id")
        @ApiResponse(responseCode = "200", description = "Returns a removed item")
        @DeleteMapping(value = "{item_id}")
        public ResponseEntity<ItemModel> removeItem(
                        @PathVariable("item_id") Long id,
                        @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {

                var sessionId = jwtService.extractSessionId(jwt);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(itemService.removeItem(id, sessionId));

        }

        @Operation(summary = "Update a item by id")
        @ApiResponse(responseCode = "200", description = "Returns a updated item")
        @PutMapping(value = "/{item_id}")
        public ResponseEntity<ItemModel> updateItem(
                        @PathVariable("item_id") Long id,
                        @RequestBody ItemModel itemModel,
                        @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {

                var sessionId = jwtService.extractSessionId(jwt);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(itemService.updateItem(id, itemModel, sessionId));

        }

}