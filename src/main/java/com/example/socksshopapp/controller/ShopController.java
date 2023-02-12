package com.example.socksshopapp.controller;

import com.example.socksshopapp.model.Colour;
import com.example.socksshopapp.model.Socks;
import com.example.socksshopapp.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/shop")
@Tag(name = "Носки", description = "CRUD-операции и др. эндпоинты для работы с магазином носков")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    @Operation(
            summary = "Добавление носков в магазин"
    )
    @Parameters(value = {
            @Parameter(name = "size", example = "35"),
            @Parameter(name = "colour", example = "WHITE"),
            @Parameter(name = "cottonPart", example = "100")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Носки добавлены в магазин",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )

                    }
            )
    })
    public ResponseEntity<Socks> addSocks(@RequestBody Socks socks) {
        Socks newSocks = shopService.addSocks(socks);
        return ResponseEntity.ok().body(newSocks);
    }

    @GetMapping
    @Operation(
            summary = "Просмотр всех моделей в наличии ИЛИ поиск моделей по размеру или цвету",
            description = "Можно искать по размеру или цвету или без параметров"
    )
    @Parameters(value = {
            @Parameter(name = "size", example = "38"),
            @Parameter(name = "colour", example = "WHITE"),
            @Parameter(name = "cottonPart", example = "100")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Носки в наличии",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )

                    }
            )
    })
    public ResponseEntity<Socks> getAllSocks(@RequestParam(required = false) Colour colour,
                                             @RequestParam(required = false) Integer size) {
        return null;
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Редактирование товара по номеру товара"
    )
    @Parameters(value = {
            @Parameter(name = "size", example = "38"),
            @Parameter(name = "colour", example = "PINK"),
            @Parameter(name = "cottonPart", example = "100")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о товаре редактирована",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )

                    }
            )
    })
    public ResponseEntity<Socks> editSocks(@PathVariable int id, @RequestBody Socks socks) {
        Socks newSocks = shopService.editSocks(id, socks);
        if (newSocks == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newSocks);
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление товара по номеру товара",
            description = "Нужно искать по номеру"
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Товар удален",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
                            )

                    }
            )
    })
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        if (shopService.deleteById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
