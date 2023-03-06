import nsu.fit.tsukanov.order.Order;
import nsu.fit.tsukanov.order.OrderBoard;
import nsu.fit.tsukanov.order.OrderBoardDeque;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = OrderBoard.class)
public class TestClass {

    private OrderBoard orderBoard = new OrderBoardDeque();


    @Test
    void orderBoard() {
        Assertions.assertFalse(orderBoard.hasOrder());
        orderBoard.addOrder(new Order(null, null, null, null));
        Assertions.assertTrue(orderBoard.hasOrder());
        orderBoard.takeOrder();
        Assertions.assertFalse(orderBoard.hasOrder());
    }

}
