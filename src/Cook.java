public class Cook implements Runnable{
    public boolean continueWorking = true;

    @Override
    public void run() {
        boolean needToWait;
        while (continueWorking || needtoCookOrders()) {
            try {
                synchronized (this) {
                    needToWait = !needtoCookOrders();
                    if (!needToWait) cooking();
                }
                if (continueWorking && needToWait) {
                    System.out.println("Chef is relaxing");
                    Thread.sleep(100);
                }
            }
            catch (InterruptedException e) {

            }
        }
    }

    private boolean needtoCookOrders() {
        return !Manager.getInstance().getOrderQueue().isEmpty();
    }

    private void cooking() throws InterruptedException {
        Manager manager = Manager.getInstance();
        Order order = manager.getOrderQueue().poll(); //chef is taking an order
        System.out.println(String.format("The order will be cooked %d ms for table №%d", order.getTime(), order.getTableNumber()));
        Thread.sleep(order.getTime());
        Dishes dishes = new Dishes(order.getTableNumber()); //cook the food
        System.out.println(String.format("The order is ready for the table №%d", dishes.getTableNumber()));
        manager.getDishesQueue().add(dishes);
    }
}
