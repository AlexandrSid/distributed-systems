package org.asidorkin.catalogservice;

import org.asidorkin.catalogservice.model.Item;
import org.asidorkin.catalogservice.model.Review;
import org.asidorkin.catalogservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    ItemRepository repository;

    final String fileName = "catalog-service/src/main/resources/jcpenney_cut_sample.csv";

    private Item injectData(String[] format, String[] values) {
        Item item = new Item();
        for (int i = 0; i < format.length; i++) {
            String parameter = format[i];
            String value = (i < values.length) ? values[i] : "";
            switch (parameter) {
                case "uniq_id":
                    item.setUniqId(value);
                    break;
                case "sku":
                    item.setSku(value);
                    break;
                case "name_title":
                    item.setName_title(value);
                    break;
                case "description":
                    item.setDescription(value);
                    break;
                case "list_price":
                    item.setList_price(value);
                    break;
                case "sale_price":
                    item.setSale_price(value);
                    break;
                case "category":
                    item.setCategory(value);
                    break;
                case "category_tree":
                    item.setCategory_tree(value);
                    break;
                case "average_product_rating":
                    item.setAverageproductrating(value);
                    break;
                case "product_url":
                    item.setProduct_url(value);
                    break;
                case "product_image_urls":
                    item.setProductimageurls(value);
                    break;
                case "brand":
                    item.setBrand(value);
                    break;
                case "total_number_reviews":
                    item.setTotalnumberreviews(value);
                    break;
                case "Reviews":
                    List<Review> reviews = Arrays.stream(splitReviews(value))
                            .map(Review::new)
                            .collect(Collectors.toList());
                    item.setReviews(reviews);
                    break;
            }
        }
        return item;
    }

    private String[] splitReviews(String reviews) {
        if (reviews.isEmpty()) return new String[0];

        String[] split = reviews.split("=>\"");
        String[] result = new String[split.length - 1];
        for (int i = 1; i < split.length; i++) {
            result[i - 1] = split[i].substring(0, split[i].indexOf("\"}"));
        }
        return result;
    }

    public List<Item> readCSVFile() {
        List<Item> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = br.readLine();
            String[] formatValues = line.split(",");

            while ((line = br.readLine()) != null) {
                String[] dataRow = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                Item item = injectData(formatValues, dataRow);
                records.add(item);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void initRepo(){
        repository.saveAll(readCSVFile());
    }

}
