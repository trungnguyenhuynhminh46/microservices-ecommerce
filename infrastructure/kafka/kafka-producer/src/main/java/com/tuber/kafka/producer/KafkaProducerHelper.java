package com.tuber.kafka.producer;

import com.tuber.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class KafkaProducerHelper {
    public <A, O> BiConsumer<SendResult<String, A>, Throwable>
    getOnSuccessKafka(
            String topicName,
            A avroModel,
            O outboxMessage,
            BiConsumer<O, OutboxStatus> onSuccessOutBox
    ) {
        return (result, exception) -> {
            if (exception == null) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Successfully sending {} to Kafka with topic: {}, partition: {}, offset: {}, timestamp: {}",
                        avroModel.getClass().getName(),
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp()
                );
                onSuccessOutBox.accept(outboxMessage, OutboxStatus.COMPLETED);
            } else {
                log.error("Errors happened while sending {} to Kafka with message: {} and outbox type: {} to topic {}",
                        avroModel.getClass().getName(),
                        avroModel,
                        outboxMessage.getClass().getName(),
                        topicName,
                        exception
                );
                onSuccessOutBox.accept(outboxMessage, OutboxStatus.FAILED);
            }
        };
    }
}
