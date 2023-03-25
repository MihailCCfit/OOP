package nsu.fit.tsukanov.pizzeria.modern.common.buffer;

import nsu.fit.tsukanov.pizzeria.modern.common.configuration.Configuration;
import nsu.fit.tsukanov.pizzeria.modern.common.objects.DeliveryOrder;

public class Storage extends BufferAbstract<DeliveryOrder> {
    public Storage(int capacity) {
        super(capacity);
    }

    public Storage() {
        super(Configuration.MAX_STORAGE_SIZE);
    }
}
