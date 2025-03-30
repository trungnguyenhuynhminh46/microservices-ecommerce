package com.tuber.outbox;

public interface OutboxScheduler {
    void processOutboxMessage();
}
