package realworld;

import org.junit.jupiter.api.Test;
import realworld.parser.BankStatementCSVParser;
import realworld.parser.BankStatementParser;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BankStatementCSVParserTest {
    private final BankStatementParser bankStatementParser = new BankStatementCSVParser();

    @Test
    void parseForm_메서드는_한_줄이_주어지면_거래내역을_리턴한다() {
        // given
        final String line = "30-01-2017,-50,Tesco";

        // when
        final BankTransaction result = bankStatementParser.parseFrom(line);

        // then
        final BankTransaction expected = new BankTransaction(LocalDate.of(2017, Month.JANUARY, 30), -50, "Tesco");
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void parseLinesFrom_메서드는_여러_줄이_주어지면_거래내역목록을_리턴한다() {
        // given
        List<String> lines = List.of(
                "30-01-2017,-50,Tesco",
                "02-02-2017,1000,Salary",
                "20-02-2017,-30,Tesco"
        );

        // when
        final List<BankTransaction> result = bankStatementParser.parseLinesFrom(lines);

        // then
        final List<BankTransaction> expected = List.of(
                new BankTransaction(LocalDate.of(2017, Month.JANUARY, 30), -50, "Tesco"),
                new BankTransaction(LocalDate.of(2017, Month.FEBRUARY, 2), 1000, "Salary"),
                new BankTransaction(LocalDate.of(2017, Month.FEBRUARY, 20), -30, "Tesco")
        );

        assertThat(result).isEqualTo(expected);
    }
}
