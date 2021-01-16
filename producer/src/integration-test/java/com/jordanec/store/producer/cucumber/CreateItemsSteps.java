package com.jordanec.store.producer.cucumber;

import com.jordanec.store.producer.entity.Item;
import com.jordanec.store.producer.service.ItemService;
import io.cucumber.java.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CreateItemsSteps {

    @Autowired
    ItemService itemService;
    List<Long> itemIds;
    Item inputItem;
    List<Item> inputItems;
    Item responseItem;
    List<Item> responseItems;

    Exception exception;

    @Before(order = 100)
    public void init() {
        itemIds = new ArrayList<>();
    }

    @After(order = 100)
    public void cleanUp(){
        itemIds.forEach(id -> itemService.delete(id));
    }


    @Given("An item with name {string} description {string} and unit price {double} is instantiated")
    public void anItemWithNameDescriptionAndUnitPriceIsInstantiated(String name, String description, Double unitPrice) {
        inputItem = new Item();
        if (StringUtils.isNotBlank(name)) {
            inputItem.setName(name);
        }
        inputItem.setDescription(description);
        inputItem.setUnitPrice(unitPrice);
    }

    @Given("An item is instantiated")
    public void anItemWithNameDescriptionAndUnitPriceIsInstantiated(Item item) {
        inputItem = item;
    }

    @When("^The item is created$")
    public void theItemIsCreated() {
        try {
            responseItem = itemService.create(inputItem);
            itemIds.add(responseItem.getItemId());
            this.exception = null;
        } catch (Exception exception) {
            this.exception = exception;
        }
    }

    @Then("^The item is returned$")
    public void theItemIsReturned() {
        Assert.assertNotNull(responseItem);
        Assert.assertNotNull(responseItem.getItemId());
    }

    @Then("An exception containing {string} is thrown")
    public void anExceptionContainingThrown(String exceptionName) {
        Assert.assertThat(exception.getCause().getCause().getMessage(), CoreMatchers.containsString(exceptionName));

    }

    @Then("An exception {string} is thrown")
    public void anExceptionIsThrown(String exceptionName) {
        Assert.assertEquals(exceptionName,  exception.getClass().getSimpleName());
    }

    @Given("Multiple items are instantiated")
    public void multipleItemsAreInstantiated(List<Item> items) {
        this.inputItems = items;
    }

    @When("The items are created")
    public void theItemsAreCreated() {
        try {
            responseItems = itemService.create(inputItems);
            responseItems.forEach(ri -> itemIds.add(ri.getItemId()));
            this.exception = null;
        } catch (Exception exception) {
            this.exception = exception;
        }
    }

    @Then("^The items are returned$")
    public void theItemsAreReturned() {
        Assert.assertNotNull(responseItems);
        responseItems.forEach(item -> Assert.assertNotNull(item.getItemId()));
    }

}
