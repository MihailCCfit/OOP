package nsu.fit.tsukanov.pizzeria.modern;

public class Main {
    public static void main(String[] args) {/*
        BakerRepository bakerRepository = new BakerRepositoryJSON();
        CourierRepository courierRepository = new CourierRepositoryJSON();
        Storage storage = new StorageImplementation();
        OrderBoard orderBoard = new OrderBoardDeque();
        BakerService bakerService = new BakerService(bakerRepository, storage, orderBoard);
        ClientService clientService = new ClientService(orderBoard);

        CourierService courierService = new CourierService(courierRepository, storage);
        List<PizzaService> services = List.of(bakerService, clientService, courierService);

        initializing(services);
        starting(services);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            switch (line) {
                case "final" -> finalWorking(services);
                case "enable" -> enable(services);
                case "start" -> starting(services);
                case "alarm" -> alarm(services);
                case "stop" -> stop(services);
                case "end" -> {
                    System.out.println("Ends");
                    return;
                }
            }
        }
    }

    public static void initializing(List<PizzaService> services) {
        services.forEach(PizzaService::initialize);
    }

    public static void starting(List<PizzaService> services) {
        services.forEach(PizzaService::startWorking);
    }

    public static void stop(List<PizzaService> services) {
        services.forEach(PizzaService::stopWorking);
    }

    public static void alarm(List<PizzaService> services) {
        services.forEach(PizzaService::alarmWorking);
    }

    public static void enable(List<PizzaService> services) {
        services.forEach(PizzaService::enableWorking);
    }

    public static void finalWorking(List<PizzaService> services) {
        services.forEach(PizzaService::finalWorking);
    }
    */
    }
}
