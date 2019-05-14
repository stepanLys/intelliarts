package stepan.lys.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import stepan.lys.model.Purchase;
import stepan.lys.service.PurchaseService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseService purchaseService;

    @Test
    public void findAll() throws Exception {
        Purchase purchase = new Purchase(LocalDate.of(2019, 4, 25), 12d, "EUR", "Photo Frame");
        Purchase purchase1 = new Purchase(LocalDate.of(2019, 4, 25), 2d, "USD", "T-shirt");
        Purchase purchase2 = new Purchase(LocalDate.of(2019, 4, 26), 2d, "UAH", "Book");

        Map<LocalDate, List<String>> purchases = new TreeMap<>();

        purchases.put(purchase.getDate(), Arrays.asList(
                purchase.getName() + " " + purchase.getPrice().toString() + " " + purchase.getCurrency(),
                purchase1.getName() + " " + purchase1.getPrice().toString() + " " + purchase1.getCurrency()));

        purchases.put(purchase2.getDate(), Arrays.asList(
                purchase2.getName() + " " + purchase2.getPrice().toString() + " " + purchase2.getCurrency()));

        given(purchaseService.getAll()).willReturn(purchases);

        this.mockMvc.perform(get("/api/purchase").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'2019-04-25': ['Photo Frame 12.0 EUR', 'T-shirt 2.0 USD'], '2019-04-26': ['Book 2.0 UAH']}"));
    }

    @Test
    public void deleteByDate() throws Exception {
        Purchase purchase = new Purchase(LocalDate.of(2019, 4, 25), 12d, "EUR", "Photo Frame");
        Purchase purchase1 = new Purchase(LocalDate.of(2019, 4, 25), 2d, "USD", "T-shirt");
        Purchase purchase2 = new Purchase(LocalDate.of(2019, 4, 26), 2d, "UAH", "Book");

        Map<LocalDate, List<String>> purchases = new TreeMap<>();

        purchases.put(purchase.getDate(), Arrays.asList(
                purchase.getName() + " " + purchase.getPrice().toString() + " " + purchase.getCurrency(),
                purchase1.getName() + " " + purchase1.getPrice().toString() + " " + purchase1.getCurrency()));

        purchases.put(purchase2.getDate(), Arrays.asList(
                purchase2.getName() + " " + purchase2.getPrice().toString() + " " + purchase2.getCurrency()));

        given(purchaseService.deleteByDate(purchase2.getDate())).willReturn(purchases);

        String uri = "/api/purchase/2019-04-26";

        this.mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'2019-04-25': ['Photo Frame 12.0 EUR', 'T-shirt 2.0 USD']}"));
    }

    @Test
    public void goodPostPurchase() throws Exception {
        Purchase purchase = new Purchase(LocalDate.of(2019, 4, 25), 12d, "EUR", "Photo Frame");

        given(purchaseService.add(ArgumentMatchers.any(Purchase.class))).willReturn(purchase);

        String jsonContent = "{\"date\": \"2019-04-25\",\"price\": 12, \"currency\": \"EUR\", \"name\": \"Photo Frame\"}";

        mockMvc.perform(post("/api/purchase/").contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .characterEncoding("UTF-8"))
                .andExpect(status().isCreated());


    }

    @Test
    public void badPostPurchase() throws Exception {
        Purchase purchase = new Purchase(LocalDate.of(2019, 4, 25), 12d, "EUR", "Photo Frame");

        given(purchaseService.add(ArgumentMatchers.any(Purchase.class))).willReturn(purchase);

        String jsonContent = "{\"date\": \"2019-04-25\", \"currency\": \"EUR\", \"name\": \"Photo Frame\"}";

        mockMvc.perform(post("/api/purchase/").contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest());


    }
}