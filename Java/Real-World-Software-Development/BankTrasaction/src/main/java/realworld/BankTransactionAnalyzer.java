package realworld;

import realworld.parser.BankStatementCSVParser;

import java.io.IOException;

public class BankTransactionAnalyzer {

    public static void main(String[] args) throws IOException {
        BankStatementAnalyzer bankStatementAnalyzer = new BankStatementAnalyzer();
        bankStatementAnalyzer.analyze(args[0], new BankStatementCSVParser());
    }
}
