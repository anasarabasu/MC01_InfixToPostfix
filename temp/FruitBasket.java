package temp;

import java.util.ArrayList;

public class FruitBasket {
    
    private ArrayList<Fruit> container;


    public FruitBasket() {
        container = new ArrayList<>();
    }

    public void addFruit(Fruit fruit) {
        container.add(fruit);
    }

    public void removeFruit(Fruit fruit) {
        container.remove(fruit);
    };

    public void checkFruits() {
        for(Fruit fruit : container) {
            System.out.println(fruit.getName());
        }
    }

}
