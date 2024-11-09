package com.yuva.order.config;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBusConfig {
    @Bean
    public ServiceBusSenderClient serviceBusSenderClient() {
        return new ServiceBusClientBuilder()
                .connectionString("Endpoint=sb://reducestock.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=9b74FGBhA0tqYhjHPFJae/unVbzMlhfB2+ASbE8dM8s=")
                .sender()
                .queueName("stock-queue")
                .buildClient();
    }
}
