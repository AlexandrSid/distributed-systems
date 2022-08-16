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

    //Reader class Object
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

    //Autowire InvoiceRepository
    @Autowired
    ItemRepository repository;

    //Writer class Object
    @Bean
    public ItemWriter<Item> writer() {
        // return new InvoiceItemWriter(); // Using lambda expression code instead of a separate implementation
        return invoices -> {
            System.out.println("Saving Item Records: " + invoices);
            repository.saveAll(invoices);
        };
    }

    //Processor class Object
    @Bean
    public ItemProcessor<Item, Item> processor() {
        // return new InvoiceProcessor(); // Using lambda expression code instead of a separate implementation
        return invoice -> {
            //do nothing
            return invoice;
        };
    }

    //Listener class Object
    @Bean
    public JobExecutionListener listener() {
        return new InvoiceListener();
    }

    //Autowire StepBuilderFactory
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //Step Object
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

    //Autowire JobBuilderFactory
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    //Job Object
    @Bean
    public Job jobA() {
        return jobBuilderFactory.get("jobA")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .start(stepA())
                // .next(stepB())
                // .next(stepC())
                .build()
                ;
    }

}


//
//@Configuration
//@EnableBatchProcessing
//public class BatchConfig {
//
//    @Autowired
//    ItemRepository repository;
//
////    @PostConstruct
////    public void initRepo() {
////        repository.saveAll(readCSVFile());
////    }
//
//    @Value("${source.name}")
//    private String fileName;
//
//    private static final String[] COLUMNS = new String[]{
//            "uniq_id",
//            "sku",
//            "name_title",
//            "description",
//            "list_price",
//            "sale_price",
//            "category",
//            "category_tree",
//            "average_product_rating",
//            "product_url",
//            "product_image_urls",
//            "brand",
//            "total_number_reviews",
//            "Reviews"
//    };
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public Job readCSVFilesJob() {
//        return jobBuilderFactory
//                .get("readCSVFilesJob")
//                .incrementer(new RunIdIncrementer())
//                .start(step1())
//                .build();
//    }
//
//    @Bean
//    public Step step1() {
//        return stepBuilderFactory.get("step1").<Item, Item>chunk(5)
//                .reader(reader())
//                .writer(writer())
//                .build();
//    }
//
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    @Bean
//    public FlatFileItemReader<Item> reader() {
//        return new FlatFileItemReaderBuilder<Item>()
//                .name("csvFileReader")
//                .resource(new ClassPathResource(fileName))
//                .delimited()
//                .names(COLUMNS)
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
//                    setTargetType(Item.class);
//                }})
//                .linesToSkip(1)
//                .build();
////        //Create reader instance
////        FlatFileItemReader<Item> reader = new FlatFileItemReader<>();
////
////        //Set input file location
////        reader.setResource(new FileSystemResource(fileName));
////
////        //Set number of lines to skips. Use it if file has header rows.
////        reader.setLinesToSkip(1);
////
////        //Configure how each line will be parsed and mapped to different values
////        reader.setLineMapper(new DefaultLineMapper() {
////            {
////                setLineTokenizer(new DelimitedLineTokenizer() {
////                    {
////                        setNames(COLUMNS);
////                    }
////                });
////                //Set values in Item class
////                setFieldSetMapper(new BeanWrapperFieldSetMapper<Item>() {
////                    {
////                        setTargetType(Item.class);
////                    }
////                });
////            }
////        });
////        return reader;
//    }
//
//    @Bean
//    public ConsoleItemWriter<Item> writer()
//    {
//        return new ConsoleItemWriter<Item>();
//    }
//}