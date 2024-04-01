package api.test;

import api.endpoints.PetEndPoints;
import api.payload.Pet;
import api.payload.Pet.Category;
import api.payload.Pet.Tag;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

public class PetTests {

    private Pet petPayload;
    private long petId;

    @BeforeClass
    public void setUp() {
        petPayload = createPetPayload();
    }

    private Pet createPetPayload() {
        long currentTimestamp = System.currentTimeMillis();

        Pet pet = new Pet();
        pet.setId(currentTimestamp);
        pet.setName("Pet" + currentTimestamp);
        Category category = new Category();
        category.setId(currentTimestamp + 1);
        category.setName("Category" + currentTimestamp);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("https://example.com/pet" + currentTimestamp + ".jpg"));
        Tag tag = new Tag();
        tag.setId(currentTimestamp + 2);
        tag.setName("Tag" + currentTimestamp);
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("available");

        return pet;
    }

    @Test(priority = 1)
    public void testCreatePet() {
        Response response = PetEndPoints.createPet(petPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        // Extracting and storing the pet ID from the response
        petId = response.then().extract().path("id");
    }

    @Test(priority = 2)
    public void testFindPetById() {
        // Use the stored petId to find the pet
        Response response = PetEndPoints.findPetById(petId);
        response.then().log().body();

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 3)
    public void testUpdatePet() {
        long newTimestamp = System.currentTimeMillis();
        petPayload.setId(petId);
        petPayload.setName("UpdatedPet" + newTimestamp);
        Category newCategory = new Category();
        newCategory.setId(newTimestamp + 3);
        newCategory.setName("UpdatedCategory" + newTimestamp);
        petPayload.setCategory(newCategory);
        petPayload.setStatus("sold");

        Response response = PetEndPoints.updatePet(petPayload);

        Assert.assertEquals(response.getStatusCode(), 200);

        Response responseAfterUpdate = PetEndPoints.findPetById(petId);
        responseAfterUpdate.then().log().body();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
    }

    @Test(priority = 4)
    public void testDeletePet() {
        // Use the stored petId to delete the pet
        Response response = PetEndPoints.deletePet(petId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
