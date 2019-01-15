package jp.co.springbatch.sample.biz.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import jp.co.springbatch.sample.data.dto.Customer;

public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {

    private static final Logger log = LoggerFactory.getLogger(CustomerItemProcessor.class);

    @Override
    public Customer process(final Customer customer) throws Exception {
        final String name = customer.getName().toUpperCase();

        final Customer transformedCustomer = new Customer(name, customer.getAddress(), customer.getTel());

        log.info("Converting (" + customer + ") into (" + transformedCustomer + ")");

        return transformedCustomer;
    }

}
