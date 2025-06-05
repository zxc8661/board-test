package board.article.api;

import board.article.controller.ArticleController;
import board.article.service.request.ArticleCreateRequest;
import board.article.service.request.ArticleUpdateRequest;
import board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        ArticleResponse response = create(new ArticleCreateRequest(
                "hi", "my content", 1L, 1L
        ));
        System.out.println("response = " + response);
    }

    ArticleResponse create(ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/articles")

                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void readTest(){
        ArticleResponse response = read(186368458952790016L);
        System.out.println("response = "+response);
    }

    ArticleResponse read(Long articleId){
        return restClient.get()
                .uri("/v1/articles/{articleId}",articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void updateTest(){
        update(186368458952790016L);
        ArticleResponse response = read(186368458952790016L);
        System.out.println("response = "+response);
    }

    void update(Long articleId){
        restClient.put()
                .uri("/v1/articles/{articleId}",articleId)
                .body(new ArticleUpdateRequest("hi2","my content 22"))
                .retrieve();

    }


    @Test
    void deleteTest(){
        restClient.delete()
                .uri("/v1/articles/{articleId}",186368458952790016L)
                .retrieve();
    }



    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }

}
