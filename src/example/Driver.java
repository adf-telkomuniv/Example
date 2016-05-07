/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import model.Application;
import view_console.ConsoleView;

/**
 *
 * @author dee
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Application app = new Application();
        app.setSaveMode(1);
        new ConsoleView(app);
    }

}
