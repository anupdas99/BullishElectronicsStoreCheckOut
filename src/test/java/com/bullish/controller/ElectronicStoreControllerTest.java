package com.bullish.controller;

import com.bullish.domain.Product;
import com.bullish.domain.Receipt;
import com.bullish.service.ElectronicStoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ElectronicStoreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ElectronicStoreService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ElectronicStoreController(service)).build();
    }

    @Test
    public void testCreateProduct() throws Exception {
        String requestBody = "{\"name\":\"Product 1\",\"price\":10.0}";

        when(service.createProduct(any())).thenReturn(new Product(1, "Product 1", 10.0));

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.0));

        verify(service, times(1)).createProduct(any());
    }

    @Test
    public void testRemoveProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{productId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).removeProduct(1);
    }

    @Test
    public void testAddDeal() throws Exception {
        String requestBody = "{\"productId\":1,\"dealType\":\"TWENTY_PERCENT_OFF\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/deals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).addDeal(any());
    }

    @Test
    public void testAddToBasket() throws Exception {
        String requestBody = "{\"customerId\":\"12345\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/basket/{productId}", 1)
                        .param("customerId", "12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).addToBasket(anyInt(), eq("12345"));
    }

    @Test
    public void testRemoveFromBasket() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/basket/{productId}", 1)
                        .param("customerId", "12345"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).removeFromBasket(anyInt(), eq("12345"));
    }

    @Test
    public void testCalculateReceipt() throws Exception {
        when(service.calculateReceipt("12345"))
                .thenReturn(new Receipt(Arrays.asList(new Product(1, "Product 1", 10.0)), 10.0, 0.0, 10.0));

        mockMvc.perform(MockMvcRequestBuilders.get("/receipt")
                        .param("customerId", "12345"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].name").value("Product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].price").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subtotal").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discount").value(0.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(10.0));

        verify(service, times(1)).calculateReceipt("12345");
    }
}

