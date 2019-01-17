package jp.co.springbatch.sample.biz.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import jp.co.springbatch.sample.data.dto.CustomerFileDto;

public class CustomerItemProcessor implements ItemProcessor<CustomerFileDto, CustomerFileDto> {

    private static final Logger log = LoggerFactory.getLogger(CustomerItemProcessor.class);

    @Override
    public CustomerFileDto process(final CustomerFileDto customer) throws Exception {
        return convertNameUpperCase(customer);
    }

    private CustomerFileDto convertNameUpperCase(final CustomerFileDto customer) {
    	final String name = customer.getName().toUpperCase();
        final CustomerFileDto convertCustomer = new CustomerFileDto(name, customer.getAddress(), customer.getTel());

        log.info("Converting [{}] into [{}]", customer, convertCustomer);
        return convertCustomer;
    }

}
