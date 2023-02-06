package database;

// Classe criada para "driblar" o fato de SQLException ser Exception (obrigada a tratar).

public class DBException extends RuntimeException {
    public DBException(String message) {
        super(message);
    }
}