package org.asidorkin.eurekaserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSVConverter {
    //
//    public static void main(String[] args) {
//        List<Item> items = readCSVFile();
//        System.out.println(items.size());
//        items.stream().forEach(System.out::println);
//    }

    public static Item injectData(String[] format, String[] values) {
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
                    item.setReviews(splitReviews(value));
                    break;
            }
        }
        return item;
    }

    private static String[] splitReviews(String reviews) {
        if (reviews.isEmpty()) return new String[0];

        String[] split = reviews.split("=>\"");
        String[] result = new String[split.length - 1];
        for (int i = 1; i < split.length; i++) {
            result[i - 1] = split[i].substring(0, split[i].indexOf("\"}"));
        }
        return result;
    }

    private static List<Item> readCSVFile() {
        List<Item> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("jcpenney_com-ecommerce_sample.csv"))) {
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

}


//  private static String dataFormat = "uniq_id,sku,name_title,description,list_price,sale_price,category,category_tree,average_product_rating,product_url,product_image_urls,brand,total_number_reviews,Reviews";
//    private static String rowExample = "b6c0b6bea69c722939585baeac73c13d," +
//            "pp5006380337," +
//            "Alfred Dunner® Essential Pull On Capri Pant," +
//            "\"You'll return to our Alfred Dunner pull-on capris again and again when you want an updated, casual look and all the comfort you love.   elastic waistband approx. 19-21\"\" inseam slash pockets polyester washable imported      \"," +
//            "41.09,24.16," +
//            "alfred dunner," +
//            "jcpenney|women|alfred dunner," +
//            "4.7 out of 5," +
//            "http://www.jcpenney.com/alfred-dunner-essential-pull-on-capri-pant/prod.jump?ppId=pp5006380337&catId=cat1002110079&&_dyncharset=UTF-8&urlState=/women/shop-brands/alfred-dunner/yellow/_/N-gkmp33Z132/cat.jump," +
//            "\"http://s7d9.scene7.com/is/image/JCPenney/DP1228201517142050M.tif?hei=380&amp;wid=380&op_usm=.4,.8,0,0&resmode=sharp2&op_usm=1.5,.8,0,0&resmode=sharp\"," +
//            "Alfred Dunner," +
//            "12," +
//            "\"{\"\"review\"\"=>[{\"\"review_1\"\"=>\"\"You never have to worry about the fit...Alfred Dunner clothing sizes are true to size and fits perfectly. Great value for the money.\"\"}, {\"\"review_2\"\"=>\"\"Good quality fabric. Perfect fit. Washed very well no iron.\"\"}, {\"\"review_3\"\"=>\"\"I do not normally wear pants or capris that have an elastic waist, but I decided to try these since they were on sale and I loved the color. I was very surprised at how comfortable they are and wear really well even wearing all day. I will buy this style again!\"\"}, {\"\"review_4\"\"=>\"\"I love these capris! They fit true to size and are so comfortable to wear. I am planning to order more of them.\"\"}, {\"\"review_5\"\"=>\"\"This product is very comfortable and the fabric launders very well\"\"}, {\"\"review_6\"\"=>\"\"I did not like the fabric. It is 100% polyester I thought it was different.I bought one at the store apprx two monts ago, and I thought it was just like it\"\"}, {\"\"review_7\"\"=>\"\"What a great deal. Beautiful Pants. Its more than I expected.\"\"}, {\"\"review_8\"\"=>\"\"Alfred Dunner has great pants, good fit and very comfortable\"\"}]}\"\n";
//
//    private static String notFullString = "0e0d413e95710119c51a0db8522a2482,pp5006790780,Alfred Dunner® Feels Like Spring Short Sleeve Chevron Texture 2Fer Shirt,\"With chic chevron pattern, our short-sleeve layered shirt is sure to become an easy favorite. crewneck short sleeves 25\"\" length polyester washable imported\",74.93,56.2,cardigans & shrugs,jcpenney|women|cardigans & shrugs,,http://www.jcpenney.com/alfred-dunner-feels-like-spring-short-sleeve-chevron-texture-2fer-shirt/prod.jump?ppId=pp5006790780&catId=cat100260301&&_dyncharset=UTF-8&urlState=/women/shop-clothing/tops/cardigans-shrugs/petite-small/_/N-1noxcdZ1z13yon/cat.jump,\"http://s7d9.scene7.com/is/image/JCPenney/DP0229201617053098M.tif?hei=380&amp;wid=380&op_usm=.4,.8,0,0&resmode=sharp2&op_usm=1.5,.8,0,0&resmode=sharp\",Alfred Dunner,,";
