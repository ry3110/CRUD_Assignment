package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetEndPoints {
	public static Response createPet(Pet pet) {
        Response response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(pet)
        .when()
                .post(Routes.post_url);

        return response;
    }

    public static Response findPetById(long petId) {
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("petId", petId)
        .when()
                .get(Routes.get_url);

        return response;
    }

    public static Response updatePet(Pet pet) {
        Response response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(pet)
        .when()
                .put(Routes.update_url);

        return response;
    }

    public static Response deletePet(long petId) {
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("petId", petId)
        .when()
                .delete(Routes.delete_url);

        return response;
    }
}
