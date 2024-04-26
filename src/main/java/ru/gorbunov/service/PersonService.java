package ru.gorbunov.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public interface PersonService {
    void getPerson(long person_id, PrintWriter out) throws SQLException, IOException;
    void createPerson(BufferedReader in, PrintWriter out) throws SQLException, IOException;
    void updatePerson(BufferedReader in, PrintWriter out) throws SQLException, IOException;
    void deletePerson(long person_id, PrintWriter out) throws SQLException, IOException;
}
