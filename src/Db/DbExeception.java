package Db;

public class DbExeception extends RuntimeException{
    private static  final long serialVersionUID = 1L;

    public DbExeception(String message) {
        super(message);
    }
}
