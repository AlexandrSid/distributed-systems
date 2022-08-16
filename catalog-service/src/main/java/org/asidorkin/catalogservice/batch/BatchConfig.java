package org.asidorkin.catalogservice.batch;

import org.asidorkin.catalogservice.model.Item;
import org.asidorkin.catalogservice.repository.ItemRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Value("${source.name}")
    private String fileName;

    private static final String[] COLUMNS = new String[]{
            "uniq_id",
            "sku",
            "name_title",
            "description",
            "list_price",
            "sale_price",
            "category",
            "category_tree",
            "average_product_rating",
            "product_url",
            "product_image_urls",
            "brand",
            "total_number_reviews",
            "Reviews"
    };

    @Bean
    public FlatFileItemReader<Item> reader() {

        return new FlatFileItemReaderBuilder<Item>()
                .name("itemReader")
                .resource(new ClassPathResource(fileName))
                .delimited()
                .names(COLUMNS)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Item>() {{
                    setTargetType(Item.class);
                }})
                .linesToSkip(1)
                .build();

    }

    @Autowired
    ItemRepository repository;

    @Bean
    public ItemWriter<Item> writer() {
        // return new InvoiceItemWriter(); // Using lambda expression code instead of a separate implementation
        return invoices -> {
            System.out.println("Saving Item Records: " + invoices);
            repository.saveAll(invoices);
        };
    }

    @Bean
    public ItemProcessor<Item, Item> processor() {
        // return new InvoiceProcessor(); // Using lambda expression code instead of a separate implementation
        return invoice -> {
            //do nothing
            return invoice;
        };
    }

    @Bean
    public JobExecutionListener listener() {
        return new InvoiceListener();
    }

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step stepA() {
        return stepBuilderFactory.get("stepA")
                .<Item, Item>chunk(2)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build()
                ;
    }

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("jobA")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .start(stepA())
                .build()
                ;
    }

}

