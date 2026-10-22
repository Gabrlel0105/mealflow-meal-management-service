package com.gabrielmamani.mealmanagement.meals.interfaces.rest;

import com.gabrielmamani.mealmanagement.meals.domain.model.commands.DeleteMealCommand;
import com.gabrielmamani.mealmanagement.meals.domain.model.queries.GetAllAvailableMealsQuery;
import com.gabrielmamani.mealmanagement.meals.domain.model.queries.GetMealByIdQuery;
import com.gabrielmamani.mealmanagement.meals.domain.model.queries.GetMealsByMealTypeQuery;
import com.gabrielmamani.mealmanagement.meals.domain.model.queries.GetMealsByProviderIdQuery;
import com.gabrielmamani.mealmanagement.meals.domain.model.queries.GetMyMealsQuery;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.MealType;
import com.gabrielmamani.mealmanagement.meals.application.commandservices.MealCommandService;
import com.gabrielmamani.mealmanagement.meals.application.queryservices.MealQueryService;
import com.gabrielmamani.mealmanagement.meals.infrastructure.external.cloudinary.CloudinaryImageService;
import com.gabrielmamani.mealmanagement.meals.interfaces.rest.resources.CreateMealResource;
import com.gabrielmamani.mealmanagement.meals.interfaces.rest.resources.MealResource;
import com.gabrielmamani.mealmanagement.meals.interfaces.rest.resources.UpdateMealResource;
import com.gabrielmamani.mealmanagement.meals.interfaces.rest.transform.CreateMealCommandFromResourceAssembler;
import com.gabrielmamani.mealmanagement.meals.interfaces.rest.transform.MealResourceFromEntityAssembler;
import com.gabrielmamani.mealmanagement.meals.interfaces.rest.transform.UpdateMealCommandFromResourceAssembler;
import com.gabrielmamani.mealmanagement.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * REST controller exposing endpoints to manage and browse the meal catalog.
 */
@RestController
@RequestMapping(value = "/api/v1/meals", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Meals", description = "Endpoints to manage and browse the meal catalog")
public class MealController {

    private final MealCommandService mealCommandService;
    private final MealQueryService mealQueryService;
    private final CloudinaryImageService cloudinaryImageService;

    /**
     * Constructs the controller with required application services and image storage service.
     *
     * @param mealCommandService     the meal command service
     * @param mealQueryService       the meal query service
     * @param cloudinaryImageService the Cloudinary image service
     */
    public MealController(MealCommandService mealCommandService, MealQueryService mealQueryService, CloudinaryImageService cloudinaryImageService) {
        this.mealCommandService = mealCommandService;
        this.mealQueryService = mealQueryService;
        this.cloudinaryImageService = cloudinaryImageService;
    }

    /**
     * Creates a new meal in the catalog.
     *
     * @param userId   the authenticated provider identifier
     * @param resource the {@link CreateMealResource} request payload
     * @return the {@link MealResource} representing the created meal
     */
    @PostMapping
    @Operation(
            summary = "Create a meal",
            description = "Creates a new meal in the catalog of the requesting provider. Requires ROLE_PROVIDER.")
    @ApiResponse(responseCode = "201", description = "Meal created",
            content = @Content(schema = @Schema(implementation = MealResource.class)))
    @ApiResponse(responseCode = "400", description = "Validation error")
    public ResponseEntity<?> createMeal(
            @Parameter(description = "Identifier of the authenticated provider", example = "42", required = true)
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody CreateMealResource resource) {
        var command = CreateMealCommandFromResourceAssembler.toCommandFromResource(userId, resource);
        var result = mealCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, MealResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Updates an existing meal.
     *
     * @param userId   the authenticated provider identifier
     * @param mealId   the target meal identifier
     * @param resource the {@link UpdateMealResource} request payload
     * @return the {@link MealResource} representing the updated meal
     */
    @PutMapping("/{mealId}")
    @Operation(
            summary = "Update a meal",
            description = "Updates an existing meal. Only the provider that created the meal may perform this operation. Requires ROLE_PROVIDER.")
    @ApiResponse(responseCode = "200", description = "Meal updated",
            content = @Content(schema = @Schema(implementation = MealResource.class)))
    @ApiResponse(responseCode = "400", description = "Validation error")
    @ApiResponse(responseCode = "403", description = "The requester does not own this meal")
    @ApiResponse(responseCode = "404", description = "Meal not found")
    public ResponseEntity<?> updateMeal(
            @Parameter(description = "Identifier of the authenticated provider", example = "42", required = true)
            @RequestHeader("X-User-Id") Long userId,
            @Parameter(description = "Identifier of the meal to update", example = "1", required = true)
            @PathVariable Long mealId,
            @Valid @RequestBody UpdateMealResource resource) {
        var command = UpdateMealCommandFromResourceAssembler.toCommandFromResource(mealId, userId, resource);
        var result = mealCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, MealResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    /**
     * Deletes a meal from the catalog.
     *
     * @param userId the authenticated provider identifier
     * @param mealId the target meal identifier
     * @return empty response with HTTP 204 No Content
     */
    @DeleteMapping("/{mealId}")
    @Operation(
            summary = "Delete a meal",
            description = "Deletes an existing meal. Only the provider that created the meal may perform this operation. Requires ROLE_PROVIDER.")
    @ApiResponse(responseCode = "204", description = "Meal deleted")
    @ApiResponse(responseCode = "403", description = "The requester does not own this meal")
    @ApiResponse(responseCode = "404", description = "Meal not found")
    public ResponseEntity<?> deleteMeal(
            @Parameter(description = "Identifier of the authenticated provider", example = "42", required = true)
            @RequestHeader("X-User-Id") Long userId,
            @Parameter(description = "Identifier of the meal to delete", example = "1", required = true)
            @PathVariable Long mealId) {
        var command = new DeleteMealCommand(mealId, userId);
        var result = mealCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, id -> null, HttpStatus.NO_CONTENT);
    }

    /**
     * Lists all meals owned by the authenticated provider.
     *
     * @param userId the authenticated provider identifier
     * @return list of {@link MealResource} instances
     */
    @GetMapping("/provider")
    @Operation(
            summary = "List my meals",
            description = "Lists every meal (available and unavailable) owned by the requesting provider. Requires ROLE_PROVIDER.")
    @ApiResponse(responseCode = "200", description = "List of the provider's meals",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MealResource.class))))
    public ResponseEntity<List<MealResource>> getMyMeals(
            @Parameter(description = "Identifier of the authenticated provider", example = "42", required = true)
            @RequestHeader("X-User-Id") Long userId) {
        var meals = mealQueryService.handle(new GetMyMealsQuery(userId));
        var resources = meals.stream().map(MealResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Lists all available meals across all providers.
     *
     * @return list of available {@link MealResource} instances
     */
    @GetMapping
    @Operation(
            summary = "List available meals",
            description = "Lists every meal currently marked as available, across all providers. Requires ROLE_CONSUMER.")
    @ApiResponse(responseCode = "200", description = "List of available meals",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MealResource.class))))
    public ResponseEntity<List<MealResource>> getAllAvailableMeals() {
        var meals = mealQueryService.handle(new GetAllAvailableMealsQuery());
        var resources = meals.stream().map(MealResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Retrieves detailed information of a single meal.
     *
     * @param mealId the meal identifier
     * @return the {@link MealResource} instance
     */
    @GetMapping("/{mealId}")
    @Operation(
            summary = "Get meal detail",
            description = "Retrieves the full detail, including nutritional information, of a single meal. Requires ROLE_CONSUMER.")
    @ApiResponse(responseCode = "200", description = "Meal detail",
            content = @Content(schema = @Schema(implementation = MealResource.class)))
    @ApiResponse(responseCode = "404", description = "Meal not found")
    public ResponseEntity<?> getMealById(
            @Parameter(description = "Identifier of the meal", example = "1", required = true)
            @PathVariable Long mealId) {
        var result = mealQueryService.handle(new GetMealByIdQuery(mealId));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, MealResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    /**
     * Lists available meals filtered by category type.
     *
     * @param mealType the meal category (BREAKFAST, LUNCH, DINNER, SNACK)
     * @return list of matching {@link MealResource} instances
     */
    @GetMapping("/type/{mealType}")
    @Operation(
            summary = "List meals by type",
            description = "Lists available meals filtered by meal type (BREAKFAST, LUNCH, DINNER, SNACK). Requires ROLE_CONSUMER.")
    @ApiResponse(responseCode = "200", description = "List of available meals of the given type",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MealResource.class))))
    @ApiResponse(responseCode = "400", description = "Invalid meal type")
    public ResponseEntity<List<MealResource>> getMealsByType(
            @Parameter(description = "Meal type", example = "LUNCH", required = true,
                    schema = @Schema(allowableValues = {"BREAKFAST", "LUNCH", "DINNER", "SNACK"}))
            @PathVariable String mealType) {
        var meals = mealQueryService.handle(new GetMealsByMealTypeQuery(MealType.fromString(mealType)));
        var resources = meals.stream().map(MealResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Lists available meals published by a specific provider.
     *
     * @param providerId the provider identifier
     * @return list of the provider's {@link MealResource} instances
     */
    @GetMapping("/provider/{providerId}")
    @Operation(
            summary = "List meals of a provider",
            description = "Lists the available meals published by a specific provider. Requires ROLE_CONSUMER.")
    @ApiResponse(responseCode = "200", description = "List of the provider's available meals",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MealResource.class))))
    public ResponseEntity<List<MealResource>> getMealsByProvider(
            @Parameter(description = "Identifier of the provider", example = "42", required = true)
            @PathVariable Long providerId) {
        var meals = mealQueryService.handle(new GetMealsByProviderIdQuery(providerId));
        var resources = meals.stream().map(MealResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Uploads dish photos to Cloudinary.
     *
     * @param userId the authenticated provider identifier
     * @param files  array of image files (max 5)
     * @return map containing hosted image URLs
     */
    @PostMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Upload a meal image",
            description = "Uploads a physical image to Cloudinary and returns the secure URL to be used in meal creation. Requires ROLE_PROVIDER.")
    @ApiResponse(responseCode = "200", description = "Image uploaded successfully")
    @ApiResponse(responseCode = "500", description = "Error uploading image to cloud storage")
    public ResponseEntity<?> uploadMealImage(
            @Parameter(
                    description = "Identifier of the authenticated provider",
                    example = "42",
                    required = true)
            @RequestHeader("X-User-Id") Long userId,

            @Parameter(
                    description = "The image files to upload (max 5)",
                    required = true)
            @RequestParam("files") MultipartFile[] files) {

        try {
            if (files.length > 5) {
                return ResponseEntity.badRequest().body(Map.of("error", "Maximum 5 images allowed"));
            }
            java.util.List<String> imageUrls = new java.util.ArrayList<>();
            for (MultipartFile file : files) {
                imageUrls.add(cloudinaryImageService.uploadImage(file));
            }

            return ResponseEntity.ok(Map.of("urls", imageUrls));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
