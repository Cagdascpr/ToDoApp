package app;

import menu.Menu;
import repository.Repository;
import service.Service;

public class Main {
    public static void  main(String[] args){

        Repository repository = new Repository();
        Service service = new Service(repository);
        Menu menu = new Menu(service);

        menu.start();



    }
}