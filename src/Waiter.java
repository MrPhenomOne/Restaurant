public class Waiter implements Runnable{

    public boolean continueWorking = true;

    @Override
    public void run() {
        Manager manager = Manager.getInstance();

        while(continueWorking || !manager.getDishesQueue().isEmpty()) {
            if (!manager.getDishesQueue().isEmpty()) {
                Dishes dishes = manager.getDishesQueue().poll(); //food is ready, give it
                System.out.println(String.format("Waiter gave up the food to №%d", dishes.getTableNumber()));
            } else {
                Table table = manager.getNextTable();
                Order order = table.getOrder();
                System.out.println(String.format("The order from the table №%d is received", order.getTableNumber()));
                manager.getOrderQueue().add(order);
            }
            try { Thread.sleep(100);} catch (InterruptedException e) {}
        }
    }
}
