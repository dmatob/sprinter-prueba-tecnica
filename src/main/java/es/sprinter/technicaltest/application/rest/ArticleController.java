package es.sprinter.technicaltest.application.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.sprinter.technicaltest.application.rest.dto.ArticleDTO;
import es.sprinter.technicaltest.application.rest.dto.ArticleModificationRequestDTO;
import es.sprinter.technicaltest.application.rest.dto.ArticlePriceModificationRequestDTO;
import es.sprinter.technicaltest.domain.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/articles")
public class ArticleController {

	private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	
	@Operation(summary = "Crear un nuevo artículo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Artículo creado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Petición incorrecta", content = @Content) })
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ArticleDTO> createArticle(@RequestBody @Valid final ArticleModificationRequestDTO articleDataDTO) {
		LOG.info("Llamada a la API de articulos para crear un nuevo articulo");
		return new ResponseEntity<>(
				ArticleDTOMapper.toArticleDTO(
						this.articleService.createArticle(ArticleDTOMapper.fromArticleDTO(articleDataDTO))),
				HttpStatus.CREATED);
	}

	
	@Operation(summary = "Obtener el listado de todos los articulos existentes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Consulta realizada con éxito", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Petición incorrecta", content = @Content) })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ArticleDTO>> getAllArticles() {
		LOG.info("Llamada a la API de articulos para obtener el listado de todos los articulos disponibles");
		return ResponseEntity.ok(ArticleDTOMapper.toListOfArticleDTOs(this.articleService.getAllArticles()));
	}
	

	@Operation(summary = "Obtener información de un artículo en base a su código")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Consulta realizada con éxito", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Petición incorrecta", content = @Content) })
	@GetMapping(value = "/{articleCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ArticleDTO> getArticleByCode(@PathVariable final String articleCode) {
		LOG.info("Llamada a la API de articulos para obtener informacion del articulo con codigo {}", articleCode);
		return ResponseEntity.ok(ArticleDTOMapper.toArticleDTO(this.articleService.getArticle(articleCode)));
	}
	

	@Operation(summary = "Actualizar un artículo existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Actualización realizada con éxito", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Petición incorrecta", content = @Content),
			@ApiResponse(responseCode = "404", description = "Artículo no encontrado", content = @Content)})
	@PutMapping(value = "/{articleCode}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ArticleDTO> updateArticle(@PathVariable final String articleCode,
			@RequestBody @Valid final ArticleModificationRequestDTO articleDataDTO) {
		LOG.info("Llamada a la API de articulos para modificar la informacion del articulo con codigo: {}",
				articleCode);
		return new ResponseEntity<>(ArticleDTOMapper.toArticleDTO(
				this.articleService.updateArticle(articleCode, ArticleDTOMapper.fromArticleDTO(articleDataDTO))),
				HttpStatus.OK);
	}
	

	@Operation(summary = "Actualizar el precio de un artículo existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Actualización de precio realizada con éxito", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Petición incorrecta", content = @Content),
			@ApiResponse(responseCode = "404", description = "Artículo no encontrado", content = @Content)})
	@PatchMapping(value = "/{articleCode}/price", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ArticleDTO> updatePriceArticle(@PathVariable final String articleCode,
			@RequestBody @Valid final ArticlePriceModificationRequestDTO articleDataDTO) {
		LOG.info("Llamada a la API de articulos para modificar el precio del articulo con codigo: {}", articleCode);
		return new ResponseEntity<>(
				ArticleDTOMapper.toArticleDTO(
						this.articleService.updatePriceArticleByCode(articleCode, articleDataDTO.getPrice())),
				HttpStatus.OK);
	}

	
	@Operation(summary = "Eliminar un artículo existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Borrado realizado con éxito", content = @Content),
			@ApiResponse(responseCode = "400", description = "Petición incorrecta", content = @Content),
			@ApiResponse(responseCode = "404", description = "Artículo no encontrado", content = @Content)})
	@DeleteMapping(value = "/{articleCode}")
	ResponseEntity<Void> deleteArticle(@PathVariable final String articleCode) {
		LOG.info("Llamada a la API de articulos para eliminar el articulo con codigo: {}", articleCode);
		this.articleService.deleteArticleByCode(articleCode);
		return ResponseEntity.ok().build();
	}

}
