package com.tuber.outbox;

public interface OutboxSchedulerDispatcher {
    void dispatchOutboxMessage();
}
