/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ApplicationFile;
import model.User;
import model.Vacancy;

/**
 *
 * @author dee
 */
public class FileIO {

    private String filename;
    private String fileLog;

    public FileIO(String filename, String fileLog) {
        this.filename = filename;
        this.fileLog = fileLog;
    }

    public void saveObject(Object o, String filename) {
        try (FileOutputStream fout = new FileOutputStream(filename)) {
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(o);
            oout.flush();
            oout.close();
        } catch (FileNotFoundException ex) {
            throw new AssertionError(ex.getMessage());
        } catch (IOException ex) {
            throw new AssertionError(ex.getMessage());
        }
    }

    public Object loadObject(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return ois.readObject();
        } catch (FileNotFoundException ex) {
            throw new AssertionError(ex.getMessage());
        } catch (IOException ex) {
            throw new AssertionError(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            throw new AssertionError(ex.getMessage());
        }
    }

    public void saveUsers(List<User> o) {
        saveObject(o, filename);
    }

    public List<User> loadUsers() {
        return (List< User>) loadObject(filename);
    }

    public void saveLog() {
        int[] log = {Vacancy.getIterator(), ApplicationFile.getIterator()};
        saveObject(log, fileLog);
    }

    public int[] loadLog() {
        int[] log = (int[]) loadObject(fileLog);
        return log;
    }
}
