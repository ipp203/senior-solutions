package streams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    OrderService ordersService = new OrderService() ;


    @BeforeEach
    public void init(){


        Product p1 = new Product("Tv","IT",2000);
        Product p2 = new Product("Laptop","IT",2400);
        Product p3 = new Product("Phone","IT",400);
        Product p4 = new Product("Lord of The Rings","Book",20);
        Product p5 = new Product("Harry Potter Collection","Book",120);

        Order o1 = new Order("pending", LocalDate.of(2021,06,07));
        o1.addProduct(p1);
        o1.addProduct(p2);
        o1.addProduct(p5);

        Order o2 = new Order("on delivery", LocalDate.of(2021,06,01));
        o2.addProduct(p3);
        o2.addProduct(p1);
        o2.addProduct(p2);

        ordersService.saveOrder(o1);
        ordersService.saveOrder(o2);

    }

    @Test
    void testCountOrdersByStatus(){
        long result = ordersService.countOrdersByStatus("pending");
        assertEquals(1L,result);
    }

    @Test
    void testCollectOrdersWithProductCategory(){
        List<Order> result = ordersService.collectOrdersWithProductCategory("Book");
        assertEquals(1,result.size());
    }

    @Test
    void testProductsOverAmountPrice(){
        List<Product> result = ordersService.productsOverAmountPrice(1990);
        assertEquals(2,result.size());
        result.sort(Comparator.comparingDouble(Product::getPrice));
        assertEquals(2000,result.get(0).getPrice());
    }

    @Test
    void testGetOrdersAmountBetweenDates(){
        double result = ordersService.getOrdersAmountBetweenDates(LocalDate.of(2020,5,1),
                LocalDate.of(2021,6,5));
        assertEquals(4800.0,result);
    }

    @Test
    void testFindProductByName(){
        Optional<Product>product = ordersService.findProductByName("Laptop");
        assertTrue(product.isPresent());
        product = ordersService.findProductByName("NoteBook");
        assertTrue(product.isEmpty());
    }

    @Test
    void testGetOrderWithMostExpensiveProduct(){
        Order result = ordersService.getOrderWithMostExpensiveProduct();
        assertTrue(result.getProducts().contains(new Product("Laptop","IT",2400)));
    }
}