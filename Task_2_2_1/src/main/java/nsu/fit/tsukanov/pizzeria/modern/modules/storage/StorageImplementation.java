package nsu.fit.tsukanov.pizzeria.modern.modules.storage;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.pizzeria.modern.common.BufferAbstract;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.Configuration;
import nsu.fit.tsukanov.pizzeria.modern.modules.order.Order;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class StorageImplementation extends BufferAbstract<Order> implements Storage {
    public StorageImplementation() {
        super(Configuration.MAX_STORAGE_SIZE);
    }

    public StorageImplementation(int capacity) {
        super(capacity);
    }
}
