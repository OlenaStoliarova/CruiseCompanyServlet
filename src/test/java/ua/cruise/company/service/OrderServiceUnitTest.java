package ua.cruise.company.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.cruise.company.dao.ExtraDao;
import ua.cruise.company.dao.OrderDao;
import ua.cruise.company.entity.Order;
import ua.cruise.company.service.exception.NoEntityFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceUnitTest {
    private static final Long ID = 1L;
    private static final Long NON_EXISTING_ID = 0L;

    @InjectMocks
    private OrderService instance;

    @Mock
    private OrderDao orderDao;
    @Mock
    private CruiseService cruiseService;
    @Mock
    private UserService userService;
    @Mock
    private ExcursionService excursionService;
    @Mock
    private ExtraDao extraDao;

    private Order order;

    @Before
    public void setUp() {
        order = new Order();
        order.setId(ID);

        when(orderDao.findById(ID)).thenReturn(Optional.of(order));
        when(orderDao.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());
    }

    @Test
    public void shouldReturnOrderWhenFoundById() throws NoEntityFoundException {
        Order result = instance.getOrderById(ID, false);

        assertThat(result).isEqualTo(order);
    }

    @Test( expected = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundExceptionWhenNotFoundById() throws NoEntityFoundException {
        instance.getOrderById(NON_EXISTING_ID, false);
    }
}
