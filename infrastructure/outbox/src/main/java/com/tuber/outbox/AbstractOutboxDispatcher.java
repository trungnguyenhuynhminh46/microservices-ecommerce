package com.tuber.outbox;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Slf4j
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractOutboxDispatcher<T> implements OutboxSchedulerDispatcher {
    protected abstract Optional<List<T>> findOutboxMessages();

    protected abstract void publishMessage(T message, BiConsumer<T, OutboxStatus> outboxCallback);

    @Override
    @Transactional
    public void dispatchOutboxMessage() {
        Optional<List<T>> optionalMessages = findOutboxMessages();
        if (optionalMessages.isPresent() && !optionalMessages.get().isEmpty()) {
            List<T> messages = optionalMessages.get();
            log.info("Sending {} {} to messages bus. The ids: {}",
                    messages.size(),
                    messages.getFirst().getClass().getSimpleName(),
                    messages.stream()
                            .map(this::getMessageId)
                            .collect(Collectors.joining(",")));
            messages.forEach(message -> publishMessage(message, this::updateOutboxStatus));
        }
    }

    protected abstract String getMessageId(T message);

    protected abstract void updateOutboxStatus(T message, OutboxStatus outboxStatus);
}
