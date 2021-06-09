package streams;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {

    private List<Order> orders = new ArrayList<>();


    public void saveOrder(Order order) {
        orders.add(order);
    }

    public long countOrdersByStatus(String status) {
        return orders.stream()
                .filter(o -> o.getStatus().equals(status))
                .count();
    }

    public List<Order> collectOrdersWithProductCategory(String category) {
        return orders.stream()
                .filter(o -> o.getProducts().stream()
                        .anyMatch(p -> p.getCategory().equals(category)))
                .collect(Collectors.toList());
    }

    public List<Product> productsOverAmountPrice(int amount){
        return orders.stream()
                .flatMap(o->o.getProducts().stream())
                .filter(p->p.getPrice()>amount)
                .distinct()
                .collect(Collectors.toList());
    }

    public double getOrdersAmountBetweenDates(LocalDate from,LocalDate until){
        return orders.stream()
                .filter(o->o.getOrderDate().isAfter(from))
                .filter(o->o.getOrderDate().isBefore(until))
                .flatMap(o->o.getProducts().stream())
                .mapToDouble(p->p.getPrice())
                .sum();
    }

    public Optional<Product> findProductByName(String name){
        return orders.stream()
                .flatMap(o->o.getProducts().stream())
                .filter(p->p.getName().equals(name))
                .findFirst();
    }

    public Order getOrderWithMostExpensiveProduct(){
        Optional<Product> mostExpensive = orders.stream()
                .flatMap(o->o.getProducts().stream())
                .max(Comparator.comparingDouble(Product::getPrice));
        if (mostExpensive.isPresent()) {
            return orders.stream()
                    .filter(o->o.getProducts().stream().anyMatch(p->p.getPrice()-mostExpensive.get().getPrice()<0.0001))
                    .findFirst().get();
        }else {
            return null;
        }
    }

}
